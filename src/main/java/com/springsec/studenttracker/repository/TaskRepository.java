package com.springsec.studenttracker.repository;

import com.springsec.studenttracker.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
    long countByUserAndStatus(User user, TaskStatus status);
}