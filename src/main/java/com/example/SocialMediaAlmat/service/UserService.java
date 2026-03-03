package com.example.SocialMediaAlmat.service;

import com.example.SocialMediaAlmat.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private final List<User> users = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    public UserService() {
        users.add(new User(counter.getAndIncrement(), "Алмат", "almat@example.com", "Создатель этой платформы"));
        users.add(new User(counter.getAndIncrement(), "Айгерим", "aigrim@example.com", "Люблю читать и путешествовать"));
        users.add(new User(counter.getAndIncrement(), "Даниял", "daniyal@example.com", "Разработчик из Алматы"));
    }

    public List<User> getAll() {
        return users;
    }

    public Optional<User> getById(Long id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    public User create(User user) {
        user.setId(counter.getAndIncrement());
        users.add(user);
        return user;
    }

    public Optional<User> update(Long id, User updated) {
        return getById(id).map(user -> {
            user.setName(updated.getName());
            user.setEmail(updated.getEmail());
            user.setBio(updated.getBio());
            return user;
        });
    }

    public boolean delete(Long id) {
        return users.removeIf(u -> u.getId().equals(id));
    }

    public int count() {
        return users.size();
    }
}

