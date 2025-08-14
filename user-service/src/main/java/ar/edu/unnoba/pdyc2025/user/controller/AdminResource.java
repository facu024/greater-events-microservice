package ar.edu.unnoba.pdyc2025.user.controller;

import ar.edu.unnoba.pdyc2025.user.dto.AdminAuthenticationRequestDTO;
import ar.edu.unnoba.pdyc2025.user.model.Admin;
import ar.edu.unnoba.pdyc2025.user.service.AdminAuthenticationService;
import ar.edu.unnoba.pdyc2025.user.util.PasswordEncoder;
import ar.edu.unnoba.pdyc2025.user.util.JwtTokenUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/admin")
public class AdminResource {

    @Autowired
    private AdminAuthenticationService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private ModelMapper modelMapper = new ModelMapper();

    @PostMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authentication(@RequestBody AdminAuthenticationRequestDTO dto) {
        try {
            // Buscar el admin en la base de datos
            Admin admin = authService.findByUsername(dto.getUsername());
            if (admin == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
            }

            // Verificar la contraseña con el PasswordEncoder
            if (!passwordEncoder.verify(dto.getPassword(), admin.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
            }

            // Generar el token JWT si las credenciales son correctas
            String token = jwtTokenUtil.generateToken(admin.getUsername());
            Map<String, String> body = new HashMap<>();
            body.put("token", token);

            return ResponseEntity.ok(body);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error: " + e.getMessage());
        }
    }
}
