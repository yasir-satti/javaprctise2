package dev.yasir.javapractise2.controller;

import dev.yasir.javapractise2.entity.Student;
import dev.yasir.javapractise2.repository.StudentsRepository;
import dev.yasir.javapractise2.service.StudentsService;
import dev.yasir.javapractise2.utils.ReplaceCamelCase;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest
@DisplayNameGeneration(ReplaceCamelCase.class)
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
    void shouldReturnAllRecordsGivenValidRecordsAreInDb() throws Exception {

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
    void shouldShowMessageNoRecordsFoundGivenNoRecordsInDb() throws Exception {

        /*
        Story:

        Given there no student records in db
        When I try to retrieve records
        Then a message is shown saying "No Student records were found."
        */

        when(studentsService.retrieveAll())
                .thenReturn(Arrays.asList());

        Throwable thrown = catchThrowable(() -> controller.getAllRecords());

        assertThat(((ResponseStatusException) thrown).getBody().getDetail())
                .isEqualTo("No Student records were found.");
    }

    @Test
    void shouldReturnRecordGivenValidRecordId() throws Exception {

        /*
        Story:

        Given a valid student record is in database
        When i try to retrieve this record using its id
        Then the record is returned
        */

        when(studentsService.retrieveById(1))
                .thenReturn(Optional.ofNullable(students.getFirst()));

        ResponseEntity<Optional<Student>> response = controller.getRecordById(1);

        assertThat(response.getBody().get()).isEqualTo(students.getFirst());
    }

    @Test
    void shoudlShowMessageNoRecordFoundGivenInvalidId() throws Exception {

        /*
        Story:

        Given a invalid student record id
        When i try to retrieve a record using the invalid
        Then a message is shown saying "No Student record was found with id x."
        */

        Optional<Student> empty = Optional.empty();
        when(studentsService.retrieveById(1))
                .thenReturn(empty);

        Throwable thrown = catchThrowable(() -> controller.getRecordById(1));

        assertThat(
                ((ResponseStatusException) thrown).getBody().getDetail())
                .isEqualTo("No Student record with id 1 was found.");
    }

    @Test
    void shouldDeleteAllRecordsGivenValidRecordsInDb() throws Exception {

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