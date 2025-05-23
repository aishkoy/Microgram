package kg.attractor.instagram.controller.mvc;

import kg.attractor.instagram.dto.PostDto;
import kg.attractor.instagram.service.PostService;
import kg.attractor.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {
    private final UserService userService;
    private final PostService postService;

    @GetMapping("search")
    public String searchPage() {
        return "search";
    }

    @GetMapping()
    public String viewAllPosts(Model model) {
        List<PostDto> posts = new ArrayList<>();
        try{
            posts = postService.getAllPosts();
        } catch (NoSuchElementException ignored) {
        }
        model.addAttribute("posts", posts);
        return "posts/list";
    }

    @GetMapping("feed")
    public String viewFollowingPosts(Model model) {
        List<PostDto> posts = new ArrayList<>();
        try{
            posts = postService.getFeedPosts(userService.getAuthId());
        } catch (NoSuchElementException ignored) {
        }
        model.addAttribute("posts", posts);
        return "posts/list";
    }
}
