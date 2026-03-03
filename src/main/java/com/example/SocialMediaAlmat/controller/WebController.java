package com.example.SocialMediaAlmat.controller;

import com.example.SocialMediaAlmat.service.PostService;
import com.example.SocialMediaAlmat.service.SocialMediaService;
import com.example.SocialMediaAlmat.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    private final UserService userService;
    private final PostService postService;
    private final SocialMediaService socialMediaService;

    public WebController(UserService userService, PostService postService, SocialMediaService socialMediaService) {
        this.userService = userService;
        this.postService = postService;
        this.socialMediaService = socialMediaService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("userCount", userService.count());
        model.addAttribute("postCount", postService.count());
        model.addAttribute("socialCount", socialMediaService.count());
        model.addAttribute("latestPosts", postService.getLatest(6));
        model.addAttribute("users", userService.getAll());
        model.addAttribute("socials", socialMediaService.getAll());
        return "index";
    }
}
