package kz.enu.SocialMediaAlmat.controller;

import kz.enu.SocialMediaAlmat.model.SocialMedia;
import kz.enu.SocialMediaAlmat.service.SocialMediaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/social-media")
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
    public ResponseEntity<SocialMedia> getById(@PathVariable Long id) {
        return socialMediaService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SocialMedia>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(socialMediaService.getByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<SocialMedia> create(@Valid @RequestBody SocialMedia sm) {
        SocialMedia created = socialMediaService.create(sm);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        if (socialMediaService.delete(id)) {
            return ResponseEntity.ok(Map.of("message", "Соцсеть удалена"));
        }
        return ResponseEntity.notFound().build();
    }
}

