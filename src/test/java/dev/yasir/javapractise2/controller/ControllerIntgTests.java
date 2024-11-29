package dev.yasir.javapractise2.controller;

import dev.yasir.javapractise2.entity.Student;
import dev.yasir.javapractise2.utils.ReplaceCamelCase;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(ReplaceCamelCase.class)
public class ControllerIntgTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnAllRecords() throws Exception {

        /*
        Story:

        Given there are a number of saved student records
        When i try to retrieve these records
        Then a list of these records is retruned
        */

        List<Student> students = Arrays.asList(
                new Student("Jack", "Smith", 34),
                new Student("Maya", "Alan", 54));

        mockMvc.perform(get("/api/records")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.size()",
                                CoreMatchers.is(students.size())))
                .andExpect(jsonPath("$[0].name", CoreMatchers.is("Jack")));
    }

    @Test
    void shouldReturnRecordGivenValidRecordId() {

        /*
        //TODO
        Story:

        Given a valid student record in database
        When i try to retrieve this record using its id
        Then the record is retruned
        */
    }
}