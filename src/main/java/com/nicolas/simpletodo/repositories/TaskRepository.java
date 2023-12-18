package com.nicolas.simpletodo.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nicolas.simpletodo.models.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findByUser_Id(UUID id);

    // @Query(value = " SELECT t FROM Task t WHERE t.user.id = :id")
    // List<Task> findByUser_Id(@Param("id") Long id);

    // @Query(value = "SELECT * FROM task t WHERE t.user_id = :id", nativeQuery =
    // true)
    // List<Task> findByUser_Id(@Param("id") Long id);
}
