//package ru.hogwarts.school;
//
//
//import com.google.gson.Gson;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//import ru.hogwarts.school.controller.AvatarController;
//import ru.hogwarts.school.controller.FacultyController;
//import ru.hogwarts.school.controller.StudentController;
//import ru.hogwarts.school.model.Faculty;
//import ru.hogwarts.school.model.Student;
//import ru.hogwarts.school.repositories.AvatarRepository;
//import ru.hogwarts.school.repositories.FacultyRepository;
//import ru.hogwarts.school.repositories.StudentRepository;
//import ru.hogwarts.school.service.AvatarService;
//import ru.hogwarts.school.service.FacultyService;
//import ru.hogwarts.school.service.StudentService;
//
//import javax.persistence.EntityManager;
//import java.util.*;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class SchoolApplicationFacultyTests {
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private FacultyController facultyController;
//    @Autowired
//    private AvatarController avatarController;
//    @Autowired
//    private FacultyService facultyService;
//    @Autowired
//    private AvatarService avatarService;
//    @Autowired
//    private TestRestTemplate testRestTemplate;
//    @Autowired
//    private StudentRepository studentRepository;
//    @Autowired
//    private FacultyRepository facultyRepository;
//@Autowired
//    private EntityManager entityManager;
//    @BeforeEach
//    void init() {
//
//        studentRepository.deleteAll();
//        facultyRepository.deleteAll();
//
//    }
//
//    @Test
//    void contextLoadsFaculty() throws Exception {
//        assertThat(facultyController).isNotNull();
//    }
//
//    @Test
//    void contextLoadsAvatar() throws Exception {
//        assertThat(avatarController).isNotNull();
//    }
//
//    @Test
//    void findFaculty() throws Exception {
//        Assertions
//                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/faculty/1", String.class))
//                .isNotNull();
//    }
//
//
//    @Test
//    void createFaculty() throws Exception {
//        Faculty faculty = new Faculty("Хогвардс","Разноцветный");
//
//        Assertions
//                .assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, String.class))
//                .isNotNull();
//        Long lastID = facultyRepository.findLastID();
//
//    }
//
//
//    @Test
//    void editFacultyTest() throws Exception {
//
//        Faculty faculty = new Faculty("Хогвардс", "Разноцветный");
//        Faculty facultyCreate = facultyService.createFaculty(faculty);
//        Long lastId = facultyService.findLastID();
//
//        Faculty faculty2 = new Faculty(lastId, "Школа", "Зеленая");
//
//        Optional<Faculty> expected = facultyService.findFaculty(lastId);
//
//        testRestTemplate.put("http://localhost:" + port + "/faculty/", faculty2);
//
//        Optional<Faculty> actual = facultyService.findFaculty(lastId);
//        Assertions.assertThat(!expected.equals(actual));
//
//
//    }
//
//
//    @Test
//    public void deleteFacultyTest() throws Exception {
//
//        Faculty faculty = new Faculty("Хогвардс", "Разноцветный");
//
//        testRestTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, String.class);
//        Long lastId = facultyService.findLastID();
//        testRestTemplate.getForObject("http://localhost:" + port + "/faculty" + lastId, String.class);
//        Optional<Faculty> lastFaculty = facultyService.findFaculty(lastId);
//        Long id = lastFaculty.get().getId();
//
//        testRestTemplate.delete("http://localhost:" + port + "/faculty/" + lastId);
//        Optional<Faculty> lastFaculty2 = facultyService.findFaculty(id);
//        Assertions.assertThat(lastFaculty2.isEmpty());
//    }
//
//
//    @Test
//    void getAllFacultyByColorTest() throws Exception {
//
//        Faculty faculty = new Faculty("string", "string");
//        Faculty faculty2 = new Faculty("string", "string");
//        Faculty faculty3 = facultyService.createFaculty(faculty);
//        Faculty faculty1 = facultyService.createFaculty(faculty2);
//        List<Faculty> list = new ArrayList<>();
//        list.add(faculty);
//        list.add(faculty2);
//        String json = new Gson().toJson(list);
//
//        String forObject = testRestTemplate.getForObject("http://localhost:" + port
//                + "/faculty/getAllFacultyByColor/?color=string", String.class);
//        System.out.println(forObject);
//        assertThat(forObject.equals(json)).isTrue();
//
//
//    }
//
//    @Test
//    void findFacultyByNameOrColorTest() throws Exception {
//
//        Faculty faculty = new Faculty("123", "string");
//        Faculty faculty2 = new Faculty("234", "string");
//        Faculty faculty1 = facultyService.createFaculty(faculty);
//        Faculty faculty3 = facultyService.createFaculty(faculty2);
//
//        List<Faculty> list = new ArrayList<>();
//        list.add(faculty1);
//        list.add(faculty3);
//        String json = new Gson().toJson(list);
//
//
//        String forObject = testRestTemplate.getForObject("http://localhost:" + port + "/faculty?name=string&color=string", String.class);
//        assertEquals(forObject, json);
//
//    }
//
//    @Test
//    void findFacultyByStudentTest() {
//        Faculty faculty = new Faculty( "string", "color");
//        Faculty save = facultyRepository.save(faculty);
//        System.out.println(faculty);
//        Student student = new Student("Олег", 8, faculty);
//        Student save1 = studentRepository.save(student);
//        System.out.println(student);
//        Faculty faculty1 = new Faculty(12L, "string", "color");
//        String json4 = new Gson().toJson(faculty1);
//
//        String forObject = testRestTemplate.getForObject("http://localhost:" + port + "/faculty/facultyByStudent/?id=28", String.class);
//
//        assertEquals(forObject, json4);
//
//
//    }
//
//
//}
