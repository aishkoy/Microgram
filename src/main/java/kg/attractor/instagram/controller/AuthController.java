package kg.attractor.instagram.controller;

import jakarta.validation.Valid;
import kg.attractor.instagram.dto.user.CreateUserDto;
import kg.attractor.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new CreateUserDto());
        }
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") CreateUserDto createUserDto,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        userService.registerUser(createUserDto);
        return "redirect:/auth/login?registered";
    }

    @GetMapping("/login")
    public String showLoginForm(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "registered", required = false) String registered,
            Model model) {

        if (error != null) {
            model.addAttribute("loginError", "Неверный email или пароль");
        }

        if (registered != null) {
            model.addAttribute("registrationSuccess", "Регистрация прошла успешно! Войдите в систему.");
        }

        return "auth/login";
    }

}