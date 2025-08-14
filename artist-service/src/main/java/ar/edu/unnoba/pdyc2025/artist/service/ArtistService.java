package ar.edu.unnoba.pdyc2025.artist.service;

import ar.edu.unnoba.pdyc2025.artist.model.Artist;
import ar.edu.unnoba.pdyc2025.artist.model.Genre;
import ar.edu.unnoba.pdyc2025.artist.repository.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistService {

    private final ArtistRepository repo;

    public ArtistService(ArtistRepository repo) {
        this.repo = repo;
    }

    public List<Artist> findAll(Genre genre) {
        return (genre != null) ? repo.findByGenre(genre) : repo.findAll();
    }

    public Artist find(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public Artist create(Artist artist) {
        return repo.save(artist);
    }

    public Artist update(Long id, Artist updated) {
        Artist artist = find(id);
        artist.setName(updated.getName());
        artist.setGenre(updated.getGenre());
        return repo.save(artist);
    }

    public void delete(Long id) {
        Artist artist = find(id);
        // en microservicios, preferimos soft-delete:
        artist.setActive(false);
        repo.save(artist);
    }
}
