package com.example.SocialMediaAlmat.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;

public class SocialMedia {

    private Long id;

    @NotBlank(message = "Название платформы обязательно")
    @Pattern(
        regexp = "Instagram|Twitter|TikTok|YouTube|LinkedIn|Facebook|VK|Telegram",
        message = "Допустимые платформы: Instagram, Twitter, TikTok, YouTube, LinkedIn, Facebook, VK, Telegram"
    )
    private String platform;

    @NotBlank(message = "Ссылка на профиль обязательна")
    private String profileUrl;

    @NotNull(message = "ID пользователя обязателен")
    private Long userId;

    @Min(value = 0, message = "Подписчики не могут быть отрицательными")
    private int followers;

    private LocalDateTime linkedAt;

    public SocialMedia() {}

    public SocialMedia(Long id, String platform, String profileUrl, Long userId, int followers) {
        this.id         = id;
        this.platform   = platform;
        this.profileUrl = profileUrl;
        this.userId     = userId;
        this.followers  = followers;
        this.linkedAt   = LocalDateTime.now();
    }

    public Long          getId()                    { return id; }
    public void          setId(Long id)             { this.id = id; }

    public String        getPlatform()              { return platform; }
    public void          setPlatform(String p)      { this.platform = p; }

    public String        getProfileUrl()            { return profileUrl; }
    public void          setProfileUrl(String u)    { this.profileUrl = u; }

    public Long          getUserId()                { return userId; }
    public void          setUserId(Long u)          { this.userId = u; }

    public int           getFollowers()             { return followers; }
    public void          setFollowers(int f)        { this.followers = f; }

    public LocalDateTime getLinkedAt()              { return linkedAt; }
    public void          setLinkedAt(LocalDateTime t) { this.linkedAt = t; }
}

