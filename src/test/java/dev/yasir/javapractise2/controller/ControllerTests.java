package dev.yasir.javapractise2.controller;

import dev.yasir.javapractise2.entity.Student;
import dev.yasir.javapractise2.repository.StudentsRepository;
import dev.yasir.javapractise2.service.StudentsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@WebMvcTest
public class ControllerTests {

    @Mock
    private StudentsService studentsService;

    @MockBean
    private StudentsRepository studentsRepository;

    @InjectMocks
    private Controller controller;

    List<Student> students = Arrays.asList(
            new Student("Jack", "Smith", 34),
            new Student("Maya", "Alan", 54));

    @Test
    void shouldReturnAllRecords() throws Exception {

        /*
        Story:

        Given there are a number of saved student records
        When I try to retrieve these records
        Then a list of these records is returned
        */

        when(studentsService.retrieveAll()).thenReturn(students);

        ResponseEntity<List<Student>> response = controller.getAllRecords();
        assertThat(students).isEqualTo(response.getBody());
    }

    @Test
    void shouldReturnRecord_GivenValidRecordId() throws Exception {

        /*
        //TODO
        Story:

        Given a valid student record is in database
        When i try to retrieve this record using its id
        Then the record is returned
        */

        when(studentsService.retrieveById(0)).thenReturn(students.getFirst());

        ResponseEntity<Student> response = controller.getRecordById(0);
        assertThat(students.getFirst()).isEqualTo(response.getBody());
    }
}