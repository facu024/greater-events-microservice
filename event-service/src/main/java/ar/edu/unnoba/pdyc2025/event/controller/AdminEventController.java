package ar.edu.unnoba.pdyc2025.event.controller;

import ar.edu.unnoba.pdyc2025.event.dto.EventDTO;
import ar.edu.unnoba.pdyc2025.event.model.Event;
import ar.edu.unnoba.pdyc2025.event.model.EventState;
import ar.edu.unnoba.pdyc2025.event.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/events")
public class AdminEventController {

    private final EventService service;

    public AdminEventController(EventService service) {
        this.service = service;
    }

    // --- GET todos los eventos (opcionalmente filtrados por estado) ---
    @GetMapping
    public List<EventDTO> getAll(@RequestParam(required = false) EventState state) {
        return service.findAll(state)
                .stream()
                .map(service::toDTO)
                .collect(Collectors.toList());
    }

    // --- GET un evento por id ---
    @GetMapping("/{id}")
    public EventDTO get(@PathVariable Long id) {
        return service.findDTO(id);
    }

    // --- POST crear evento ---
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventDTO create(@RequestBody Event event) {
        Event saved = service.create(event);
        return service.toDTO(saved);
    }

    // --- PUT actualizar evento ---
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody Event event) {
        service.update(id, event);
    }

    // --- DELETE evento ---
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // --- POST agregar artista ---
    @PostMapping("/{id}/artists")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addArtist(@PathVariable Long id, @RequestBody Map<String, Long> body) {
        service.addArtist(id, body.get("artist_id"));
    }

    // --- DELETE remover artista ---
    @DeleteMapping("/{id}/artists/{artistId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeArtist(@PathVariable Long id, @PathVariable Long artistId) {
        service.removeArtist(id, artistId);
    }

    // --- PUT confirmar evento ---
    @PutMapping("/{id}/confirmed")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirm(@PathVariable Long id) {
        service.confirm(id);
    }

    // --- PUT reprogramar evento ---
    @PutMapping("/{id}/rescheduled")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reschedule(@PathVariable Long id, @RequestBody Map<String, String> body) {
        LocalDate newDate = LocalDate.parse(body.get("start_date"));
        service.reschedule(id, newDate);
    }

    // --- PUT cancelar evento ---
    @PutMapping("/{id}/canceled")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable Long id) {
        service.cancel(id);
    }
}