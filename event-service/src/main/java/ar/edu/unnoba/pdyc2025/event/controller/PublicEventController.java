package ar.edu.unnoba.pdyc2025.event.controller;

import ar.edu.unnoba.pdyc2025.event.dto.EventDTO;
import ar.edu.unnoba.pdyc2025.event.model.EventState;
import ar.edu.unnoba.pdyc2025.event.service.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicEventController {

    private final EventService service;

    public PublicEventController(EventService service) {
        this.service = service;
    }

    // --- GET futuros eventos ---
    @GetMapping("/events")
    public List<EventDTO> futureEvents() {
        return service.findAllFuture()
                .stream()
                .map(service::toDTO)
                .toList();
    }

    // --- GET detalle de un evento ---
    @GetMapping("/events/{eventId}")
    public EventDTO eventDetails(@PathVariable Long eventId) {
        return service.findFutureById(eventId)
                .map(service::toDTO)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado o no accesible"));
    }

    // --- GET eventos de un artista ---
    @GetMapping("/artists/{artistId}/events")
    public List<EventDTO> artistEvents(@PathVariable Long artistId) {
        return service.findFutureByArtist(artistId)
                .stream()
                .map(service::toDTO)
                .toList();
    }
}