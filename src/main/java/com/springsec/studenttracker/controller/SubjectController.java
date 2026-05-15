package com.springsec.studenttracker.controller;

import com.springsec.studenttracker.entity.*;
import com.springsec.studenttracker.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController extends BaseController {

    private final SubjectRepository subjectRepository;

    public SubjectController(UserRepository userRepository, SubjectRepository subjectRepository) {
        super(userRepository);
        this.subjectRepository = subjectRepository;
    }

    @PostMapping
    public Subject addSubject(@RequestBody Subject subject, Authentication auth) {
        subject.setUser(getCurrentUser(auth));
        return subjectRepository.save(subject);
    }

    @GetMapping
    public List<Subject> getSubjects(Authentication auth) {
        return subjectRepository.findByUser(getCurrentUser(auth));
    }

    @PutMapping("/{id}")
    public Subject updateSubject(@PathVariable Long id, @RequestBody Subject newSubject, Authentication auth) {
        Subject subject = subjectRepository.findById(id).orElseThrow();
        subject.setSubjectName(newSubject.getSubjectName());
        subject.setTeacherName(newSubject.getTeacherName());
        subject.setUser(getCurrentUser(auth));
        return subjectRepository.save(subject);
    }

    @DeleteMapping("/{id}")
    public String deleteSubject(@PathVariable Long id) {
        subjectRepository.deleteById(id);
        return "Subject deleted successfully";
    }
}