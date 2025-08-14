package ar.edu.unnoba.pdyc2025.auth.service;

import ar.edu.unnoba.pdyc2025.auth.model.Admin;
import ar.edu.unnoba.pdyc2025.auth.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImp implements AdminService {
    private final AdminRepository repo;
    public AdminServiceImp(AdminRepository repo) { this.repo = repo; }
    public Admin findByUsername(String username) { return repo.findByUsername(username); }
}
