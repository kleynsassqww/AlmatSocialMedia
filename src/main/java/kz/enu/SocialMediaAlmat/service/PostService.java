package kz.enu.SocialMediaAlmat.service;

import kz.enu.SocialMediaAlmat.model.Post;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final List<Post> posts = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    public PostService() {
        posts.add(new Post(counter.getAndIncrement(), "Добро пожаловать в AlmatSocial!", "Это первый пост на нашей платформе. Рад всех приветствовать!", 1L));
        posts.add(new Post(counter.getAndIncrement(), "Весна в Алматы", "Алматы весной — это что-то особенное. Цветут яблони, горы покрыты снегом...", 2L));
        posts.add(new Post(counter.getAndIncrement(), "Советы по программированию", "Spring Boot — отличный фреймворк для создания REST API. Начните с малого и двигайтесь вперёд!", 1L));
    }

    public List<Post> getAll() {
        return posts;
    }

    public Optional<Post> getById(Long id) {
        return posts.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public List<Post> getByUserId(Long userId) {
        return posts.stream().filter(p -> p.getUserId().equals(userId)).collect(Collectors.toList());
    }

    public Post create(Post post) {
        post.setId(counter.getAndIncrement());
        post.setLikes(0);
        posts.add(post);
        return post;
    }

    public Optional<Post> update(Long id, Post updated) {
        return getById(id).map(post -> {
            post.setTitle(updated.getTitle());
            post.setContent(updated.getContent());
            return post;
        });
    }

    public boolean delete(Long id) {
        return posts.removeIf(p -> p.getId().equals(id));
    }

    public Optional<Post> like(Long id) {
        return getById(id).map(post -> {
            post.setLikes(post.getLikes() + 1);
            return post;
        });
    }

    public List<Post> getLatest(int n) {
        int size = posts.size();
        return posts.subList(Math.max(0, size - n), size);
    }

    public int count() {
        return posts.size();
    }
}

