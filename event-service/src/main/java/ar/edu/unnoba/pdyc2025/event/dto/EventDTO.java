package ar.edu.unnoba.pdyc2025.event.dto;

import ar.edu.unnoba.pdyc2025.event.model.EventState;
import java.time.LocalDate;
import java.util.Set;

public class EventDTO {
    private Long id;
    private String name;
    private EventState state;
    private LocalDate startDate;
    private LocalDate endDate;
    private Set<Long> artistIds;

    // Constructor
    public EventDTO(Long id, String name, EventState state, LocalDate startDate, LocalDate endDate, Set<Long> artistIds) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.startDate = startDate;
        this.endDate = endDate;
        this.artistIds = artistIds;
    }

    // Getters y setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public EventState getState() { return state; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public Set<Long> getArtistIds() { return artistIds; }
}