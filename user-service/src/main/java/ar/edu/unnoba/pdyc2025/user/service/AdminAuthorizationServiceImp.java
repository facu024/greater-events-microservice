package ar.edu.unnoba.pdyc2025.user.service;

import ar.edu.unnoba.pdyc2025.user.model.Admin;
import ar.edu.unnoba.pdyc2025.user.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminAuthorizationServiceImp implements AdminAuthorizationService {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AdminService adminService;

    public Admin authorize(String token) throws Exception {
        if (!jwtTokenUtil.verify(token)) {
            throw new Exception("Invalid token");
        }
        String username = jwtTokenUtil.getSubject(token);
        Admin admin = adminService.findByUsername(username);
        if (admin == null) throw new Exception("Admin not found");
        return admin;
    }
}
