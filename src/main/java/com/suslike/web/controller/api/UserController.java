package com.suslike.web.controller.api;


import com.suslike.web.AuthAdapter;
import com.suslike.web.dto.user.UserDto;
import com.suslike.web.dto.user.UserEditDto;
import com.suslike.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("restUser")
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;
    private final AuthAdapter authAdapter;

    @PostMapping()
    public HttpStatus editUser(UserEditDto edit) {
        userService.editUser(edit, authAdapter.getAuthUserName());
        return HttpStatus.OK;
    }

    @GetMapping("/follower/{username}")
    public ResponseEntity<List<UserDto>> getAllFollowers(@PathVariable String username){
        List<UserDto> followers = userService.getAllFollowers(username);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/followings/{username}")
    public ResponseEntity<List<UserDto>> getAllFollowings(@PathVariable String username){
        List<UserDto> followings = userService.getAllFollowings(username);
        return ResponseEntity.ok(followings);
    }
}