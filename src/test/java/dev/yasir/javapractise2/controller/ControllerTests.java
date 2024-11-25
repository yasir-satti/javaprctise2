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

//+++++++++++++++++++++++++++++

/*public class PokemonController {

    private PokemonService pokemonService;

    @Autowired
    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("pokemon")
    public ResponseEntity<PokemonResponse> getPokemons(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return new ResponseEntity<>(pokemonService.getAllPokemon(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("pokemon/{id}")
    public ResponseEntity<PokemonDto> pokemonDetail(@PathVariable int id) {
        return ResponseEntity.ok(pokemonService.getPokemonById(id));

    }

    @PostMapping("pokemon/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PokemonDto> createPokemon(@RequestBody PokemonDto pokemonDto) {
        return new ResponseEntity<>(pokemonService.createPokemon(pokemonDto), HttpStatus.CREATED);
    }

    @PutMapping("pokemon/{id}/update")
    public ResponseEntity<PokemonDto> updatePokemon(@RequestBody PokemonDto pokemonDto, @PathVariable("id") int pokemonId) {
        PokemonDto response = pokemonService.updatePokemon(pokemonDto, pokemonId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("pokemon/{id}/delete")
    public ResponseEntity<String> deletePokemon(@PathVariable("id") int pokemonId) {
        pokemonService.deletePokemonId(pokemonId);
        return new ResponseEntity<>("Pokemon delete", HttpStatus.OK);
    }*/

//+++++++++++++++++++++++++++

/*@WebMvcTest(PokemonController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PokemonControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PokemonService pokemonService;

    @Autowired
    private ObjectMapper objectMapper;

    Pokemon pokemon = Pokemon.builder()
            .name("Pekashu")
            .type("Electric")
            .build();
    PokemonDto pokemonDto = PokemonDto.builder()
            .name("Pekashu")
            .type("Electric")
            .build();
    ReviewDto reviewDto = ReviewDto.builder()
            .content("content")
            .stars(3)
            .title("title")
            .build();
    Review review = Review.builder()
            .title("title")
            .content("content")
            .stars(3)
            .build();

    @Test
    void CreatePokemon_ReturnCreatedPokemon() throws Exception {
        given(pokemonService.createPokemon(ArgumentMatchers.any()))
                .willAnswer((invocation -> invocation.getArgument(0)));

        mockMvc.perform(post("/api/pokemon/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pokemonDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(pokemonDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(pokemonDto.getType())));
    }

    @Test
    void GetAllPokemon_ReturnResponseDto() throws Exception {

        PokemonResponse pokemonResponseDto = PokemonResponse.builder()
                .pageNo(1)
                .pageSize(10)
                .content(Arrays.asList(pokemonDto))
                .last(true)
                .build();
        when(pokemonService.getAllPokemon(1, 10))
                .thenReturn(pokemonResponseDto);

        mockMvc.perform(get("/api/pokemon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("pageNo", "1")
                        .param("pageSize", "10"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.content.size()",
                                CoreMatchers.is(pokemonResponseDto.getContent().size())))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.content[0].type",
                                CoreMatchers.is(pokemonDto.getType())))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.content[0].name",
                                CoreMatchers.is(pokemonDto.getName())));
    }

    @Test
    void PokemonDetail_ReturnPokemonDto() throws Exception {

        when(pokemonService.getPokemonById(Mockito.anyInt()))
                .thenReturn(pokemonDto);

        mockMvc.perform(get("/api/pokemon/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pokemonDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",
                        CoreMatchers.is(pokemonDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type",
                        CoreMatchers.is(pokemonDto.getType())));
    }

    @Test
    void PokemonUpdate_ReturnPokemonDto() throws Exception {

        when(pokemonService.updatePokemon(pokemonDto, 1)).thenReturn(pokemonDto);

        mockMvc.perform(put("/api/pokemon/1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pokemonDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",
                        CoreMatchers.is(pokemonDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type",
                        CoreMatchers.is(pokemonDto.getType())));
    }

    @Test
    void PokemonDelete_ReturnString() throws Exception {

        doNothing().when(pokemonService).deletePokemonId(1);

        mockMvc.perform(delete("/api/pokemon/1/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }*/
