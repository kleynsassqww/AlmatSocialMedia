package kz.enu.SocialMediaAlmat.controller;

import kz.enu.SocialMediaAlmat.model.User;
import kz.enu.SocialMediaAlmat.service.PostService;
import kz.enu.SocialMediaAlmat.service.SocialMediaService;
import kz.enu.SocialMediaAlmat.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    private final UserService userService;
    private final PostService postService;
    private final SocialMediaService socialMediaService;

    public AdminController(UserService userService, PostService postService, SocialMediaService socialMediaService) {
        this.userService = userService;
        this.postService = postService;
        this.socialMediaService = socialMediaService;
    }

    @GetMapping("/admin")
    public String adminPanel(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.isAdmin()) {
            return "redirect:/login"; // или 403 Forbidden
        }
        model.addAttribute("users", userService.getAll());
        model.addAttribute("posts", postService.getAll());
        model.addAttribute("socials", socialMediaService.getAll());
        return "admin";
    }

    @PostMapping("/admin/delete-user")
    public String deleteUser(Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.isAdmin()) {
            userService.delete(id);
        }
        return "redirect:/admin";
    }
}
