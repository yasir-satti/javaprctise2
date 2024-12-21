package dev.yasir.javapractise2.controller;

import dev.yasir.javapractise2.entity.Student;
import dev.yasir.javapractise2.service.StudentsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No Student records were found.");
        }
    }

    @GetMapping("/record/{id}")
    public ResponseEntity<Optional<Student>> getRecordById(@PathVariable int id) throws Exception {
        Optional<Student> student = studentsService.retrieveById(id);
        if (!student.isEmpty()) {
            return new ResponseEntity<>(student, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No Student record with id " +
                            Long.toHexString(id)
                            + " was found.");
        }
    }

    @DeleteMapping("/records")
    public void deleteAllRecords() throws Exception {
        studentsService.deleteAll();
    }
}
