package com.nicolas.simpletodo.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nicolas.simpletodo.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

}
