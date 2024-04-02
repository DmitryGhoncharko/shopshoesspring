package com.example.shopshoesspring.auth.config.auth;

import com.example.shopshoesspring.entity.User;
import com.example.shopshoesspring.repository.UserRepository;
import com.example.shopshoesspring.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userLogin) throws UsernameNotFoundException {
        Optional<User> userOptional = userService.findUserByUserLogin(userLogin);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            return org.springframework.security.core.userdetails.User.builder().
                    username(user.getUserLogin()).
                    password(user.getUserPassword()).
                    roles(user.getUserRole().getRoleName()).
                    build();
        }else{
            throw new UsernameNotFoundException(userLogin);
        }

    }
}
