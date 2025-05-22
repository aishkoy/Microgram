package kg.attractor.instagram.controller.mvc;

import jakarta.validation.Valid;
import kg.attractor.instagram.dto.PostDto;
import kg.attractor.instagram.dto.user.UserDto;
import kg.attractor.instagram.service.FollowService;
import kg.attractor.instagram.service.PostService;
import kg.attractor.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Controller("mvcUser")
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PostService postService;
    private final FollowService followService;

    @GetMapping("{userId}")
    public String userPage(@PathVariable Long userId, Model model) {
        UserDto user = userService.getUserById(userId);

        List<PostDto> posts = new ArrayList<>();
        try {
            posts = postService.getUserPosts(userId);
        } catch (NoSuchElementException ignored) {
        }

        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        return "user/profile";
    }

    @PreAuthorize("@userService.isCurrentUser(#userId)")
    @GetMapping("{userId}/edit")
    public String editProfilePage(@PathVariable Long userId, Model model) {
        UserDto editUserDto = userService.getAuthUser();
        model.addAttribute("editUserDto", editUserDto);
        return "user/editProfile";
    }

    @PreAuthorize("@userService.isCurrentUser(#userId)")
    @PostMapping("{userId}/edit")
    public String updateProfile(
            @PathVariable Long userId,
            @Valid @ModelAttribute("editUserDto") UserDto editUserDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "user/editProfile";
        }

        userService.updateUser(editUserDto);
        redirectAttributes.addFlashAttribute("successMessage", "Профиль успешно обновлен");
        return "redirect:/users/" + userId;
    }

    @PostMapping("{userId}/avatar")
    public String uploadAvatar(@PathVariable Long userId,
                               @RequestParam("file") MultipartFile file) {
        userService.uploadAvatar(file, userId);
        return "redirect:/users/" + userId + "/edit";
    }
}