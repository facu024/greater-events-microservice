package ar.edu.unnoba.pdyc2025.user.repository;

import ar.edu.unnoba.pdyc2025.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByEmail(String email);
}