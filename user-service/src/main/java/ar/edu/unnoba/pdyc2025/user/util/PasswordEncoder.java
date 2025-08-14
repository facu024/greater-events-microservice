package ar.edu.unnoba.pdyc2025.user.util;

import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Component
public class PasswordEncoder {
    private final BCryptPasswordEncoder delegate = new BCryptPasswordEncoder();

    public String encode(String raw) {
        return delegate.encode(raw);
    }

    public boolean verify(String raw, String hashed) {
        return delegate.matches(raw, hashed);
    }
}


