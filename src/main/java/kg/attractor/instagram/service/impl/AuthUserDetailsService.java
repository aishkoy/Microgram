package kg.attractor.instagram.service.impl;

import kg.attractor.instagram.entity.User;
import kg.attractor.instagram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user;
        
        if (login.contains("@")) {
            user = userRepository.findByEmail(login)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + login));
        } else {
            user = userRepository.findByUsername(login)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + login));
        }

        return new CustomUserDetails(user);
    }
}