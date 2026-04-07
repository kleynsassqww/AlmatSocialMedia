package kz.enu.SocialMediaAlmat.controller;

import kz.enu.SocialMediaAlmat.model.User;
import kz.enu.SocialMediaAlmat.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userForm", new User());
        return "register";
    }

    @PostMapping("/register")
    public String handleRegistration(@ModelAttribute("userForm") User userForm, Model model) {
        Map<String, String> errors = new LinkedHashMap<>();

        if (userForm.getName() == null || userForm.getName().trim().length() < 2) {
            errors.put("name", "Имя должно быть не менее 2 символов");
        }
        if (userForm.getEmail() == null || userForm.getEmail().trim().isEmpty()) {
            errors.put("email", "Email не может быть пустым");
        }
        if (userForm.getPassword() == null || userForm.getPassword().trim().length() < 6) {
            errors.put("password", "Пароль должен содержать не менее 6 символов");
        }
        if (userService.existsByEmail(userForm.getEmail())) {
            errors.put("email", "Email уже занят");
        }
        if (userService.existsByName(userForm.getName())) {
            errors.put("name", "Имя уже занято");
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("userForm", userForm);
            return "register";
        }

        String code = String.valueOf((int) (Math.random() * 900000) + 100000);
        User newUser = new User(null, userForm.getName(), userForm.getEmail(), userForm.getBio(), userForm.getPassword());
        newUser.setConfirmationCode(code);
        newUser.setVerified(false);
        userService.create(newUser);

        // Имитация отправки email (в реальном приложении здесь JavaMailSender)
        System.out.println("=======================================================");
        System.out.println("ОТПРАВКА EMAIL на " + newUser.getEmail());
        System.out.println("Ваш код подтверждения: " + code);
        System.out.println("=======================================================");

        return "redirect:/verify?email=" + newUser.getEmail();
    }

    @GetMapping("/verify")
    public String showVerifyForm(String email, Model model) {
        model.addAttribute("email", email);
        return "verify";
    }

    @PostMapping("/verify")
    public String handleVerify(String email, String code, Model model) {
        User user = userService.findByEmail(email).orElse(null);
        if (user == null) {
            model.addAttribute("error", "Пользователь не найден");
            return "verify";
        }
        if (user.isVerified()) {
            return "redirect:/login";
        }
        if (code != null && code.equals(user.getConfirmationCode())) {
            userService.markVerified(user.getId());
            return "redirect:/login?verified=true";
        }
        model.addAttribute("email", email);
        model.addAttribute("error", "Неверный код");
        return "verify";
    }
}
