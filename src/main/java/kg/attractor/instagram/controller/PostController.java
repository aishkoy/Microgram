package kg.attractor.instagram.controller;

import kg.attractor.instagram.dto.CommentDto;
import kg.attractor.instagram.dto.PostDto;
import kg.attractor.instagram.dto.user.UserDto;
import kg.attractor.instagram.service.CommentService;
import kg.attractor.instagram.service.LikeService;
import kg.attractor.instagram.service.PostService;
import kg.attractor.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final LikeService likeService;
    private final CommentService commentService;
    private final UserService userService;

    @GetMapping("/create")
    public String showCreatePostForm() {
        return "posts/create";
    }

    @PostMapping("/create")
    public String createPost(
            @RequestParam("image") MultipartFile image,
            @RequestParam("description") String description,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes
    ) {
        try {
            UserDto currentUser = userService.findByUsername(userDetails.getUsername());
            postService.createPost(description, image, currentUser.getId());
            redirectAttributes.addFlashAttribute("successMessage", "Пост успешно создан");
            return "redirect:/profile";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при загрузке изображения: " + e.getMessage());
            return "redirect:/posts/create";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при создании поста: " + e.getMessage());
            return "redirect:/posts/create";
        }
    }

    @GetMapping("/{postId}")
    public String viewPost(@PathVariable Long postId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            PostDto post = postService.getPostById(postId);
            UserDto currentUser = userService.findByUsername(userDetails.getUsername());

            List<CommentDto> comments = commentService.getPostComments(postId);
            Long likesCount = likeService.getPostLikesCount(postId);
            boolean isLiked = likeService.isPostLikedByUser(postId, currentUser.getId());

            model.addAttribute("post", post);
            model.addAttribute("comments", comments);
            model.addAttribute("likesCount", likesCount);
            model.addAttribute("isLiked", isLiked);
            model.addAttribute("currentUser", currentUser);

            return "posts/view";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Пост не найден");
            return "redirect:/";
        }
    }

    @PostMapping("/{postId}/like")
    @ResponseBody
    public String toggleLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            UserDto currentUser = userService.findByUsername(userDetails.getUsername());
            likeService.toggleLike(postId, currentUser.getId());
            Long likesCount = likeService.getPostLikesCount(postId);
            return String.valueOf(likesCount);
        } catch (Exception e) {
            return "error";
        }
    }

    @PostMapping("/{postId}/comment")
    @ResponseBody
    public CommentDto addComment(
            @PathVariable Long postId,
            @RequestParam String content,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        UserDto currentUser = userService.findByUsername(userDetails.getUsername());
        return commentService.addComment(postId, currentUser.getId(), content);
    }

    @DeleteMapping("/comment/{commentId}")
    @ResponseBody
    public String deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        try {
            UserDto currentUser = userService.findByUsername(userDetails.getUsername());
            commentService.deleteComment(commentId, currentUser.getId());
            return "success";
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }

    @DeleteMapping("/{postId}")
    public String deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes
    ) {
        try {
            UserDto currentUser = userService.findByUsername(userDetails.getUsername());
            postService.deletePost(postId, currentUser.getId());
            redirectAttributes.addFlashAttribute("successMessage", "Пост успешно удален");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при удалении поста: " + e.getMessage());
        }
        return "redirect:/profile";
    }

    @GetMapping("/all")
    public String viewAllPosts(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<PostDto> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "posts/list";
    }

}