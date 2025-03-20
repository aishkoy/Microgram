package kg.attractor.microgram.controller.api;

import kg.attractor.microgram.dto.FollowDto;
import kg.attractor.microgram.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController("restFollow")
@RequiredArgsConstructor
@RequestMapping("api/follow")
public class FollowController {
	private final FollowService followService;
	
	@PostMapping
	public void follow(@RequestBody FollowDto dto) {
		followService.createFollow(dto);
	}

	@DeleteMapping("unfollow")
	public void unfollow (@RequestBody FollowDto dto) {
		followService.deleteFollow(dto);
	}
}
