package ar.edu.unnoba.pdyc2025.user.service;

import ar.edu.unnoba.pdyc2025.user.model.Admin;

public interface AdminAuthenticationService {
    String authenticate(Admin admin) throws Exception;

    Admin findByUsername(String username);
}
