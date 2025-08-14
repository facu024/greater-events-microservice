//
//package ar.edu.unnoba.pdyc2025.artist.controller;
//
//import ar.edu.unnoba.pdyc2025.artist.model.Artist;
//import ar.edu.unnoba.pdyc2025.artist.model.Genre;
//import ar.edu.unnoba.pdyc2025.artist.repository.ArtistRepository;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//public class ArtistController {
//
//    private final ArtistRepository repo;
//    public ArtistController(ArtistRepository repo) { this.repo = repo; }
//
//    // PUBLIC
//    @GetMapping("/public/artists")
//    public List<Artist> all(@RequestParam(value="genre",required=false) Genre genre) {
//        return genre == null ? repo.findAll() : repo.findByGenre(genre);
//    }
//    @GetMapping("/public/artists/{id}")
//    public ResponseEntity<Artist> get(@PathVariable Long id) {
//        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
//    }
//
//    // ADMIN
//    @GetMapping("/admin/artists")
//    public List<Artist> adminList(@RequestParam(value="genre",required=false) Genre genre) {
//        return all(genre);
//    }
//
//    @GetMapping("/admin/artists/{id}")
//    public ResponseEntity<Artist> adminGet(@PathVariable Long id) {
//        return get(id);
//    }
//
//    @PostMapping("/admin/artists")
//    public Artist create(@RequestBody Artist a) { a.setId(null); return repo.save(a); }
//
//    @PutMapping("/admin/artists/{id}")
//    public ResponseEntity<Artist> update(@PathVariable Long id, @RequestBody Artist a) {
//        Optional<Artist> db = repo.findById(id);
//        if (db.isEmpty()) return ResponseEntity.notFound().build();
//        Artist x = db.get();
//        x.setName(a.getName());
//        x.setGenre(a.getGenre());
//        x.setActive(a.isActive());
//        return ResponseEntity.ok(repo.save(x));
//    }
//
//    @DeleteMapping("/admin/artists/{id}")
//    public ResponseEntity<?> delete(@PathVariable Long id) {
//        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
//        repo.deleteById(id);
//        return ResponseEntity.ok().build();
//    }
//}
