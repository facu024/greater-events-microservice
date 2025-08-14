package ar.edu.unnoba.pdyc2025.user.dto;

public class UserDTO {

    private String email;
    private String password;

    private String username; // obligatorio


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.trim();
    }


    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

