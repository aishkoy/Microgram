package kg.attractor.microgram.controller.mvc;

import kg.attractor.microgram.AuthAdapter;
import kg.attractor.microgram.dto.post.PostDto;
import kg.attractor.microgram.dto.user.UserDto;
import kg.attractor.microgram.dto.user.UserEditDto;
import kg.attractor.microgram.service.FollowService;
import kg.attractor.microgram.service.PostService;
import kg.attractor.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller("UserMvc")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final AuthAdapter adapter;
    private final PostService postService;
    private final UserService userService;
    private final FollowService followService;

    @GetMapping()
    public String getProfile(Model model) {
        UserDto user = adapter.getAuthUser();
        List<PostDto> posts = postService.getAllPostsByOwner(user.getEmail());
        model.addAttribute("user",user);
        model.addAttribute("posts",posts);
        return "profile";
    }

    @GetMapping("/profile/{username}")
    public String getUser(Model model, @PathVariable String username) {
        String authorizedUserEmail = adapter.getAuthUserName();
        UserDto user = userService.getUserByUsername(username);
        List<PostDto> posts = postService.getAllPostsByOwner(user.getEmail());
        if (authorizedUserEmail != null) {
            model.addAttribute("followings", followService.getUserFollowing(authorizedUserEmail));
            System.out.println("followings added");
        }
        model.addAttribute("user",user);
        model.addAttribute("posts",posts);
        return "profile";
    }

    @GetMapping("/search")
    public String searchPage() {
        return "search";
    }

    @PostMapping("/search")
    public String searchByUsernameOrEmail (String search, Model model) {
        List<UserDto> results = userService.searchByUsernameOrEmail(search);
        model.addAttribute("results", results);
        return "search";
    }
    
    @GetMapping("/profile/upload")
    public String uploadProfileAvatarPAge() {
        return "avatar_upl";
        
    }
    
    @PostMapping("/profile/upload")
    public String uploadProfileAvatar(MultipartFile avatar) {
        userService.addAvatar(avatar, adapter.getAuthUserName());
        return "redirect:/user";
    }
    
    @GetMapping("/profile/edit")
    public String editProfile(Model model) {
        model.addAttribute("profile", adapter.getAuthUser());
        return "edit_profile";
    }

    @PostMapping("/profile/edit")
    public String editProfile(UserEditDto user) {
        userService.editUser(user, adapter.getAuthUserName());
        return "redirect:/user";
    }

}
