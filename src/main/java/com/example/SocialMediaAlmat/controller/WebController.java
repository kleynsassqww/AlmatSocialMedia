package com.example.SocialMediaAlmat.controller;

import com.example.SocialMediaAlmat.service.PostService;
import com.example.SocialMediaAlmat.service.SocialMediaService;
import com.example.SocialMediaAlmat.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    private final UserService        userService;
    private final PostService        postService;
    private final SocialMediaService socialMediaService;

    public WebController(UserService u, PostService p, SocialMediaService s) {
        this.userService        = u;
        this.postService        = p;
        this.socialMediaService = s;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("users",       userService.getAll());
        model.addAttribute("posts",       postService.getAll());
        model.addAttribute("socials",     socialMediaService.getAll());
        model.addAttribute("latestPosts", postService.getLatest(3));
        model.addAttribute("userCount",   userService.getAll().size());
        model.addAttribute("postCount",   postService.getAll().size());
        model.addAttribute("socialCount", socialMediaService.getAll().size());
        model.addAttribute("platforms",
                new String[]{"Instagram","Twitter","TikTok","YouTube","LinkedIn","Facebook","VK","Telegram"});
        return "index";
    }
}

