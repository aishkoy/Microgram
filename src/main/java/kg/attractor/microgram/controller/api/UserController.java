package kg.attractor.microgram.controller.api;


import kg.attractor.microgram.AuthAdapter;
import kg.attractor.microgram.dto.user.UserDto;
import kg.attractor.microgram.dto.user.UserEditDto;
import kg.attractor.microgram.service.UserService;
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
    
    
    @PostMapping("")
    public HttpStatus editUser(UserEditDto edit) {
        userService.editUser(edit, authAdapter.getAuthUserName());
        return HttpStatus.OK;
    }

    @GetMapping("/follower/{userUsername}")
    public ResponseEntity<List<UserDto>> getAllFollowers(@PathVariable String userUsername){
        List<UserDto> followers = userService.getAllFollowers(userUsername);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/followings/{userUsername}")
    public ResponseEntity<List<UserDto>> getAllFollowings(@PathVariable String userUsername){

        List<UserDto> followings = userService.getAllFollowings(userUsername);
        return ResponseEntity.ok(followings);
    }
}