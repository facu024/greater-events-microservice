package ar.edu.unnoba.pdyc2025.user.service;

import ar.edu.unnoba.pdyc2025.user.dto.ArtistDTO;
import ar.edu.unnoba.pdyc2025.user.dto.UserDTO;
import ar.edu.unnoba.pdyc2025.user.model.*;
import java.util.List;

public interface UserService {
    User findByUsername(String username);
    void registerUser(User user);
    void followArtist(String username, Long artistId, String artistName);
    void unfollowArtist(String username, Long artistId);
    List<ArtistRef> getFollowedArtists(String username);

    List<ArtistDTO> getActiveFollowedArtists(String username);

    void favoriteEvent(String username, Long eventId, String title);
    void unfavoriteEvent(String username, Long eventId);
    List<EventRef> getFavoriteEvents(String username);

    List<EventRef> getRecommendedEvents(String username); // placeholder
    List<UserNotification> getNotifications(String username);

    User createUser(UserDTO dto);
}
