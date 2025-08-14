package ar.edu.unnoba.pdyc2025.auth.controller;

import ar.edu.unnoba.pdyc2025.auth.dto.AdminAuthenticationRequestDTO;
import ar.edu.unnoba.pdyc2025.auth.model.Admin;
import ar.edu.unnoba.pdyc2025.auth.service.AdminAuthenticationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/admin")
public class AdminResource {
    @Autowired private AdminAuthenticationService authService;
    private ModelMapper modelMapper = new ModelMapper();

    @PostMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authentication(@RequestBody AdminAuthenticationRequestDTO dto) {
        try {
            Admin admin = modelMapper.map(dto, Admin.class);
            String token = authService.authenticate(admin);
            Map<String, String> body = new HashMap<>();
            body.put("token", token);
            return ResponseEntity.ok(body);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
