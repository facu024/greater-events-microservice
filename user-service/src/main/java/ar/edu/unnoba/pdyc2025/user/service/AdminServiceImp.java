package ar.edu.unnoba.pdyc2025.user.service;

import ar.edu.unnoba.pdyc2025.user.model.Admin;
import ar.edu.unnoba.pdyc2025.user.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImp implements AdminService {
    private final AdminRepository repo;
    public AdminServiceImp(AdminRepository repo) { this.repo = repo; }
    public Admin findByUsername(String username) { return repo.findByUsername(username); }
}
