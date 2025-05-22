package kg.attractor.instagram.controller.mvc;

import kg.attractor.instagram.dto.CommentDto;
import kg.attractor.instagram.dto.PostDto;
import kg.attractor.instagram.dto.user.UserDto;
import kg.attractor.instagram.service.CommentService;
import kg.attractor.instagram.service.LikeService;
import kg.attractor.instagram.service.PostService;
import kg.attractor.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.NoSuchElementException;

@Controller("mvcPost")
@RequestMapping("posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final LikeService likeService;
    private final CommentService commentService;
    private final UserService userService;

    @GetMapping("create")
    public String showCreatePostForm() {
        return "posts/create";
    }

    @PostMapping("create")
    public String createPost(
            @RequestParam("image") MultipartFile image,
            @RequestParam("description") String description,
            RedirectAttributes redirectAttributes
    ) {
        try {
            UserDto currentUser = userService.getAuthUser();
            postService.createPost(description, image, currentUser.getId());
            redirectAttributes.addFlashAttribute("successMessage", "Пост успешно создан");
            return "redirect:/profile";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при создании поста: " + e.getMessage());
            return "redirect:/posts/create";
        }
    }

    @GetMapping("{postId}")
    public String viewPost(@PathVariable Long postId, Model model) {
        try {
            PostDto post = postService.getPostById(postId);
            UserDto currentUser = userService.getAuthUser();

            List<CommentDto> comments = List.of();
            try{
                comments = commentService.getPostComments(postId);
            } catch (NoSuchElementException ignored){}

            boolean isLiked = likeService.isPostLikedByUser(postId, currentUser.getId());

            model.addAttribute("post", post);
            model.addAttribute("comments", comments);
            model.addAttribute("isLiked", isLiked);
            model.addAttribute("currentUser", currentUser);

            return "posts/view";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Пост не найден");
            return "redirect:/";
        }
    }

    @PostMapping("{postId}/like")
    public String toggleLike(@PathVariable Long postId) {
        try {
            UserDto currentUser = userService.getAuthUser();
            likeService.toggleLike(postId, currentUser.getId());
            Long likesCount = likeService.getPostLikesCount(postId);
            return String.valueOf(likesCount);
        } catch (Exception e) {
            return "error";
        }
    }

    @PostMapping("{postId}/comment")
    public CommentDto addComment(
            @PathVariable Long postId,
            @RequestParam String content
    ) {
        UserDto currentUser = userService.getAuthUser();
        return commentService.addComment(postId, currentUser.getId(), content);
    }

    @DeleteMapping("comment/{commentId}")
    public String deleteComment(
            @PathVariable Long commentId
    ) {
        try {
            UserDto currentUser = userService.getAuthUser();
            commentService.deleteComment(commentId, currentUser.getId());
            return "success";
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }

    @DeleteMapping("{postId}")
    public String deletePost(
            @PathVariable Long postId,
            RedirectAttributes redirectAttributes
    ) {
        try {
            UserDto currentUser = userService.getAuthUser();
            postService.deletePost(postId, currentUser.getId());
            redirectAttributes.addFlashAttribute("successMessage", "Пост успешно удален");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при удалении поста: " + e.getMessage());
        }
        return "redirect:/profile";
    }

    @GetMapping()
    public String viewAllPosts(Model model) {
        List<PostDto> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "posts/list";
    }
}