package com.nicolas.simpletodo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nicolas.simpletodo.models.User;
import com.nicolas.simpletodo.repositories.UserRepository;
import com.nicolas.simpletodo.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));

        return new UserSS(user.getId(), user.getUsername(), user.getPassword(), user.getProfiles());
    }

}
