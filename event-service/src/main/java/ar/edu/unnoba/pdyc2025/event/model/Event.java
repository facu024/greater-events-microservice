package ar.edu.unnoba.pdyc2025.event.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ⚡ Esto es clave
    private Long id;

    private String name;
    private String description;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private EventState state;

    @ElementCollection
    @CollectionTable(name = "event_artist_ids", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "artist_id")
    private Set<Long> artistIds = new HashSet<>();

    // getters y setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public EventState getState() { return state; }
    public void setState(EventState state) { this.state = state; }
    public Set<Long> getArtistIds() { return artistIds; }
    public void setArtistIds(Set<Long> artistIds) { this.artistIds = artistIds; }
}