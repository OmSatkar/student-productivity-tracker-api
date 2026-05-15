package com.springsec.studenttracker.repository;

import com.springsec.studenttracker.entity.Subject;
import com.springsec.studenttracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByUser(User user);
}