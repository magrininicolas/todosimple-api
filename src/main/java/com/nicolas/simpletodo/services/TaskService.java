package com.nicolas.simpletodo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nicolas.simpletodo.models.Task;
import com.nicolas.simpletodo.models.User;
import com.nicolas.simpletodo.repositories.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public List<Task> findAllByUserId(Long id) {
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
        updatedTask.setDescription(updatedTask.getDescription());

        return taskRepository.save(updatedTask);
    }

    public void deleteTask(Long id) {
        Task deletedTask = findById(id);
        try {
            taskRepository.delete(deletedTask);
        } catch (RuntimeException e) {
            throw new RuntimeException("Deletion cannot succeed. Message: " + e.getMessage());
        }
    }

}
