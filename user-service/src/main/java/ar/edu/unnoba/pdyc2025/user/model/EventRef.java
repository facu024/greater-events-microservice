package ar.edu.unnoba.pdyc2025.user.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "event_ref")
public class EventRef {

    @Id
    private Long id; // viene del event-service

    private String title;

    // Mapeo inverso opcional
    @ManyToMany(mappedBy = "favoriteEvents")
    private Set<User> fans = new HashSet<>();

    public EventRef() {}
    public EventRef(Long id) { this.id = id; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Set<User> getFans() { return fans; }
    public void setFans(Set<User> fans) { this.fans = fans; }
}