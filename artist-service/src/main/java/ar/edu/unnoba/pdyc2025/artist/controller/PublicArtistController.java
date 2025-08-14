package ar.edu.unnoba.pdyc2025.artist.controller;

import ar.edu.unnoba.pdyc2025.artist.model.Artist;
import ar.edu.unnoba.pdyc2025.artist.model.Genre;
import ar.edu.unnoba.pdyc2025.artist.service.ArtistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/artists")
public class PublicArtistController {

    private final ArtistService service;

    public PublicArtistController(ArtistService service) {
        this.service = service;
    }

    @GetMapping
    public List<Artist> all(@RequestParam(required = false) Genre genre) {
        return service.findAll(genre);
    }

    @GetMapping("/{id}")
    public Artist one(@PathVariable Long id) {
        return service.find(id);
    }
}
