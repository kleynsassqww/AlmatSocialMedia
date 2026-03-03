package kz.enu.SocialMediaAlmat.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SocialMedia {

    private Long id;

    @NotBlank(message = "Название платформы обязательно")
    private String platform;

    @NotBlank(message = "Ссылка на профиль обязательна")
    private String profileUrl;

    @NotNull(message = "ID пользователя обязателен")
    private Long userId;

    public SocialMedia() {}

    public SocialMedia(Long id, String platform, String profileUrl, Long userId) {
        this.id = id;
        this.platform = platform;
        this.profileUrl = profileUrl;
        this.userId = userId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }

    public String getProfileUrl() { return profileUrl; }
    public void setProfileUrl(String profileUrl) { this.profileUrl = profileUrl; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}

