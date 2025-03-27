package com.suslike.web.controller.mvc;

import com.suslike.web.AuthAdapter;
import com.suslike.web.dto.user.UserEditDto;
import com.suslike.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller("profileMvc")
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final AuthAdapter adapter;
    private final UserService userService;
    
    @GetMapping("avatar")
    public String uploadProfileAvatarPAge() {
        return "avatar_upl";
    }
    
    @PostMapping("avatar")
    public String uploadProfileAvatar(MultipartFile avatar) {
        userService.addAvatar(avatar, adapter.getAuthUserName());
        return "redirect:/@" + adapter.getAuthUserName();
    }
    
    @GetMapping()
    public String editProfile(Model model) {
        model.addAttribute("profile", adapter.getAuthUser());
        return "edit_profile";
    }

    @PostMapping()
    public String editProfile(UserEditDto user) {
        userService.editUser(user, adapter.getAuthUserName());
        return "redirect:/@" + adapter.getAuthUserName();
    }

}
