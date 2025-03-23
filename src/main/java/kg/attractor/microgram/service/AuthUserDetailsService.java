package kg.attractor.microgram.service;

import kg.attractor.microgram.dao.UserDao;
import kg.attractor.microgram.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class AuthUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDao userModelRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userModelRepository.getUserByEmail(username).orElse(null);
        if (user == null) throw new UsernameNotFoundException(String.format("No user with email %s%n",username));
        return new User(user.getEmail(),user.getPassword(), List.of(new SimpleGrantedAuthority("USER")));
    }
}
