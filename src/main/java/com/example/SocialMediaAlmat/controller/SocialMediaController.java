package com.example.SocialMediaAlmat.controller;

import com.example.SocialMediaAlmat.model.SocialMedia;
import com.example.SocialMediaAlmat.service.SocialMediaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/social-media")
@CrossOrigin(origins = "*")
public class SocialMediaController {

    private final SocialMediaService socialMediaService;

    public SocialMediaController(SocialMediaService socialMediaService) {
        this.socialMediaService = socialMediaService;
    }

    @GetMapping
    public ResponseEntity<List<SocialMedia>> getAll() {
        return ResponseEntity.ok(socialMediaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return socialMediaService.getById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Соцсеть id=" + id + " не найдена")));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SocialMedia>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(socialMediaService.getByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody SocialMedia sm) {
        return ResponseEntity.status(HttpStatus.CREATED).body(socialMediaService.create(sm));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody SocialMedia sm) {
        return socialMediaService.update(id, sm)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Соцсеть id=" + id + " не найдена")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (socialMediaService.delete(id)) {
            return ResponseEntity.ok(Map.of("message", "Соцсеть id=" + id + " удалена"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Соцсеть id=" + id + " не найдена"));
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Integer>> count() {
        return ResponseEntity.ok(Map.of("total", socialMediaService.getAll().size()));
    }
}

