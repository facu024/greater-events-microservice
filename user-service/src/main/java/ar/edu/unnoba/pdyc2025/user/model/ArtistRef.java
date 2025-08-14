package ar.edu.unnoba.pdyc2025.user.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "artist_ref")
public class ArtistRef {

    @Id
    private Long id; // viene del artist-service

    private String name;

    // Mapeo inverso opcional (no necesario si solo se consulta desde User)
    @ManyToMany(mappedBy = "followedArtists")
    private Set<User> followers = new HashSet<>();

    public ArtistRef() {}
    public ArtistRef(Long id) { this.id = id; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<User> getFollowers() { return followers; }
    public void setFollowers(Set<User> followers) { this.followers = followers; }
}