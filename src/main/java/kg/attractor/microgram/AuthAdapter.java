package kg.attractor.microgram;

import kg.attractor.microgram.dto.user.UserDto;
import kg.attractor.microgram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class AuthAdapter {
    private final UserService service;
    public UserDto getAuthUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new NoSuchElementException("user not authorized");
        }
        if (authentication instanceof AnonymousAuthenticationToken){
            throw new IllegalArgumentException("user not authorized");
        }
        String name = authentication.getName();
        return service.getUserByEmail(name);
    }
    
    public String getAuthUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
