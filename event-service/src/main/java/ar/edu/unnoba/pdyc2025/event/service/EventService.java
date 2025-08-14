package ar.edu.unnoba.pdyc2025.event.service;

import ar.edu.unnoba.pdyc2025.event.dto.EventDTO;
import ar.edu.unnoba.pdyc2025.event.model.Event;
import ar.edu.unnoba.pdyc2025.event.model.EventState;
import ar.edu.unnoba.pdyc2025.event.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository repo;

    public EventService(EventRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<Event> findAll(EventState state) {
        return state != null ? repo.findByState(state) : repo.findAllWithArtists();
    }

    @Transactional(readOnly = true)
    public Event find(Long id) {
        return repo.findByIdWithArtists(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public EventDTO findDTO(Long id) {
        Event e = find(id);
        return toDTO(e);
    }


    @Transactional(readOnly = true)
    public List<Event> findAllFuture() {
        return repo.findAllWithArtists()
                .stream()
                .filter(e -> e.getStartDate() != null && e.getStartDate().isAfter(LocalDate.now()))
                .filter(e -> e.getState() == EventState.CONFIRMED || e.getState() == EventState.RESCHEDULED)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<Event> findFutureById(Long id) {
        return repo.findByIdWithArtists(id)
                .filter(e -> e.getStartDate() != null && e.getStartDate().isAfter(LocalDate.now()))
                .filter(e -> e.getState() == EventState.CONFIRMED || e.getState() == EventState.RESCHEDULED);
    }

    @Transactional(readOnly = true)
    public List<Event> findFutureByArtist(Long artistId) {
        return repo.findAllWithArtists()
                .stream()
                .filter(e -> e.getArtistIds().contains(artistId))
                .filter(e -> e.getStartDate() != null && e.getStartDate().isAfter(LocalDate.now()))
                .filter(e -> e.getState() == EventState.CONFIRMED || e.getState() == EventState.RESCHEDULED)
                .collect(Collectors.toList());
    }

    public Event create(Event e) {
        e.setState(EventState.TENTATIVE);
        return repo.save(e);
    }

    public Event update(Long id, Event data) {
        Event ev = find(id);
        if (ev.getState() != EventState.TENTATIVE)
            throw new RuntimeException("Only tentative events can be modified");

        ev.setName(data.getName());
        ev.setDescription(data.getDescription());
        ev.setStartDate(data.getStartDate());

        if (data.getEndDate() != null) {
            if (ev.getStartDate() != null && data.getEndDate().isBefore(ev.getStartDate())) {
                throw new RuntimeException("End date must be the same or after start date");
            }
            ev.setEndDate(data.getEndDate());
        } else {
            ev.setEndDate(null); // Permitir borrar la fecha de fin
        }

        return repo.save(ev);
    }

    public void delete(Long id) {
        Event ev = find(id);
        if (ev.getState() == EventState.TENTATIVE) {
            repo.delete(ev);
        } else {
            throw new RuntimeException("Can't delete non-tentative events");
        }
    }

    public void addArtist(Long eventId, Long artistId) {
        Event e = find(eventId);
        if (e.getState() != EventState.TENTATIVE) throw new RuntimeException("Invalid state");
        e.getArtistIds().add(artistId);
        repo.save(e);
    }

    public void removeArtist(Long eventId, Long artistId) {
        Event e = find(eventId);
        if (e.getState() != EventState.TENTATIVE) throw new RuntimeException("Invalid state");
        e.getArtistIds().remove(artistId);
        repo.save(e);
    }

    public void confirm(Long id) {
        Event e = find(id);
        if (e.getStartDate() == null || e.getStartDate().isBefore(LocalDate.now()))
            throw new RuntimeException("Event date must be future");
        e.setState(EventState.CONFIRMED);
        repo.save(e);
    }

    public void reschedule(Long id, LocalDate date) {
        Event e = find(id);
        if (e.getState() == EventState.TENTATIVE) {
            e.setStartDate(date);
            e.setState(EventState.RESCHEDULED);
            repo.save(e);
            return;
        }
        if ((e.getState() == EventState.CONFIRMED || e.getState() == EventState.RESCHEDULED)
                && !e.getStartDate().isBefore(LocalDate.now())
                && date.isAfter(LocalDate.now())) {
            e.setStartDate(date);
            e.setState(EventState.RESCHEDULED);
            repo.save(e);
            return;
        }
        throw new RuntimeException("Invalid reschedule");
    }

    public void cancel(Long id) {
        Event e = find(id);
        if (e.getState() == EventState.CONFIRMED || e.getState() == EventState.RESCHEDULED ||e.getState() == EventState.TENTATIVE ) {
            e.setState(EventState.CANCELED);
            repo.save(e);
        } else {
            throw new RuntimeException("Invalid cancel");
        }
    }

    // --- Mapeo a DTO ---
    public EventDTO toDTO(Event e) {
        return new EventDTO(
                e.getId(),
                e.getName(),
                e.getState(),
                e.getStartDate(),
                e.getEndDate(),
                e.getArtistIds() // lazy collection ya cargada por @Transactional o JOIN FETCH
        );
    }
}