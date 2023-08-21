package ru.hogwarts.school;

import com.google.gson.Gson;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
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
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private FacultyRepository facultyRepository;


    @BeforeEach
    void init() {
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
    }

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
        Student student = new Student("Вася", 9);
        studentRepository.save(student);
        Assertions
                .assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/1", String.class))
                .isNotNull();

    }

    @Test
    void createStudent() throws Exception {
        Student student = new Student(1L, "Вася", 9);
        String actual = testRestTemplate.postForObject("http://localhost:" + port + "/student", student, String.class);
        Assertions
                .assertThat(actual)
                .isNotNull();
        Optional<Student> student1 = studentService.findStudent(1L);

    }

    @Test
    void editStudentTest() throws Exception {
        Student student2 = new Student("Пётр", 16);
        Student student3 = new Student("Вася", 18);
        studentRepository.save(student2);
        studentRepository.save(student3);
        Long lastId = studentRepository.findLastID();
        Optional<Student> expected = studentService.findStudent(lastId);
        student2.setId(lastId);
        testRestTemplate.put("http://localhost:" + port + "/student/", student2);
        lastId = studentRepository.findLastID();
        Optional<Student> actual = studentService.findStudent(lastId);
        Assertions.assertThat(!expected.equals(actual)).isTrue();
        testRestTemplate.put("http://localhost:" + port + "/student/", expected);




    }


    @Test
    public void deleteStudentTest() throws Exception {

        Student student = new Student(1L, "Петруха", 45);

        testRestTemplate.postForObject("http://localhost:" + port + "/student", student, String.class);
        Long lastId = studentService.findLastID();
        testRestTemplate.getForObject("http://localhost:" + port + "/student" + lastId, String.class);
        Optional<Student> lastStudent = studentService.findStudent(lastId);
        Long id = lastStudent.get().getId();

        testRestTemplate.delete("http://localhost:" + port + "/student/" + lastId);
        Optional<Student> lastFaculty2 = studentService.findStudent(id);
        Assertions.assertThat(lastFaculty2.isEmpty());
    }


    @Test
    void findStudentByAgeTest() throws Exception {
        Faculty faculty = new Faculty("String", "sdvsdfv");
        facultyRepository.save(faculty);
        Student student = new Student("Вася", 3,faculty);
        Student save = studentRepository.save(student);
        List<Student> list = new ArrayList<>();
        list.add(save);

        String json = new Gson().toJson(list);

        String forObject = testRestTemplate.getForObject("http://localhost:" + port + "/student/findAllByAge/?age=3", String.class);

        assertEquals(forObject, json);

    }

    @Test
    void findAllByAgeBetweenTest() throws Exception {
        Faculty faculty = new Faculty("Гриффиндор", "Желтый");
        facultyRepository.save(faculty);
        List<Student> list = new ArrayList<>();
        Student student = new Student("Васечка", 16,faculty);
        Student student2 = new Student("Петруха", 45,faculty);
        list.add(student);
        list.add(student2);
        studentRepository.save(student);
        studentRepository.save(student2);

        String json = new Gson().toJson(list);

        String forObject = testRestTemplate.getForObject("http://localhost:" + port + "/student?min=15&max=45", String.class);
        assertEquals(forObject, json);


    }

    @Test
    void findAllStudentsByFacultyTest() {
        Faculty faculty = new Faculty(1L, "string", "color");
        facultyService.createFaculty(faculty);
        Long lastID = facultyRepository.findLastID();
        faculty.setId(lastID);

        Student student = new Student("Олег", 8,faculty);
        Student student2 = new Student("Петя", 12,faculty);
        List<Student> list2 = new ArrayList<>();
        list2.add(student);
        list2.add(student2);

        Student save = studentRepository.save(student);
        Student save1 = studentRepository.save(student2);


        Student student1 = new Student(save.getId(), "Олег", 8,faculty);
        Student student3 = new Student(save1.getId(), "Петя", 12,faculty);
        List<Student> list3 = new ArrayList<>();
        list3.add(student1);
        list3.add(student3);
        String json4 = new Gson().toJson(list3);

        String forObject = testRestTemplate.getForObject("http://localhost:" + port + "/student/StudentByFaculty/?id=2", String.class);
        assertEquals(forObject, json4);

    }

    @Test
    void findAllStudentCountTest() {
        int count = 5;
        String json = new Gson().toJson(count);
        Student student = new Student("Гарри", 22);
        Student student1 = new Student("Рон", 22);
        Student student2 = new Student("Гермиона", 21);
        Student student3 = new Student("Петя", 18);
        Student student4 = new Student("Вася", 17);
        studentRepository.save(student);
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        studentRepository.save(student4);

        String forObject = testRestTemplate.getForObject("http://localhost:" + port + "/student/AllStudentCount", String.class);
        assertEquals(json,forObject);
    }

    @Test
    void findAllStudentAverageTest() {
        double count = 20.0;
        String json = new Gson().toJson(count);
        Student student = new Student("Гарри", 22);
        Student student1 = new Student("Рон", 22);
        Student student2 = new Student("Гермиона", 21);
        Student student3 = new Student("Петя", 18);
        Student student4 = new Student("Вася", 17);
        studentRepository.save(student);
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        studentRepository.save(student4);

        String forObject = testRestTemplate.getForObject("http://localhost:" + port + "/student/AllStudentAverage", String.class);
        assertEquals(json,forObject);
    }
    @Test
    void findFiveLastStudentTest() {
        Faculty faculty = new Faculty("adv", "sdv");
        facultyRepository.save(faculty);
        Student student = new Student("Гарри", 22,faculty);
        Student student1 = new Student("Рон", 22,faculty);
        Student student2 = new Student("Гермиона", 21,faculty);
        Student student3 = new Student("Петя", 18,faculty);
        Student student4 = new Student("Вася", 17,faculty);
        Student student5 = new Student("Олеся", 12,faculty);
        Student student6 = new Student("Денис", 15,faculty);
        studentRepository.save(student);
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        studentRepository.save(student4);
        studentRepository.save(student5);
        studentRepository.save(student6);

        List<Student> students = new ArrayList<>();
        students.add(student6);
        students.add(student5);
        students.add(student4);
        students.add(student3);
        students.add(student2);
        String json = new Gson().toJson(students);

        String forObject = testRestTemplate.getForObject("http://localhost:" + port + "/student/findFiveLastStudent", String.class);
        assertEquals(json,forObject);
    }
}
