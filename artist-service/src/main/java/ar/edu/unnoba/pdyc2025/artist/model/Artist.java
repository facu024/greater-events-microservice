package ar.edu.unnoba.pdyc2025.artist.model;

import jakarta.persistence.*;

@Entity
public class Artist {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private boolean active = true;

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
