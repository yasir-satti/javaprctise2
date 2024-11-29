package dev.yasir.javapractise2.service;

import dev.yasir.javapractise2.entity.Student;
import dev.yasir.javapractise2.utils.ReplaceCamelCase;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;

@ActiveProfiles(value = "application.properties")
@DataJpaTest
@DisplayNameGeneration(ReplaceCamelCase.class)
public class StudentsServiceTests {

    List<Student> students = Arrays.asList(
            new Student(1L, "name1", "lastName1", 1),
            new Student(2L, "name2", "lastName2", 2));

    @Autowired
    private StudentsService studentsService;

    @Test
    void shouldInsertNewRecordGivenValidSRecord() throws Exception {

        /*
        Story:

        Given a valid student record and table exists in db
        When I try to insert the new record into table
        Then the new record is inserted into the table, and it returns the new record id.
        */

        Student newRecord = new Student(3L, "jk", "df", 6);
        Long newRecordId = studentsService.insertRecord(newRecord);
        assertThat(newRecordId).isEqualTo(3L);
    }

    @Test
    void shouldShowMessageRecordAlreadyExistsTryingToInsertNewRecordGivenExistingRecord() {

        /*
        Story:

        Given an exiting student record in table
        When I try to insert the record into table
        Then a message is shown saying "Record with id x already exists in database."
        */

        Student newRecord = students.getFirst();

        Throwable thrown = catchThrowable(() -> studentsService.insertRecord(newRecord));

        assertThat(thrown).hasMessage("Record with Id " +
                Long.toHexString(newRecord.getId()) +
                " already exists in database.");
    }

    @Test
    void shouldUpdateExistingRecordByIdGivenValidRecordByIdExists() throws Exception {

        /*
        Story:

        Given a valid student record by id and table exists in db
        When I try to update the record in the table
        Then the updated record is inserted into the table, and it returns the updated record from table.
        */

        Student expectedUpdatedRecord = students.getFirst();
        expectedUpdatedRecord.setName("updated name");
        expectedUpdatedRecord.setLastname("updated last name");
        Long id = expectedUpdatedRecord.getId();

        Student updatedRecord = studentsService.update(expectedUpdatedRecord, id);
        assertThat(updatedRecord).isEqualTo(expectedUpdatedRecord);
    }

    @Test
    void shouldShowMessageRecordNotFoundToUpdateGivenNoMatchingRecordByIdExists() {

        /*
        Story:

        Given no valid student record by id exists in table
        When I try to update the record in the table
        Then a message is shown saying "No Student record with id x was found to update."
        */

        Student expectedUpdatedRecord =
                new Student(3L, "jk", "df", 6);
        Long id = expectedUpdatedRecord.getId();

        Throwable thrown = catchThrowable(() -> studentsService.update(expectedUpdatedRecord, id));

        assertThat(thrown).hasMessage("No Student record with Id " +
                Long.toHexString(expectedUpdatedRecord.getId()) +
                " was found in database to update.");
    }

    @Test
    void shouldReturnListOfStudentRecordsGivenRecordsExistInDb() throws Exception {

        /*
        Story:

        Given there are a number of saved student records in db
        When I try to retrieve these records
        Then a list of these records is returned
        */

        List<Student> returnedList = studentsService.retrieveAll();

        assertThat(returnedList).isEqualTo(students);
    }

    @Test
    void shouldShowMessageNoRecordsFoundWhenRetrievingAllRecordsGivenNoRecordsExistInDb() throws Exception {

        /*
        Story:

        Given there are no records in db
        When I try to retrieve records
        Then a message is shown saying "No Student records were found."
        */

        studentsService.deleteAll();

        Throwable thrown = catchThrowable(() -> studentsService.retrieveAll());

        assertThat(thrown).hasMessage("No Student records were found.");
    }

    @Test
    void shouldReturnAStudentRecordsGivenValidRecordId() throws Exception {

        /*
        Story:

        Given there are a number of saved student records in db
        When I try to retrieve a record using its id
        Then the requested record is returned
        */

        Optional<Student> returnedRecord = studentsService.retrieveById(1);

        assertThat(returnedRecord).isEqualTo(Optional.of(students.getFirst()));
    }

    @Test
    void shouldShowMessageNoRecordByIdFoundGivenInvalidRecordId() {

        /*
        Story:

        Given there are a number of saved student records in db
        When I try to retrieve a record using an id that does not exist
        Then a message is shown saying "No Student record was found with id x"
        */

        Throwable thrown = catchThrowable(() -> studentsService.retrieveById(0));

        assertThat(thrown).hasMessage("No Student record was found with Id 0.");    }

    @Test
    void shouldDeleteAllRecordsGivenThereAreRecordsInDb() throws Exception {

        /*
        Story:

        Given there are a number of saved student records in db
        When I try to delete these records
        Then the records are deleted.
        */

        List<Student> studentsList = studentsService.retrieveAll();

        assertThat(studentsList).isNotEmpty();

        assertAll(
                () -> studentsService.deleteAll()
        );

        Throwable thrown = catchThrowable(() -> studentsService.deleteAll());

        assertThat(thrown).hasMessage("No Student records were found.");
    }

    @Test
    void shouldShowMessageNoRecordsFoundWhenDeletingAllGivenNoRecordsExistInDb() throws Exception {

        /*
        Story:

        Given there are no student records in db
        When I try to delete records
        Then a message is shown saying "No Student records were found."
        */

        studentsService.deleteAll();

        Throwable thrown = catchThrowable(() -> studentsService.deleteAll());

        assertThat(thrown).hasMessage("No Student records were found.");
    }
}
