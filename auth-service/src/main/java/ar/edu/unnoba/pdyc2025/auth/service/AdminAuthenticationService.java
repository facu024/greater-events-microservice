package ar.edu.unnoba.pdyc2025.auth.service;

import ar.edu.unnoba.pdyc2025.auth.model.Admin;

public interface AdminAuthenticationService {
    String authenticate(Admin admin) throws Exception;
}
