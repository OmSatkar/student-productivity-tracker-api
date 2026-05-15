package com.springsec.studenttracker.repository;

import com.springsec.studenttracker.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByUser(User user);
    long countByUserAndStatus(User user, AttendanceStatus status);
}