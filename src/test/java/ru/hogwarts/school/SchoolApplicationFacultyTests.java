package ru.hogwarts.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class SchoolApplicationFacultyTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FacultyRepository facultyRepository;
    @MockBean
    private StudentController studentController;
    @MockBean
    private StudentService studentService;
    @MockBean
    private AvatarService avatarService;
    @SpyBean
    private FacultyService facultyService;
    @InjectMocks
    private FacultyController facultyController;


    @Test
    void findFacultyTest() throws Exception {

        final Long ID = 1L;
        final String NAME = "Хогвардс";
        final String COLOR = "Разноцветный";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", NAME);
        facultyObject.put("color", COLOR);

        Faculty faculty = new Faculty(ID,NAME,COLOR);

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.color").value(COLOR));
    }

    @Test
    void createFacultyTest() throws Exception {
        final Long ID = 1L;
        final String NAME = "Хогвардс";
        final String COLOR = "Разноцветный";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", NAME);
        facultyObject.put("color", COLOR);

        Faculty faculty = new Faculty(ID,NAME,COLOR);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.color").value(COLOR));
    }
    @Test
    void editFacultyTest() throws Exception {

        final Long ID = 1L;
        final String NAME = "Хогвардс";
        final String COLOR = "Разноцветный";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", NAME);
        facultyObject.put("color", COLOR);

        Faculty faculty = new Faculty(ID,NAME,COLOR);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.color").value(COLOR));
    }

    @Test
    void deleteFacultyFacultyTest() throws Exception {
        final Long ID = 1L;
        final String NAME = "Хогвардс";
        final String COLOR = "Разноцветный";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", NAME);
        facultyObject.put("color", COLOR);

        Faculty faculty = new Faculty(ID,NAME,COLOR);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/1")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllFacultyByColor() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", "Хогвардс1");
        facultyObject.put("color", "Разноцветный1");

        Faculty faculty = new Faculty(2L, "Хогвардс1", "Разноцветный1");
        Faculty faculty2 = new Faculty(4L, "Хогвардс3", "Разноцветный1");

        List<Faculty> list2 = new ArrayList<>();
        list2.add(faculty);
        list2.add(faculty2);


        when(facultyRepository.findAllByColor("Разноцветный1")).thenReturn(list2);


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/getAllFacultyByColor/?color=Разноцветный1")
                        .content(list2.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(faculty, faculty2))));
    }
    @Test
    void findFacultyByNameOrColorTest()throws Exception  {
        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", "Хогвардс1");
        facultyObject.put("color", "Разноцветный1");

        Faculty faculty = new Faculty(2L, "Хогвардс1", "string");
        Faculty faculty2 = new Faculty(4L, "Хогвардс3", "string");

        List<Faculty> list2 = new ArrayList<>();
        list2.add(faculty);
        list2.add(faculty2);

        when(facultyRepository.findAllByNameOrColorIgnoreCase(any(String.class),
                any(String.class))).thenReturn(list2);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty?name=string&color=string")
                        .content(list2.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(faculty, faculty2))));
    }
    @Test
    void findFacultyByStudentTest() throws Exception{
        final Long ID = 1L;
        final String NAME = "Хогвардс";
        final String COLOR = "Разноцветный";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", NAME);
        facultyObject.put("color", COLOR);

        Faculty faculty = new Faculty(ID,NAME,COLOR);

        when(facultyRepository.findByStudent_id(any(Long.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/facultyByStudent?id=3")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.color").value(COLOR));

    }

    }
