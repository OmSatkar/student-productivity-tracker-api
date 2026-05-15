package com.springsec.studenttracker.controller;

import com.springsec.studenttracker.entity.*;
import com.springsec.studenttracker.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController extends BaseController {

    private final TaskRepository taskRepository;
    private final SubjectRepository subjectRepository;

    public TaskController(UserRepository userRepository, TaskRepository taskRepository, SubjectRepository subjectRepository) {
        super(userRepository);
        this.taskRepository = taskRepository;
        this.subjectRepository = subjectRepository;
    }

    @PostMapping
    public Task addTask(@RequestBody Task task, Authentication auth) {
        User user = getCurrentUser(auth);

        Subject subject = subjectRepository.findById(task.getSubject().getId())
                .orElseThrow();

        task.setUser(user);
        task.setSubject(subject);
        task.setStatus(TaskStatus.PENDING);

        return taskRepository.save(task);
    }

    @GetMapping
    public List<Task> getTasks(Authentication auth) {
        return taskRepository.findByUser(getCurrentUser(auth));
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task newTask) {
        Task task = taskRepository.findById(id).orElseThrow();

        task.setTitle(newTask.getTitle());
        task.setDescription(newTask.getDescription());
        task.setDueDate(newTask.getDueDate());
        task.setPriority(newTask.getPriority());

        return taskRepository.save(task);
    }

    @PatchMapping("/{id}/complete")
    public String completeTask(@PathVariable Long id) {
        Task task = taskRepository.findById(id).orElseThrow();
        task.setStatus(TaskStatus.COMPLETED);
        taskRepository.save(task);

        return "Task marked as completed";
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "Task deleted successfully";
    }
}