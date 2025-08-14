package ar.edu.unnoba.pdyc2025.artist.repository;

import ar.edu.unnoba.pdyc2025.artist.model.Artist;
import ar.edu.unnoba.pdyc2025.artist.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    List<Artist> findByGenre(Genre genre);
}
