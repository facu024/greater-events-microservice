package ar.edu.unnoba.pdyc2025.auth.repository;

import ar.edu.unnoba.pdyc2025.auth.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(@Param("username") String username);
}
