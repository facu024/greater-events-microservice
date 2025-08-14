package ar.edu.unnoba.pdyc2025.artist.controller;

import ar.edu.unnoba.pdyc2025.artist.model.Artist;
import ar.edu.unnoba.pdyc2025.artist.model.Genre;
import ar.edu.unnoba.pdyc2025.artist.service.ArtistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/artists")
public class AdminArtistController {

    private final ArtistService service;

    public AdminArtistController(ArtistService service) {
        this.service = service;
    }

    @GetMapping
    public List<Artist> getAll(@RequestParam(required = false) Genre genre) {
        return service.findAll(genre);
    }

    @GetMapping("/{id}")
    public Artist get(@PathVariable Long id) {
        return service.find(id);
    }

    @PostMapping
    public Artist create(@RequestBody Artist artist) {
        return service.create(artist);
    }

    @PutMapping("/{id}")
    public Artist update(@PathVariable Long id, @RequestBody Artist artist) {
        return service.update(id, artist);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
