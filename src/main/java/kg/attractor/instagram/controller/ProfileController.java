package kg.attractor.instagram.controller;

import kg.attractor.instagram.dto.user.DisplayUserDto;
import kg.attractor.instagram.dto.user.EditUserDto;
import kg.attractor.instagram.service.UserService;
import kg.attractor.instagram.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;
    private final FileUtil fileUtil;

    @GetMapping
    public String profilePage(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        DisplayUserDto userDto = userService.getByUsername(currentUser.getUsername());
        model.addAttribute("user", userDto);
        return "user/profile";
    }

    @GetMapping("/edit")
    public String editProfilePage(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        EditUserDto editUserDto = userService.getEditUserDto(currentUser.getUsername());
        model.addAttribute("editUserDto", editUserDto);
        return "user/editProfile";
    }

    @PostMapping("/edit")
    public String updateProfile(
            @AuthenticationPrincipal UserDetails currentUser,
            @Valid @ModelAttribute("editUserDto") EditUserDto editUserDto,
            BindingResult bindingResult,
            @RequestParam("avatarFile") MultipartFile avatarFile,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "user/editProfile";
        }

        userService.updateUserWithAvatar(currentUser.getUsername(), editUserDto, avatarFile);
        redirectAttributes.addFlashAttribute("successMessage", "Профиль успешно обновлен");
        return "redirect:/profile";
    }
}