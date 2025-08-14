package ar.edu.unnoba.pdyc2025.auth.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

    private final BCryptPasswordEncoder delegate = new BCryptPasswordEncoder();

    public String encode(String rawPassword) {
        return delegate.encode(rawPassword);
    }

    public boolean verify(String rawPassword, String encodedPassword) {
        return delegate.matches(rawPassword, encodedPassword);
    }
}
