package com.example.SocialMediaAlmat.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class Post {

    private Long id;

    @NotNull(message = "ID пользователя обязателен")
    private Long userId;

    @NotBlank(message = "Заголовок обязателен")
    @Size(min = 3, max = 100, message = "Заголовок: от 3 до 100 символов")
    private String title;

    @NotBlank(message = "Содержание обязательно")
    @Size(min = 5, max = 2000, message = "Содержание: от 5 до 2000 символов")
    private String content;

    private String imageUrl;
    private int    likes;
    private LocalDateTime createdAt;

    public Post() {}

    public Post(Long id, Long userId, String title, String content, String imageUrl) {
        this.id        = id;
        this.userId    = userId;
        this.title     = title;
        this.content   = content;
        this.imageUrl  = imageUrl;
        this.likes     = 0;
        this.createdAt = LocalDateTime.now();
    }

    public Long          getId()                  { return id; }
    public void          setId(Long id)           { this.id = id; }

    public Long          getUserId()              { return userId; }
    public void          setUserId(Long u)        { this.userId = u; }

    public String        getTitle()               { return title; }
    public void          setTitle(String t)       { this.title = t; }

    public String        getContent()             { return content; }
    public void          setContent(String c)     { this.content = c; }

    public String        getImageUrl()            { return imageUrl; }
    public void          setImageUrl(String i)    { this.imageUrl = i; }

    public int           getLikes()               { return likes; }
    public void          setLikes(int l)          { this.likes = l; }

    public LocalDateTime getCreatedAt()           { return createdAt; }
    public void          setCreatedAt(LocalDateTime t) { this.createdAt = t; }
}

