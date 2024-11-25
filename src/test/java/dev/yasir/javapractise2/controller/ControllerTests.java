package dev.yasir.javapractise2.controller;

import dev.yasir.javapractise2.entity.Student;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnAllRecords() throws Exception {

        /*
        //TODO
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
}