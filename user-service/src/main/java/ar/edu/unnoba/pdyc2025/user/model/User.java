package ar.edu.unnoba.pdyc2025.user.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users") // importante: evita la palabra reservada "user"
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String username;
    private String passwordHash;

    // --- ARTISTS ---
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_artist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    private Set<ArtistRef> followedArtists = new HashSet<>();

    // --- EVENTS ---
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_event",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private Set<EventRef> favoriteEvents = new HashSet<>();

    // --- GETTERS / SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public Set<ArtistRef> getFollowedArtists() { return followedArtists; }
    public void setFollowedArtists(Set<ArtistRef> followedArtists) { this.followedArtists = followedArtists; }

    public Set<EventRef> getFavoriteEvents() { return favoriteEvents; }
    public void setFavoriteEvents(Set<EventRef> favoriteEvents) { this.favoriteEvents = favoriteEvents; }

    // --- UTILS ---
    public void followArtist(ArtistRef artist) { this.followedArtists.add(artist); }
    public void unfollowArtist(Long artistId) {
        this.followedArtists.removeIf(a -> a.getId().equals(artistId));
    }

    public void favoriteEvent(EventRef event) { this.favoriteEvents.add(event); }
    public void unfavoriteEvent(Long eventId) {
        this.favoriteEvents.removeIf(e -> e.getId().equals(eventId));
    }
}