package kz.enu.SocialMediaAlmat.service;

import kz.enu.SocialMediaAlmat.model.SocialMedia;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class SocialMediaService {

    private final List<SocialMedia> accounts = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    public SocialMediaService() {
        accounts.add(new SocialMedia(counter.getAndIncrement(), "Instagram", "https://instagram.com/almat", 1L));
        accounts.add(new SocialMedia(counter.getAndIncrement(), "GitHub", "https://github.com/almat", 1L));
        accounts.add(new SocialMedia(counter.getAndIncrement(), "TikTok", "https://tiktok.com/@aigrim", 2L));
        accounts.add(new SocialMedia(counter.getAndIncrement(), "LinkedIn", "https://linkedin.com/in/daniyal", 3L));
    }

    public List<SocialMedia> getAll() {
        return accounts;
    }

    public Optional<SocialMedia> getById(Long id) {
        return accounts.stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    public List<SocialMedia> getByUserId(Long userId) {
        return accounts.stream().filter(a -> a.getUserId().equals(userId)).collect(Collectors.toList());
    }

    public SocialMedia create(SocialMedia sm) {
        sm.setId(counter.getAndIncrement());
        accounts.add(sm);
        return sm;
    }

    public boolean delete(Long id) {
        return accounts.removeIf(a -> a.getId().equals(id));
    }

    public int count() {
        return accounts.size();
    }
}

