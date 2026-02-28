package com.example.SocialMediaAlmat.controller;

import com.example.SocialMediaAlmat.model.Post;
import com.example.SocialMediaAlmat.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAll() {
        return ResponseEntity.ok(postService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return postService.getById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Пост id=" + id + " не найден")));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.getByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Post post) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.create(post));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Post post) {
        return postService.update(id, post)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Пост id=" + id + " не найден")));
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<?> like(@PathVariable Long id) {
        return postService.like(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Пост id=" + id + " не найден")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (postService.delete(id)) {
            return ResponseEntity.ok(Map.of("message", "Пост id=" + id + " удалён"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Пост id=" + id + " не найден"));
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Integer>> count() {
        return ResponseEntity.ok(Map.of("total", postService.getAll().size()));
    }
}

