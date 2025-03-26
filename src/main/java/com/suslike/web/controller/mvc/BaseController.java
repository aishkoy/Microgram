package com.suslike.web.controller.mvc;


import jakarta.validation.Valid;
import com.suslike.web.AuthAdapter;
import com.suslike.web.dto.post.PostDto;
import com.suslike.web.dto.user.UserAddDto;
import com.suslike.web.dto.user.UserDto;
import com.suslike.web.service.FollowService;
import com.suslike.web.service.PostService;
import com.suslike.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller("mainMvc")
@RequestMapping("/")
@RequiredArgsConstructor
public class BaseController {
    private final PostService postService;
    private final UserService userService;
    private final AuthAdapter adapter;
    private final FollowService followService;

    @GetMapping("register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("register")
    public String registerNewUser(@Valid UserAddDto userDto){
        userService.addUser(userDto);
        return "redirect:/login";
    }

    @GetMapping("login")
    public String loginPage() {
        return "login";
    }

    @GetMapping
    public String mainPage(Model model) {
        model.addAttribute("authorizedUserId", adapter.getAuthId());
        model.addAttribute("posts", postService.getPostsFromFeed(adapter.getAuthId()));
        return "feed";
    }

    @GetMapping("/search")
    public String searchPage() {
        return "search";
    }

    @PostMapping("/search")
    public String searchByUsernameOrEmail(String search, Model model) {
        List<UserDto> results = userService.searchByUsernameOrEmail(search);
        model.addAttribute("results", results);
        return "search";
    }

    @GetMapping("/@{username}")
    public String getProfile(Model model, @PathVariable String username) {
        UserDto profileUser = userService.getUserByUsername(username);
        List<PostDto> posts = postService.getAllPostsByOwner(profileUser.getId(), adapter.getAuthId());

        model.addAttribute("authorizedUserId", adapter.getAuthId());
        model.addAttribute("followings", followService.getUserFollowing(adapter.getAuthId()));
        model.addAttribute("user", profileUser);
        model.addAttribute("posts", posts);

        return "profile";
    }
}

