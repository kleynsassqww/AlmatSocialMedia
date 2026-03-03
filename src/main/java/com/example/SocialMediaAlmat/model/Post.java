package com.example.SocialMediaAlmat.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class Post {

    private Long id;

    @NotBlank(message = "Заголовок не может быть пустым")
    @Size(min = 3, max = 100, message = "Заголовок от 3 до 100 символов")
    private String title;

    @NotBlank(message = "Содержание не может быть пустым")
    @Size(min = 5, max = 2000, message = "Содержание от 5 до 2000 символов")
    private String content;

    @NotNull(message = "ID пользователя обязателен")
    private Long userId;

    private int likes;

    private LocalDateTime createdAt;

    public Post() {
        this.createdAt = LocalDateTime.now();
        this.likes = 0;
    }

    public Post(Long id, String title, String content, Long userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.likes = 0;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public int getLikes() { return likes; }
    public void setLikes(int likes) { this.likes = likes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

