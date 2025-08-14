package ar.edu.unnoba.pdyc2025.auth;

import ar.edu.unnoba.pdyc2025.auth.model.Admin;
import ar.edu.unnoba.pdyc2025.auth.repository.AdminRepository;
import ar.edu.unnoba.pdyc2025.auth.util.PasswordEncoder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
// @EnableDiscoveryClient no es obligatorio en 2023.x si tenés el starter

@SpringBootApplication
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() { return new PasswordEncoder(); }
//
//    // Seed admin si no existe
//    @Bean
//    public CommandLineRunner seed(AdminRepository repo, PasswordEncoder enc) {
//        return args -> {
//            if (repo.findByUsername("admin") == null) {
//                Admin a = new Admin();
//                a.setUsername("admin");
//                a.setPassword(enc.encode("admin"));
//                repo.save(a);
//                System.out.println("Admin creado: admin/admin");
//            }
//        };
//    }
}
