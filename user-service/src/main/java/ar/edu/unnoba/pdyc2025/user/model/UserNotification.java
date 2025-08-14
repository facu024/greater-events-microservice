package ar.edu.unnoba.pdyc2025.user.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class UserNotification {
    @Id @GeneratedValue private Long id;

    @ManyToOne(optional=false) private User user;
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
