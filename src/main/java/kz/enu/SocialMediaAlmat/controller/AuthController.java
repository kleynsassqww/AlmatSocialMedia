package kz.enu.SocialMediaAlmat.controller;

import kz.enu.SocialMediaAlmat.model.User;
import kz.enu.SocialMediaAlmat.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("loginForm", new User());
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@ModelAttribute("loginForm") User loginForm, Model model, HttpSession session) {
        Optional<User> u = userService.authenticate(loginForm.getEmail(), loginForm.getPassword());
        if (u.isPresent()) {
            session.setAttribute("user", u.get());
            return "redirect:/";
        }
        model.addAttribute("error", "Неверный email или пароль");
        return "login";
    }

    @RequestMapping(path = "/api/login")
    public ResponseEntity<Map<String, String>> apiLogin(@RequestBody Map<String, String> payload, HttpSession session) {
        String email = payload.get("email");
        String password = payload.get("password");
        Optional<User> u = userService.authenticate(email, password);
        if (u.isPresent()) {
            session.setAttribute("user", u.get());
            return ResponseEntity.ok(Map.of("message", "ok"));
        }
        return ResponseEntity.status(401).body(Map.of("error", "invalid_credentials"));
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
