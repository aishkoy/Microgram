package com.suslike.web.service;

import com.suslike.web.dao.UserDao;
import com.suslike.web.models.UserModel;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class AuthUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDao userModelRepository;

    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String login) {

        Optional<UserModel> userByEmail = userModelRepository.getUserByEmail(login);
        Optional<UserModel> userByUsername;

        if (userByEmail.isEmpty()) {
            userByUsername = userModelRepository.getUserByUsername(login);
        } else {
            userByUsername = Optional.empty();
        }

        UserModel user = userByEmail.orElseGet(() ->
                userByUsername.orElseThrow(() ->
                        new UsernameNotFoundException(String.format("User not found with login: %s", login))
                )
        );

        List<String> roles = userModelRepository.getUserRoles(user.getId());
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
        return new User(user.getUsername(),user.getPassword(), authorities);
    }
}
