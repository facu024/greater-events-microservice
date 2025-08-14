package ar.edu.unnoba.pdyc2025.user.service;

import ar.edu.unnoba.pdyc2025.user.client.ArtistClient;
import ar.edu.unnoba.pdyc2025.user.dto.ArtistDTO;
import ar.edu.unnoba.pdyc2025.user.dto.UserDTO;
import ar.edu.unnoba.pdyc2025.user.model.*;
import ar.edu.unnoba.pdyc2025.user.repository.*;
import ar.edu.unnoba.pdyc2025.user.util.PasswordEncoder;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImp implements UserService {

    private final UserRepository userRepo;
    private final UserNotificationRepository notifRepo;
    private final ArtistRefRepository artistRepo;
    private final EventRefRepository eventRepo;
    private final ArtistClient artistClient;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository u, UserNotificationRepository n,
                          ArtistRefRepository a, EventRefRepository e,
                          ArtistClient artistClient,
                          PasswordEncoder passwordEncoder) {
        this.userRepo = u;
        this.notifRepo = n;
        this.artistRepo = a;
        this.eventRepo = e;
        this.artistClient = artistClient;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(UserDTO dto) {
        if (dto.getUsername() == null || dto.getUsername().isBlank()) {
            dto.setUsername(dto.getEmail().split("@")[0]);
        }
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public void registerUser(User user) {
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new BadRequestException("Email en uso.");
        }
        userRepo.save(user);
    }

    private User require(String username) {
        User u = userRepo.findByUsername(username);
        if (u == null) {
            u = new User();
            u.setUsername(username);
            u.setEmail(username + "@unknown.local");
            u = userRepo.save(u);
        }
        return u;
    }

    @Override
    public void followArtist(String username, Long artistId, String name) {
        User u = require(username);
        ArtistRef ar = artistRepo.findById(artistId).orElseGet(() -> {
            ArtistRef x = new ArtistRef();
            x.setId(artistId);
            x.setName(name);
            return artistRepo.save(x);
        });
        u.getFollowedArtists().add(ar);
        userRepo.save(u);
    }

    @Override
    public void unfollowArtist(String username, Long artistId) {
        User u = require(username);
        u.getFollowedArtists().removeIf(a -> a.getId().equals(artistId));
        userRepo.save(u);
    }

    @Override
    public List<ArtistRef> getFollowedArtists(String username) {
        User user = userRepo.findByUsername(username);
        return new ArrayList<>(user.getFollowedArtists());
    }

    @Override
    public List<ArtistDTO> getActiveFollowedArtists(String username) {
        List<ArtistRef> refs = getFollowedArtists(username);
        return refs.stream()
                .map(ref -> artistClient.getArtist(ref.getId()))
                .filter(Objects::nonNull)
                .filter(ArtistDTO::isActive)
                .collect(Collectors.toList());
    }

    @Override
    public void favoriteEvent(String username, Long eventId, String title) {
        User u = require(username);
        EventRef er = eventRepo.findById(eventId).orElseGet(() -> {
            EventRef x = new EventRef();
            x.setId(eventId);
            x.setTitle(title);
            return eventRepo.save(x);
        });
        u.getFavoriteEvents().add(er);
        userRepo.save(u);
    }

    @Override
    public void unfavoriteEvent(String username, Long eventId) {
        User u = require(username);
        u.getFavoriteEvents().removeIf(e -> e.getId().equals(eventId));
        userRepo.save(u);
    }

    @Override
    public List<EventRef> getFavoriteEvents(String username) {
        return new ArrayList<>(require(username).getFavoriteEvents());
    }

    @Override
    public List<EventRef> getRecommendedEvents(String username) {
        return Collections.emptyList();
    }

    @Override
    public List<UserNotification> getNotifications(String username) {
        User u = require(username);
        return notifRepo.findByUserIdOrderByTimestampDesc(u.getId());
    }
}