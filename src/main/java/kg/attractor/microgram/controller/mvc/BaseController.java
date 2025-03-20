package kg.attractor.microgram.controller.mvc;


import jakarta.validation.Valid;
import kg.attractor.microgram.dto.user.UserAddDto;
import kg.attractor.microgram.service.PostService;
import kg.attractor.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("BaseMvc")
@RequestMapping("/")
@RequiredArgsConstructor
public class BaseController {
	private final PostService postService;
	private final UserService userService;

	@GetMapping
	public String mainPage(Model model, Authentication auth) {
		if (auth != null) {
			model.addAttribute("posts", postService.getPostsFromFeed(auth.getName()));
		}
		return "base/feed";
		//todo need main page as album mb
	}
	
	@GetMapping("register")
	public String registerPage() {
		return "base/register";
	}
	
	@PostMapping("register")
	public String registerNewUser(@Valid UserAddDto userDto) throws Exception {
		userService.addUser(userDto);
		return "redirect:/login";
	}
	
	@GetMapping("login")
	public String loginPage() {
		return "base/login";
	}
}

