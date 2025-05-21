package kg.attractor.instagram.controller;

import kg.attractor.instagram.dto.user.DisplayUserDto;
import kg.attractor.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    @GetMapping
    public String profilePage(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        DisplayUserDto userDto = userService.getByUsername(currentUser.getUsername());
        model.addAttribute("user", userDto);
        return "user/profile";
    }
}
