package ru.hogwarts.school;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
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
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class SchoolApplicationStudentTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FacultyRepository facultyRepository;
    @MockBean
    private FacultyService facultyService;
    @MockBean
    private AvatarService avatarService;
    @MockBean
    private AvatarRepository avatarRepository;
    @MockBean
    private StudentRepository studentRepository;
    @SpyBean
    private StudentService studentService;
    @InjectMocks
    private StudentController studentController;


    @Test
    void findStudentTest() throws Exception {
        final Long ID = 1L;
        final String NAME = "Вася";
        final Integer AGE = 2;

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", NAME);
        facultyObject.put("age", AGE);

        Student student = new Student(ID, NAME, AGE);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.age").value(AGE));
    }

    @Test
    void createStudentTest() throws Exception {
        final Long ID = 1L;
        final String NAME = "Вася";
        final Integer AGE = 2;

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", NAME);
        facultyObject.put("age", AGE);

        Student student = new Student(ID, NAME, AGE);

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.age").value(AGE));
    }

    @Test
    void editFacultyTest() throws Exception {

        final Long ID = 1L;
        final String NAME = "Вася";
        final Integer AGE = 2;

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", NAME);
        facultyObject.put("age", AGE);

        Student student = new Student(ID, NAME, AGE);

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.age").value(AGE));
    }

    @Test
    void deleteFacultyFacultyTest() throws Exception {
        final Long ID = 1L;
        final String NAME = "Вася";
        final Integer AGE = 2;

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", NAME);
        facultyObject.put("age", AGE);

        Student student = new Student(ID, NAME, AGE);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/1")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void findByFacultyId() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", "");
        facultyObject.put("age", "");

        Student student = new Student(1L, "Вася", 8);
        Student student2 = new Student(2L, "Вася", 9);
        List<Student> list = new ArrayList<>();
        list.add(student);
        list.add(student2);


        when(studentRepository.findAllByFaculty_id(1L)).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/StudentByFaculty/?id=1")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(student, student2))));
    }
    @Test
    void findStudentByAgeTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", "");
        facultyObject.put("age", "");

        Student student = new Student(1L, "Вася", 8);
        Student student2 = new Student(2L, "Вася", 9);
        List<Student> list = new ArrayList<>();
        list.add(student);
        list.add(student2);


        when(studentRepository.findByAge(any(Integer.class))).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/findAllByAge/?age=3")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(student, student2))));
    }

    @Test
    void findAllByAgeBetweenTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", "");
        facultyObject.put("age", "");

        Student student = new Student(1L, "Вася", 8);
        Student student2 = new Student(2L, "Вася", 9);
        List<Student> list = new ArrayList<>();
        list.add(student);
        list.add(student2);


        when(studentRepository.findAllByAgeBetween(any(Integer.class),any(Integer.class))).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student?min=2&max=4")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(student, student2))));
    }


}