package dev.yasir.javapractise2.controller;

import dev.yasir.javapractise2.entity.Student;
import dev.yasir.javapractise2.utils.ReplaceCamelCase;
import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DisplayNameGeneration(ReplaceCamelCase.class)
public class ControllerIntgTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnAllRecords() throws Exception {

        /*
        Story:

        Given there are a number of saved student records
        When I try to retrieve these records
        Then a list of these records is returned
        */

        List<Student> students = Arrays.asList(
                new Student("name1", "lastName1", 1),
                new Student("name2", "lastName2", 2));

        mockMvc.perform(get("/api/records")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.size()",
                                CoreMatchers.is(students.size())))
                .andExpect(jsonPath("$[0].name", CoreMatchers.is("name1")));
    }

    @Test
    void shouldShowMessageNoRecordsFoundGivenNoRecordsInDb() throws Exception {

        /*
        Story:

        Given there no student records in db
        When I try to retrieve records
        Then a message is shown saying "No Student records were found."
        */

        mockMvc.perform(delete("/api/records")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Assertions.assertThatThrownBy(
                () -> mockMvc.perform(get("/api/records")
                        .contentType(MediaType.APPLICATION_JSON)))
                .hasCauseInstanceOf(Exception.class)
                .hasMessageContaining("No Student records were found.");
    }

    @Test
    void shouldReturnRecordGivenValidRecordId() throws Exception {

        /*
        Story:

        Given a valid student record in database
        When I try to retrieve this record using its id
        Then the record is returned
        */

        mockMvc.perform(get("/api/record/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", CoreMatchers.is("name2")));
    }

    @Test
    void shoudlShowMessageNoRecordFoundGivenInvalidId() {

        /*
        Story:

        Given an invalid student record id
        When I try to retrieve a record using the invalid
        Then a message is shown saying "No Student record was found with id x."
        */

        Assertions.assertThatThrownBy(
                        () -> mockMvc.perform(get("/api/record/3")
                                .contentType(MediaType.APPLICATION_JSON)))
                .hasCauseInstanceOf(Exception.class)
                .hasMessageContaining("No Student record was found with Id 3.");
    }

    @Test
    void shouldDeleteAllRecordsGivenValidRecordsInDb() throws Exception {

        /*
        Story:

        Given there are a number of student records in db
        When I try to delete these records
        Then the records are deleted.
        */

        mockMvc.perform(get("/api/records")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.size()",
                                CoreMatchers.is(2)));

        mockMvc.perform(delete("/api/records")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Assertions.assertThatThrownBy(
                        () -> mockMvc.perform(get("/api/records")
                                .contentType(MediaType.APPLICATION_JSON)))
                .hasCauseInstanceOf(Exception.class)
                .hasMessageContaining("No Student records were found.");
    }
}