package com.nicolas.simpletodo.controllers;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nicolas.simpletodo.models.User;
import com.nicolas.simpletodo.models.User.CreateUser;
import com.nicolas.simpletodo.models.User.UpdateUser;
import com.nicolas.simpletodo.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable UUID id) {
        User user = userService.findById(id);

        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    @Validated(CreateUser.class)
    public ResponseEntity<Void> createUser(@Valid @RequestBody User user) {
        userService.create(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Validated(UpdateUser.class)
    public ResponseEntity<Void> updateUser(@Valid @RequestBody User user, @PathVariable UUID id) {
        user.setId(id);
        userService.update(user);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
