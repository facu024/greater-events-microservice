package ar.edu.unnoba.pdyc2025.user;

import ar.edu.unnoba.pdyc2025.user.model.Admin;
import ar.edu.unnoba.pdyc2025.user.util.JwtTokenUtil;
import ar.edu.unnoba.pdyc2025.user.util.PasswordEncoder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import ar.edu.unnoba.pdyc2025.user.util.JwtAuthorizationFilter;
import ar.edu.unnoba.pdyc2025.user.service.AdminAuthorizationService;
import ar.edu.unnoba.pdyc2025.user.repository.AdminRepository;


@SpringBootApplication
public class UserServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new PasswordEncoder();
	}

	@Bean
	public FilterRegistrationBean<JwtAuthorizationFilter> jwtFilter(
			AdminAuthorizationService authService,
			JwtTokenUtil jwtTokenUtil) {

		JwtAuthorizationFilter filter = new JwtAuthorizationFilter(jwtTokenUtil, authService);		// si querés usar authorizationService, agregá un setter en JwtAuthorizationFilter
		// filter.setAuthorizationService(authService);

		FilterRegistrationBean<JwtAuthorizationFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(filter);
		registration.addUrlPatterns("/admin/*");
		return registration;
	}
	@Bean
	public CommandLineRunner initAdmin(AdminRepository adminRepository, PasswordEncoder encoder) {
		return args -> {
			if (adminRepository.findByUsername("admin") == null) {
				Admin admin = new Admin();
				admin.setUsername("admin");
				admin.setPassword(encoder.encode("admin123"));
				adminRepository.save(admin);
				System.out.println("Admin por defecto creado: admin/admin123");
			}
		};
	}
}


