package com.example.SocialMediaAlmat.service;

import com.example.SocialMediaAlmat.model.SocialMedia;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class SocialMediaService {

    private final List<SocialMedia> list    = new ArrayList<>();
    private final AtomicLong        counter = new AtomicLong(1);

    public SocialMediaService() {
        list.add(new SocialMedia(counter.getAndIncrement(), "Instagram", "https://instagram.com/almat_dev", 1L, 1500));
        list.add(new SocialMedia(counter.getAndIncrement(), "Twitter",   "https://twitter.com/almat_dev",   1L,  800));
        list.add(new SocialMedia(counter.getAndIncrement(), "TikTok",    "https://tiktok.com/@almat_dev",   1L, 5200));
        list.add(new SocialMedia(counter.getAndIncrement(), "Instagram", "https://instagram.com/aizat_kz",  2L, 3200));
    }

    public List<SocialMedia> getAll() { return list; }

    public Optional<SocialMedia> getById(Long id) {
        return list.stream().filter(s -> s.getId().equals(id)).findFirst();
    }

    public List<SocialMedia> getByUserId(Long userId) {
        return list.stream().filter(s -> s.getUserId().equals(userId)).collect(Collectors.toList());
    }

    public SocialMedia create(SocialMedia sm) {
        sm.setId(counter.getAndIncrement());
        list.add(sm);
        return sm;
    }

    public Optional<SocialMedia> update(Long id, SocialMedia updated) {
        return getById(id).map(s -> {
            s.setPlatform(updated.getPlatform());
            s.setProfileUrl(updated.getProfileUrl());
            s.setFollowers(updated.getFollowers());
            return s;
        });
    }

    public boolean delete(Long id) {
        return list.removeIf(s -> s.getId().equals(id));
    }
}

