package com.example.SocialMediaAlmat.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class User {

    private Long id;

    @NotBlank(message = "Имя пользователя обязательно")
    @Size(min = 3, max = 30, message = "Имя: от 3 до 30 символов")
    private String username;

    @NotBlank(message = "Email обязателен")
    @Email(message = "Некорректный email")
    private String email;

    @Size(max = 300, message = "Bio не более 300 символов")
    private String bio;

    private LocalDateTime createdAt;

    public User() {}

    public User(Long id, String username, String email, String bio) {
        this.id        = id;
        this.username  = username;
        this.email     = email;
        this.bio       = bio;
        this.createdAt = LocalDateTime.now();
    }

    public Long          getId()        { return id; }
    public void          setId(Long id) { this.id = id; }

    public String        getUsername()              { return username; }
    public void          setUsername(String u)      { this.username = u; }

    public String        getEmail()                 { return email; }
    public void          setEmail(String e)         { this.email = e; }

    public String        getBio()                   { return bio; }
    public void          setBio(String b)           { this.bio = b; }

    public LocalDateTime getCreatedAt()             { return createdAt; }
    public void          setCreatedAt(LocalDateTime t) { this.createdAt = t; }
}

