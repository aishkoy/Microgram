package com.suslike.web.controller.mvc;

import com.suslike.web.AuthAdapter;
import com.suslike.web.dto.comments.CommentDto;
import com.suslike.web.dto.post.PostDto;
import com.suslike.web.service.CommentService;
import com.suslike.web.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller("postMvc")
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;
	private final AuthAdapter adapter;
	private final CommentService commentService;

	@GetMapping()
	public String postPage() {
		return "post";
	}

	@PostMapping()
	public String post(String content, MultipartFile mFile) {
		Long owner = adapter.getAuthUser().getId();
		postService.addPost(content, mFile, owner);
		return "redirect:/@" + adapter.getAuthUserName();
	}

	@GetMapping("{id}")
	public String getPostsById(@PathVariable Long id, Model model){
		String email = adapter.getAuthUserName();
		PostDto post = postService.getPostById(id, adapter.getAuthId());
		List<CommentDto> comments = commentService.getCommentsByPostId(id);
		model.addAttribute("post",post);
		model.addAttribute("comments",comments);
		model.addAttribute("authEmail",email);
		model.addAttribute("authorizedUserId", adapter.getAuthId());
		return "postPage";
	}

	@GetMapping("favorites")
	public String favoritesPage(Model model) {
		model.addAttribute("posts", postService.getFavoritesPostsByOwner(adapter.getAuthId()));
		model.addAttribute("authorizedUserId", adapter.getAuthId());
		return "favorites";
	}
}
