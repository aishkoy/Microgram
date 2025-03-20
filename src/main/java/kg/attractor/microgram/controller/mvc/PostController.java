package kg.attractor.microgram.controller.mvc;

import kg.attractor.microgram.AuthAdapter;
import kg.attractor.microgram.dto.comments.CommentDto;
import kg.attractor.microgram.dto.post.PostDto;
import kg.attractor.microgram.service.CommentService;
import kg.attractor.microgram.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller("PostMvc")
@RequestMapping("/")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;
	private final AuthAdapter authAdapter;
	private final CommentService commentService;

	@GetMapping("post")
	public String postPage() {
		return "base/post";
	}

	@GetMapping("{id}")
	public String getPostsById(@PathVariable Long id, Model model){
		String email = authAdapter.getAuthUserName();
		PostDto post = postService.getPostById(id);
		List<CommentDto> comments = commentService.getCommentsByPostId(id);
		model.addAttribute("post",post);
		model.addAttribute("comments",comments);
		model.addAttribute("authEmail",email);
		return "base/postPage";
	}
	
	@PostMapping("post")
	public String post(String content, MultipartFile mFile) {
		String owner = authAdapter.getAuthUser().getEmail();
		postService.addPost(content, mFile, owner);
		return "redirect:/user";
	}
}
