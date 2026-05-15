package com.springsec.studenttracker.controller;

import com.springsec.studenttracker.entity.*;
import com.springsec.studenttracker.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController extends BaseController {

    private final AttendanceRepository attendanceRepository;
    private final SubjectRepository subjectRepository;

    public AttendanceController(UserRepository userRepository, AttendanceRepository attendanceRepository, SubjectRepository subjectRepository) {
        super(userRepository);
        this.attendanceRepository = attendanceRepository;
        this.subjectRepository = subjectRepository;
    }

    @PostMapping
    public Attendance markAttendance(@RequestBody Attendance attendance, Authentication auth) {
        User user = getCurrentUser(auth);

        Subject subject = subjectRepository.findById(attendance.getSubject().getId())
                .orElseThrow();

        attendance.setUser(user);
        attendance.setSubject(subject);

        return attendanceRepository.save(attendance);
    }

    @GetMapping
    public List<Attendance> getAttendance(Authentication auth) {
        return attendanceRepository.findByUser(getCurrentUser(auth));
    }
}