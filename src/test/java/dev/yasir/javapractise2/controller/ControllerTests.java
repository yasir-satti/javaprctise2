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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
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
    void shouldReturnAllRecords_GivenValidRecordsAreInDb() throws Exception {

        /*
        Story:

        Given there are a number of saved student records
        When I try to retrieve these records
        Then a list of these records is returned
        */

        when(studentsService.retrieveAll())
                .thenReturn(students);

        ResponseEntity<List<Student>> response = controller.getAllRecords();

        assertThat(response.getBody()).isEqualTo(students);
    }

    @Test
    void shouldShowMessageNoRecordsFound_GivenNoRecordsInDb() throws Exception {

        /*
        Story:

        Given there no student records in db
        When I try to retrieve records
        Then a message is shown saying "No Student records were found."
        */

        when(studentsService.retrieveAll())
                .thenReturn(Arrays.asList());

        Throwable thrown = catchThrowable(() -> controller.getAllRecords());

        assertThat(thrown.getMessage().contains("No Student records were found."));
    }

    @Test
    void shouldReturnRecord_GivenValidRecordId() throws Exception {

        /*
        Story:

        Given a valid student record is in database
        When i try to retrieve this record using its id
        Then the record is returned
        */

        when(studentsService.retrieveById(1))
                .thenReturn(students.getFirst());

        ResponseEntity<Student> response = controller.getRecordById(1);

        assertThat(response.getBody()).isEqualTo(students.getFirst());
    }

    @Test
    void shouldDeleteAllRecords_GivenValidRecordsInDb() throws Exception {

        /*
        Story:

        Given there are a number of student records in db
        When I try to delete these records
        Then the records are deleted.
        */

        doNothing().when(studentsService).deleteAll();

        assertAll(
                () -> controller.deleteAllRecords()
        );
    }
}