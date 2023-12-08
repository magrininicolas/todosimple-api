package com.nicolas.simpletodo.services;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nicolas.simpletodo.models.User;
import com.nicolas.simpletodo.models.enums.ProfileEnum;
import com.nicolas.simpletodo.repositories.UserRepository;
import com.nicolas.simpletodo.services.exceptions.DataBindingViolationException;
import com.nicolas.simpletodo.services.exceptions.ObjectNotFoundException;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("User not found"));
    }

    @Transactional
    public User create(User user) {
        user.setId(null);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
        return userRepository.save(user);
    }

    @Transactional
    public User update(User user) {
        User updatedUser = findById(user.getId());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(updatedUser);
    }

    public void delete(Long id) {
        User user = findById(id);
        try {
            userRepository.delete(user);
        } catch (RuntimeException e) {
            throw new DataBindingViolationException("Deletion cannot succeed. Message: " + e.getMessage());
        }
    }
}
