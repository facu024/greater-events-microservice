package ar.edu.unnoba.pdyc2025.event.repository;

import ar.edu.unnoba.pdyc2025.event.model.Event;
import ar.edu.unnoba.pdyc2025.event.model.EventState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByState(EventState state);

    // Trae un evento con artistas precargados
    @Query("SELECT e FROM Event e LEFT JOIN FETCH e.artistIds WHERE e.id = :id")
    Optional<Event> findByIdWithArtists(@Param("id") Long id);

    // Trae todos los eventos con artistas precargados
    @Query("SELECT DISTINCT e FROM Event e LEFT JOIN FETCH e.artistIds")
    List<Event> findAllWithArtists();
}