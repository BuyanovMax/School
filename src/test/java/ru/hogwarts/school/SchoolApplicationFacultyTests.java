package ru.hogwarts.school;

import com.google.gson.Gson;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.hogwarts.school.controller.AvatarController;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.dto.FacultyCreateDto;
import ru.hogwarts.school.dto.FacultyReadDto;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SchoolApplicationFacultyTests {
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;
    @Autowired
    private AvatarController avatarController;
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private AvatarService avatarService;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private EntityManager entityManager;


    private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>("postgres")
            .withUsername("postgres")
            .withPassword("shadow")
            .withDatabaseName("postgres");

    @BeforeAll
    static void runContainer() {
        POSTGRE_SQL_CONTAINER.start();
    }
    @BeforeEach
    void init() {
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
    }
    @Test
    void contextLoadsFaculty() throws Exception {
        assertThat(facultyController).isNotNull();
    }

    @Test
    void contextLoadsAvatar() throws Exception {
        assertThat(avatarController).isNotNull();
    }

    @Test
    void findFaculty() throws Exception {
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/faculty/1", String.class))
                .isNotNull();
    }


    @Test
    void createFaculty() throws Exception {
        Faculty faculty = new Faculty("Хогвардс","Разноцветный");

        Assertions
                .assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, String.class))
                .isNotNull();
        Long lastID = facultyRepository.findLastID();
    }


    @Test
    void editFacultyTest() throws Exception {
        FacultyCreateDto faculty = new FacultyCreateDto("Хогвардс", "Разноцветный");
        FacultyReadDto facultyCreate = facultyService.createFaculty(faculty);
        Long lastId = facultyService.findLastID();

        Faculty faculty2 = new Faculty(lastId, "Школа", "Зеленая");

        Optional<FacultyReadDto> expected = facultyService.findFaculty(lastId);

        testRestTemplate.put("http://localhost:" + port + "/faculty/", faculty2);

        Optional<FacultyReadDto> actual = facultyService.findFaculty(lastId);
        Assertions.assertThat(!expected.equals(actual));
    }


    @Test
    public void deleteFacultyTest() throws Exception {
        Faculty faculty = new Faculty("Хогвардс", "Разноцветный");

        testRestTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, String.class);
        Long lastId = facultyService.findLastID();
        testRestTemplate.getForObject("http://localhost:" + port + "/faculty" + lastId, String.class);
        Long lastId2 = facultyService.findLastID();
        testRestTemplate.delete("http://localhost:" + port + "/faculty/" + lastId);
        Optional<FacultyReadDto> lastFaculty2 = facultyService.findFaculty(lastId2);
        Assertions.assertThat(lastFaculty2.isEmpty());
    }


    @Test
    void getAllFacultyByColorTest() throws Exception {
        FacultyCreateDto faculty = new FacultyCreateDto("string", "string");
        FacultyCreateDto faculty2 = new FacultyCreateDto("string", "string");
        FacultyReadDto faculty3 = facultyService.createFaculty(faculty);
        FacultyReadDto faculty1 = facultyService.createFaculty(faculty2);
        List<FacultyReadDto> list = new ArrayList<>();
        list.add(faculty3);
        list.add(faculty1);
        String json = new Gson().toJson(list);

        String forObject = testRestTemplate.getForObject("http://localhost:" + port
                + "/faculty/getAllFacultyByColor/?color=string", String.class);
        System.out.println(forObject);
        assertThat(forObject.equals(json)).isTrue();
    }

    @Test
    void findFacultyByNameOrColorTest() throws Exception {
        FacultyCreateDto faculty = new FacultyCreateDto("string", "123");
        FacultyCreateDto faculty2 = new FacultyCreateDto("string", "234");
        FacultyReadDto faculty1 = facultyService.createFaculty(faculty);
        FacultyReadDto faculty3 = facultyService.createFaculty(faculty2);

        List<FacultyReadDto> list = new ArrayList<>();
        list.add(faculty1);
        list.add(faculty3);

        String json = new Gson().toJson(list);


        String forObject = testRestTemplate.getForObject("http://localhost:" + port + "/faculty?name=string&color=string", String.class);
        assertEquals(forObject, json);
    }

    @Test
    void findFacultyByStudentTest() {
        Faculty faculty = facultyRepository.save(new Faculty( "string", "color"));
        studentRepository.save(new Student("Олег", 8, faculty));

        FacultyReadDto facultyReadDto = new FacultyReadDto("string");
        String json4 = new Gson().toJson(facultyReadDto);

        String forObject = testRestTemplate.getForObject("http://localhost:" + port + "/faculty/facultyByStudent/?id=28", String.class);

        assertEquals(forObject, json4);
    }


}
