package com.example.SocialMediaAlmat.service;

import com.example.SocialMediaAlmat.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private final List<User> users   = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    public UserService() {
        users.add(new User(counter.getAndIncrement(), "almat_dev",  "almat@example.com",   "Привет! Я Алмат — разработчик и создатель этой соцсети."));
        users.add(new User(counter.getAndIncrement(), "aizat_kz",   "aizat@example.com",   "Люблю путешествовать и программировать."));
        users.add(new User(counter.getAndIncrement(), "daniyar_777","daniyar@example.com",  "Fullstack-разработчик из Алматы."));
    }

    public List<User> getAll() { return users; }

    public Optional<User> getById(Long id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    public boolean existsByEmail(String email) {
        return users.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

    public User create(User user) {
        user.setId(counter.getAndIncrement());
        users.add(user);
        return user;
    }

    public Optional<User> update(Long id, User updated) {
        return getById(id).map(u -> {
            u.setUsername(updated.getUsername());
            u.setEmail(updated.getEmail());
            u.setBio(updated.getBio());
            return u;
        });
    }

    public boolean delete(Long id) {
        return users.removeIf(u -> u.getId().equals(id));
    }
}

