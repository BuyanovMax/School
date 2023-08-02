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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SchoolApplicationStudentTests {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;
    @Autowired
    private AvatarController avatarController;
    @Autowired
    private StudentService studentService;
    @Autowired
    private AvatarService avatarService;
    @Autowired
    private TestRestTemplate testRestTemplate;



    @Test
    void contextLoadsStudent() throws Exception {
        assertThat(studentController).isNotNull();
    }

    @Test
    void contextLoadsAvatar() throws Exception {
        assertThat(avatarController).isNotNull();
    }


    @Test
    void findStudent() throws Exception {
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/1", String.class))
                .isNotNull();
    }

    @Test
    void createStudent() throws Exception {
        Student student = new Student();
        student.setName("Петруха");
        student.setAge(45);

        Assertions
                .assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/student", student, String.class))
                .isNotNull();
        testRestTemplate.delete("http://localhost:" + port + "/student/1");

    }

    @Test
    void editStudentTest() throws Exception {

        Student student = new Student();
        student.setName("Петруха");
        student.setAge(45);
        java.lang.Long lastId = studentService.findLastID();
        Student student2 = new Student();
        student2.setId(lastId);
        student2.setName("Васечка");
        student2.setAge(16);

        Optional<Student> expected = studentService.findStudent(lastId);

        testRestTemplate.put("http://localhost:" + port + "/student/", student2);

        Optional<Student> actual = studentService.findStudent(lastId);
        Assertions.assertThat(!expected.equals(actual));
        testRestTemplate.put("http://localhost:" + port + "/student/", student);

    }


    @Test
    public void deleteStudentTest() throws Exception {

        Student student = new Student();
        student.setName("Петруха");
        student.setAge(45);

        testRestTemplate.postForObject("http://localhost:" + port + "/student", student, String.class);
        java.lang.Long lastId = studentService.findLastID();
        testRestTemplate.getForObject("http://localhost:" + port + "/student" + lastId, String.class);
        Optional<Student> lastStudent = studentService.findStudent(lastId);
        java.lang.Long id = lastStudent.get().getId();

        testRestTemplate.delete("http://localhost:" + port + "/student/" + lastId);
        Optional<Student> lastFaculty2 = studentService.findStudent(id);
        Assertions.assertThat(lastFaculty2.isEmpty());
    }


    @Test
    void findStudentByAgeTest() throws Exception {
        Student student = new Student();
        student.setId(3L);
        student.setName("Вася");
        student.setAge(3);
        List<Student> list = new ArrayList<>();
        list.add(student);

        String json = new Gson().toJson(list);

        String forObject = testRestTemplate.getForObject("http://localhost:" + port + "/student/findAllByAge/?age=3", String.class);

        assertEquals(forObject,json);
    }

    @Test
    void findAllByAgeBetweenTest() throws Exception{

        List<Student> list = new ArrayList<>();
        Student student = new Student();
        student.setId(5L);
        student.setName("Алена");
        student.setAge(2);
        Student student2 = new Student();
        student2.setId(3L);
        student2.setName("Вася");
        student2.setAge(3);
        list.add(student);
        list.add(student2);

        String json = new Gson().toJson(list);

        String forObject = testRestTemplate.getForObject("http://localhost:" + port + "/student?min=2&max=4", String.class);
        assertEquals(forObject,json);

    }

}
