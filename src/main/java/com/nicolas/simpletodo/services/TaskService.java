package com.nicolas.simpletodo.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nicolas.simpletodo.models.Task;
import com.nicolas.simpletodo.models.User;
import com.nicolas.simpletodo.repositories.TaskRepository;
import com.nicolas.simpletodo.services.exceptions.DataBindingViolationException;
import com.nicolas.simpletodo.services.exceptions.ObjectNotFoundException;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    private final UserService userService;

    TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public Task findById(UUID id) {
        return taskRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Task not found"));
    }

    public List<Task> findAllByUserId(UUID id) {
        userService.findById(id);

        return taskRepository.findByUser_Id(id);
    }

    @Transactional
    public Task createTask(Task task) {
        User user = userService.findById(task.getUser().getId());

        task.setId(null);
        task.setUser(user);
        return taskRepository.save(task);
    }

    @Transactional
    public Task updateTask(Task task) {
        Task updatedTask = findById(task.getId());
        updatedTask.setDescription(task.getDescription());

        return taskRepository.save(updatedTask);
    }

    @Transactional
    public void deleteTask(UUID id) {
        Task deletedTask = findById(id);
        try {
            taskRepository.delete(deletedTask);
        } catch (RuntimeException e) {
            throw new DataBindingViolationException("Deletion cannot succeed. Message: " + e.getMessage());
        }
    }

}
