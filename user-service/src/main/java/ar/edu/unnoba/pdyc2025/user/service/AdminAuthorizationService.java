package ar.edu.unnoba.pdyc2025.user.service;

import ar.edu.unnoba.pdyc2025.user.model.Admin;

public interface AdminAuthorizationService {
    Admin authorize(String token) throws Exception;
}
