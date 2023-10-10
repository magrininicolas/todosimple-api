package com.nicolas.simpletodo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nicolas.simpletodo.models.User;
import com.nicolas.simpletodo.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public User create(User user) {
        user.setId(null);
        return userRepository.save(user);
    }

    @Transactional
    public User update(User user) {
        User updatedUser = findById(user.getId());
        updatedUser.setPassword(updatedUser.getPassword());
        return userRepository.save(updatedUser);
    }

    public void delete(Long id) {
        User user = findById(id);
        try {
            userRepository.delete(user);
        } catch (RuntimeException e) {
            throw new RuntimeException("Deletion cannot succeed. Message: " + e.getMessage());
        }
    }
}
