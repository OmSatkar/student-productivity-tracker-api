package com.springsec.studenttracker.controller;

import com.springsec.studenttracker.entity.*;
import com.springsec.studenttracker.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController extends BaseController {

    private final SubjectRepository subjectRepository;
    private final TaskRepository taskRepository;
    private final AttendanceRepository attendanceRepository;

    public DashboardController(
            UserRepository userRepository,
            SubjectRepository subjectRepository,
            TaskRepository taskRepository,
            AttendanceRepository attendanceRepository
    ) {
        super(userRepository);
        this.subjectRepository = subjectRepository;
        this.taskRepository = taskRepository;
        this.attendanceRepository = attendanceRepository;
    }

    @GetMapping
    public Map<String, Object> dashboard(Authentication auth) {
        User user = getCurrentUser(auth);

        long totalSubjects = subjectRepository.findByUser(user).size();
        long pendingTasks = taskRepository.countByUserAndStatus(user, TaskStatus.PENDING);
        long completedTasks = taskRepository.countByUserAndStatus(user, TaskStatus.COMPLETED);

        long present = attendanceRepository.countByUserAndStatus(user, AttendanceStatus.PRESENT);
        long absent = attendanceRepository.countByUserAndStatus(user, AttendanceStatus.ABSENT);
        long total = present + absent;

        double percentage = total == 0 ? 0 : (present * 100.0) / total;

        Map<String, Object> data = new HashMap<>();
        data.put("totalSubjects", totalSubjects);
        data.put("pendingTasks", pendingTasks);
        data.put("completedTasks", completedTasks);
        data.put("attendancePercentage", percentage);

        return data;
    }
}