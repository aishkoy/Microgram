package com.suslike.web.controller.api;

import com.suslike.web.dto.FollowDto;
import com.suslike.web.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController("restFollow")
@RequiredArgsConstructor
@RequestMapping("api/follows")
public class FollowController {
	private final FollowService followService;
	
	@PostMapping
	public void follow(@RequestBody FollowDto dto) {
		followService.createFollow(dto);
	}

	@DeleteMapping()
	public void unfollow (@RequestBody FollowDto dto) {
		followService.deleteFollow(dto);
	}

	@GetMapping("/count/{userId}")
	public ResponseEntity<Map<String, Integer>> getFollowCounts(@PathVariable Long userId) {
		Map<String, Integer> counts = new HashMap<>();
		counts.put("followers", followService.getFollowersCount(userId));
		counts.put("following", followService.getFollowingCount(userId));
		return ResponseEntity.ok(counts);
	}
}
