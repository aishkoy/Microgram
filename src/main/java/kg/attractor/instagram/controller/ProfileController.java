package kg.attractor.instagram.controller;

import jakarta.validation.Valid;
import kg.attractor.instagram.dto.PostDto;
import kg.attractor.instagram.dto.user.EditUserDto;
import kg.attractor.instagram.dto.user.UserDto;
import kg.attractor.instagram.service.PostService;
import kg.attractor.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;
    private final PostService postService;

    @GetMapping
    public String profilePage(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        UserDto user = userService.findByUsername(currentUser.getUsername());
        List<PostDto> posts = postService.getUserPosts(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        model.addAttribute("isCurrentUser", true);

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