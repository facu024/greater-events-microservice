package ar.edu.unnoba.pdyc2025.user.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ar.edu.unnoba.pdyc2025.user.dto.EventDTO;

import java.util.Arrays;
import java.util.List;

@Component
public class EventClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String EVENT_SERVICE_URL = "http://localhost:8082/public/events";

    public EventDTO getEvent(Long id) {
        return restTemplate.getForObject(EVENT_SERVICE_URL + "/" + id, EventDTO.class);
    }

    public List<EventDTO> getEventsByIds(List<Long> ids) {
        // Llamadas individuales por ahora; en prod se puede optimizar
        return ids.stream()
                .map(this::getEvent)
                .toList();
    }
}