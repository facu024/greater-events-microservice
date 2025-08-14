package ar.edu.unnoba.pdyc2025.auth.service;

import ar.edu.unnoba.pdyc2025.auth.model.Admin;
import ar.edu.unnoba.pdyc2025.auth.util.JwtTokenUtil;
import ar.edu.unnoba.pdyc2025.auth.util.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminAuthenticationServiceImp implements AdminAuthenticationService {
    private final AdminService adminService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public AdminAuthenticationServiceImp(AdminService adminService,
                                         PasswordEncoder passwordEncoder,
                                         JwtTokenUtil jwtTokenUtil) {
        this.adminService = adminService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
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
