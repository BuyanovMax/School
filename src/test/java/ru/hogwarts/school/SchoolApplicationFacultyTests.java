package ru.hogwarts.school;


import com.google.gson.Gson;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controller.AvatarController;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.*;

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
        Faculty faculty = new Faculty();
        faculty.setName("Хогвардс");
        faculty.setColor("Разноцветный");

        Assertions
                .assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, String.class))
                .isNotNull();
        testRestTemplate.delete("http://localhost:" + port + "/faculty/1");
    }


    @Test
    void editFacultyTest() throws Exception {

        Faculty faculty = new Faculty();
        faculty.setName("Хогвардс");
        faculty.setColor("Разноцветный");
        java.lang.Long lastId = facultyService.findLastID();
        Faculty faculty2 = new Faculty();
        faculty2.setId(lastId);
        faculty2.setName("Школа");
        faculty2.setColor("Зеленая");

        Optional<Faculty> expected = facultyService.findFaculty(lastId);

        testRestTemplate.put("http://localhost:" + port + "/faculty/", faculty2);

        Optional<Faculty> actual = facultyService.findFaculty(lastId);
        Assertions.assertThat(!expected.equals(actual));
        testRestTemplate.put("http://localhost:" + port + "/faculty/", faculty);

    }


    @Test
    public void deleteFacultyTest() throws Exception {

        Faculty faculty = new Faculty();
        faculty.setName("Хогвардс");
        faculty.setColor("Разноцветный");

        testRestTemplate.postForObject("http://localhost:" + port + "/faculty", faculty, String.class);
        java.lang.Long lastId = facultyService.findLastID();
        testRestTemplate.getForObject("http://localhost:" + port + "/faculty" + lastId, String.class);
        Optional<Faculty> lastFaculty = facultyService.findFaculty(lastId);
        java.lang.Long id = lastFaculty.get().getId();

        testRestTemplate.delete("http://localhost:" + port + "/faculty/" + lastId);
        Optional<Faculty> lastFaculty2 = facultyService.findFaculty(id);
        Assertions.assertThat(lastFaculty2.isEmpty());
    }


    @Test
    void getAllFacultyByColorTest() throws Exception {

        Faculty faculty = new Faculty();
        faculty.setId(2L);
        faculty.setName("string");
        faculty.setColor("string");
        Faculty faculty2 = new Faculty();
        faculty2.setId(3L);
        faculty2.setName("string");
        faculty2.setColor("string");

        List<Faculty> list = new ArrayList<>();
        list.add(faculty);
        list.add(faculty2);
        String json = new Gson().toJson(list);
        String color = "string";

        String forObject = testRestTemplate.getForObject("http://localhost:" + port
                                                         + "/faculty/getAllFacultyByColor/?color=string", String.class);
        System.out.println(forObject);

        assertThat(forObject.equals(json));

    }

    @Test
    void findFacultyByNameOrColorTest() throws Exception {

        Faculty faculty = new Faculty();
        faculty.setId(2L);
        faculty.setName("string");
        faculty.setColor("string");
        Faculty faculty2 = new Faculty();
        faculty2.setId(3L);
        faculty2.setName("string");
        faculty2.setColor("string");
        Faculty faculty3 = new Faculty();
        faculty3.setId(4L);
        faculty3.setName("string");
        faculty3.setColor("string2");
        Faculty faculty4 = new Faculty();
        faculty4.setId(5L);
        faculty4.setName("string");
        faculty4.setColor("string2");

        List<Faculty> list = new ArrayList<>();
        list.add(faculty);
        list.add(faculty2);
        list.add(faculty3);
        list.add(faculty4);


        String json = new Gson().toJson(list);

        String forObject = testRestTemplate.getForObject("http://localhost:" + port + "/faculty?name=string&color=string", String.class);
        assertEquals(forObject, json);


    }

    @Test
    void findStudentByFacultyTest() {
        Student student = new Student();
        student.setId(7L);
        student.setName("Олег");
        student.setAge(8);

        List<Student> list = new ArrayList<>();
        list.add(student);
        String json = new Gson().toJson(list);

        String forObject = testRestTemplate.getForObject("http://localhost:" + port + "/student/StudentByFaculty/?id=9", String.class);
        assertEquals(forObject,json);
    }


}
