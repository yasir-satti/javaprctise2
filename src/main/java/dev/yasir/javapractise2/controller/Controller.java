package dev.yasir.javapractise2.controller;

import dev.yasir.javapractise2.entity.Student;
import dev.yasir.javapractise2.service.StudentsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {

    private final StudentsService studentsService;

    public Controller(StudentsService studentsService) {
        this.studentsService = studentsService;
    }

    @GetMapping("/records")
    public ResponseEntity<List<Student>> getAllRecords() throws Exception {
        List<Student> students = studentsService.retrieveAll();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
}
