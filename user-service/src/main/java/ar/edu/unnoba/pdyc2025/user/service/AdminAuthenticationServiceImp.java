package ar.edu.unnoba.pdyc2025.user.service;

import ar.edu.unnoba.pdyc2025.user.model.Admin;
import ar.edu.unnoba.pdyc2025.user.repository.AdminRepository;
import ar.edu.unnoba.pdyc2025.user.util.JwtTokenUtil;
import ar.edu.unnoba.pdyc2025.user.util.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminAuthenticationServiceImp implements AdminAuthenticationService {
    private final AdminService adminService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AdminRepository adminRepository;

    public AdminAuthenticationServiceImp(AdminService adminService,
                                         PasswordEncoder passwordEncoder,
                                         JwtTokenUtil jwtTokenUtil) {
        this.adminService = adminService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username); // Devuelve el admin real
    }

    @Override
    public String authenticate(Admin admin) throws Exception {
        Admin dbAdmin = adminService.findByUsername(admin.getUsername());
        if (dbAdmin == null || !passwordEncoder.verify(admin.getPassword(), dbAdmin.getPassword())) {
            throw new Exception("Invalid credentials");
        }
        return jwtTokenUtil.generateToken(admin.getUsername());
    }

}
