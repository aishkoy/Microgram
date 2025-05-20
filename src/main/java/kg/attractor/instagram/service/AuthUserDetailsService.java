package kg.attractor.instagram.service;

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
    public UserDetails loadUserByUsername(String loginIdentifier) throws UsernameNotFoundException {
        User user;
        
        if (loginIdentifier.contains("@")) {
            user = userRepository.findByEmail(loginIdentifier)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + loginIdentifier));
        } else {
            user = userRepository.findByUsername(loginIdentifier)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + loginIdentifier));
        }

        return new CustomUserDetails(user);
    }
}