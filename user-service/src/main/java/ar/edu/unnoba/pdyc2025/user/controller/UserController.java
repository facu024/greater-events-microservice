package ar.edu.unnoba.pdyc2025.user.controller;

import ar.edu.unnoba.pdyc2025.user.client.ArtistClient;
import ar.edu.unnoba.pdyc2025.user.client.EventClient;
import ar.edu.unnoba.pdyc2025.user.dto.ArtistDTO;
import ar.edu.unnoba.pdyc2025.user.dto.EventDTO;
import ar.edu.unnoba.pdyc2025.user.dto.UserDTO;
import ar.edu.unnoba.pdyc2025.user.model.ArtistRef;
import ar.edu.unnoba.pdyc2025.user.model.EventRef;
import ar.edu.unnoba.pdyc2025.user.model.User;
import ar.edu.unnoba.pdyc2025.user.model.UserNotification;
import ar.edu.unnoba.pdyc2025.user.service.UserService;
import ar.edu.unnoba.pdyc2025.user.util.JwtTokenUtil;
import ar.edu.unnoba.pdyc2025.user.util.PasswordEncoder;
import jakarta.ws.rs.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    private final ArtistClient artistClient;

    @Autowired
    private EventClient eventClient;

    private final UserService userService;
    private final JwtTokenUtil jwt;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(ArtistClient artistClient, UserService userService, JwtTokenUtil jwt, PasswordEncoder passwordEncoder) {
        this.artistClient = artistClient;
        this.userService = userService;
        this.jwt = jwt;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = new ModelMapper();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        User user = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> loginUser(@RequestBody UserDTO userDTO) {
        try {
            User user = userService.findByUsername(userDTO.getUsername());
            if (user == null || !passwordEncoder.verify(userDTO.getPassword(), user.getPasswordHash())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Usuario o contraseña inválidos"));
            }

            String token = jwt.generateToken(user.getUsername());
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Ocurrió un error al iniciar sesión"));
        }
    }

    // --- REGISTER ---
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        try {
            User user = modelMapper.map(userDTO, User.class);
            user.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));
            userService.registerUser(user);
            return ResponseEntity.status(201).body("Usuario registrado exitosamente");
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ocurrió un error al registrar el usuario");
        }
    }

    // --- ARTISTS ---
    private String getUsernameFromHeader(@RequestHeader Map<String, String> headers){
        String authHeader = headers.get("authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new BadRequestException("Header Authorization inválido o ausente");
        }
        String token = authHeader.substring(7); // saca "Bearer "
        return jwt.getSubject(token);
    }

    @PostMapping("/artists/{id}/follow")
    public ResponseEntity<Void> follow(@RequestHeader Map<String, String> headers,
                                       @PathVariable Long id,
                                       @RequestBody(required=false) Map<String,String> body){
        userService.followArtist(getUsernameFromHeader(headers), id,
                body!=null ? body.getOrDefault("name","") : "");
        return ResponseEntity.ok().build();
    }



    @DeleteMapping("/artists/{id}/unfollow")
    public ResponseEntity<Void> unfollow(@RequestHeader Map<String, String> headers,
                                         @PathVariable Long id){
        userService.unfollowArtist(getUsernameFromHeader(headers), id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/artists/following")
    public List<ArtistDTO> followed(@RequestHeader Map<String,String> headers){
        String username = getUsernameFromHeader(headers);
        return userService.getActiveFollowedArtists(username);
    }

    // --- EVENTS ---
    @PostMapping("/events/{id}/favorite")
    public ResponseEntity<Void> favorite(@RequestHeader Map<String, String> headers,
                                         @PathVariable Long id,
                                         @RequestBody(required=false) Map<String,String> body){
        userService.favoriteEvent(getUsernameFromHeader(headers), id,
                body!=null ? body.getOrDefault("title","") : "");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/events/{id}/unfavorite")
    public ResponseEntity<Void> unfavorite(@RequestHeader Map<String, String> headers,
                                           @PathVariable Long id){
        userService.unfavoriteEvent(getUsernameFromHeader(headers), id);
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/events/favorites")
//    public List<EventRef> favorites(@RequestHeader Map<String, String> headers){
//        return userService.getFavoriteEvents(getUsernameFromHeader(headers));
//    }

    @GetMapping("/events/favorites")
    public List<EventDTO> favorites(@RequestHeader Map<String, String> headers){
        String username = getUsernameFromHeader(headers);
        List<EventRef> favorites = userService.getFavoriteEvents(username);

        // Mapear EventRef a EventDTO usando el cliente
        return favorites.stream()
                .map(e -> eventClient.getEvent(e.getId()))
                .toList();
    }

    @GetMapping("/events/recommended")
    public List<EventRef> recommended(@RequestHeader Map<String, String> headers){
        return userService.getRecommendedEvents(getUsernameFromHeader(headers));
    }

    // --- NOTIFICATIONS ---
    @GetMapping("/notifications")
    public List<UserNotification> notifications(@RequestHeader Map<String, String> headers){
        return userService.getNotifications(getUsernameFromHeader(headers));
    }
}