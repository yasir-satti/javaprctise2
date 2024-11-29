package dev.yasir.javapractise2.controller;

import dev.yasir.javapractise2.entity.Student;
import dev.yasir.javapractise2.service.StudentsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
        if (!students.isEmpty()) {
            return new ResponseEntity<>(students, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Student records were found.");
        }
    }

    @GetMapping("/record/{id}")
    public ResponseEntity<Student> getRecordById(int id) throws Exception {
        Student student = studentsService.retrieveById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @DeleteMapping("/records")
    public void deleteAllRecords() throws Exception {
        studentsService.deleteAll();
    }
}
