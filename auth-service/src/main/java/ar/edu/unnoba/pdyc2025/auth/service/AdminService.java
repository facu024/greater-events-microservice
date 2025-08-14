package ar.edu.unnoba.pdyc2025.auth.service;

import ar.edu.unnoba.pdyc2025.auth.model.Admin;

public interface AdminService {
    Admin findByUsername(String username);
}
