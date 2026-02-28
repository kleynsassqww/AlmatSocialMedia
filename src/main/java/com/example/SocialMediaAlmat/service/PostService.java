package com.example.SocialMediaAlmat.service;

import com.example.SocialMediaAlmat.model.Post;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final List<Post> posts   = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    public PostService() {
        posts.add(new Post(counter.getAndIncrement(), 1L,
                "Мой первый пост",
                "Привет всем! Я Алмат и я запустил свою собственную соцсеть. Добро пожаловать!", null));
        posts.add(new Post(counter.getAndIncrement(), 1L,
                "Spring Boot — это мощь",
                "Сегодня изучаю Spring Boot и создаю REST API. Это реально очень интересно и полезно!", null));
        posts.add(new Post(counter.getAndIncrement(), 2L,
                "Путешествие в горы",
                "Только что вернулась с Алатауских гор. Природа Казахстана невероятна!", null));
    }

    public List<Post> getAll() { return posts; }

    public List<Post> getLatest(int limit) {
        return posts.stream()
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    public Optional<Post> getById(Long id) {
        return posts.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public List<Post> getByUserId(Long userId) {
        return posts.stream().filter(p -> p.getUserId().equals(userId)).collect(Collectors.toList());
    }

    public Post create(Post post) {
        post.setId(counter.getAndIncrement());
        posts.add(post);
        return post;
    }

    public Optional<Post> update(Long id, Post updated) {
        return getById(id).map(p -> {
            p.setTitle(updated.getTitle());
            p.setContent(updated.getContent());
            p.setImageUrl(updated.getImageUrl());
            return p;
        });
    }

    public Optional<Post> like(Long id) {
        return getById(id).map(p -> {
            p.setLikes(p.getLikes() + 1);
            return p;
        });
    }

    public boolean delete(Long id) {
        return posts.removeIf(p -> p.getId().equals(id));
    }
}

