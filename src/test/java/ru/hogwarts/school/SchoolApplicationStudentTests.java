package ru.hogwarts.school;

import com.google.gson.Gson;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controller.AvatarController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.dto.FacultyCreateDto;
import ru.hogwarts.school.dto.FacultyReadDto;
import ru.hogwarts.school.dto.StudentCreateDto;
import ru.hogwarts.school.dto.StudentReadDto;
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
import static org.junit.Assert.assertEquals;

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
        studentRepository.save(new Student("Вася", 9));
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
    }

    @Test
    void updateStudentTest() throws Exception {
        Student student2 = new Student("Пётр", 16);
        Student student3 = new Student("Вася", 18);
        studentRepository.save(student2);
        studentRepository.save(student3);
        Long lastId = studentRepository.findLastID();
        Optional<StudentReadDto> expected = studentService.findStudent(lastId);
        student2.setId(lastId);
        testRestTemplate.put("http://localhost:" + port + "/student/", student2);
        lastId = studentRepository.findLastID();
        Optional<StudentReadDto> actual = studentService.findStudent(lastId);
        Assertions.assertThat(expected.equals(actual)).isTrue();
        testRestTemplate.put("http://localhost:" + port + "/student/", expected);


    }


    @Test
    public void deleteStudentTest() throws Exception {
        Student student = new Student(1L, "Петруха", 45);

        testRestTemplate.postForObject("http://localhost:" + port + "/student", student, String.class);
        Long lastId = studentService.findLastID();
        testRestTemplate.getForObject("http://localhost:" + port + "/student" + lastId, String.class);
        Optional<StudentReadDto> lastStudent = studentService.findStudent(lastId);
        Long id = lastStudent.get().getId();

        testRestTemplate.delete("http://localhost:" + port + "/student/" + lastId);
        Optional<StudentReadDto> lastFaculty2 = studentService.findStudent(id);
        Assertions.assertThat(lastFaculty2.isEmpty());
    }


    @Test
    void findStudentByAgeTest() throws Exception {
        Faculty faculty = new Faculty("String", "sdvsdfv");
        facultyRepository.save(faculty);

        StudentCreateDto student = new StudentCreateDto("Вася", 3,faculty.getId());

        StudentReadDto student2 = studentService.createStudent(student);

        List<StudentReadDto> list = new ArrayList<>();
        list.add(student2);

        String json = new Gson().toJson(list);

        String forObject = testRestTemplate.getForObject("http://localhost:" + port + "/student/findAllByAge/?age=3", String.class);

        assertEquals(forObject, json);

    }

    @Test
    void findAllByAgeBetweenTest() throws Exception {
        FacultyCreateDto facultyCreateDto = new FacultyCreateDto("Гриффиндор", "Желтый");
        FacultyReadDto faculty = facultyService.createFaculty(facultyCreateDto);
        Long lastID = facultyRepository.findLastID();
        List<StudentReadDto> list = new ArrayList<>();

        StudentCreateDto student = new StudentCreateDto("Васечка", 16,lastID);
        StudentCreateDto student2 = new StudentCreateDto("Петруха", 45,lastID);
        StudentReadDto student1 = studentService.createStudent(student);
        StudentReadDto student3 = studentService.createStudent(student2);

        list.add(student1);
        list.add(student3);
        String json = new Gson().toJson(list);

        String forObject = testRestTemplate.getForObject("http://localhost:" + port + "/student?min=15&max=45", String.class);
        assertEquals(forObject, json);


    }

    @Test
    void findAllStudentsByFacultyTest() {
        FacultyCreateDto faculty = new FacultyCreateDto( "string", "color");
        FacultyReadDto faculty1 = facultyService.createFaculty(faculty);
        Long lastID = facultyRepository.findLastID();

        StudentCreateDto student = new StudentCreateDto("Олег", 8,lastID);
        StudentCreateDto student2 = new StudentCreateDto("Петя", 12,lastID);
        List<StudentCreateDto> list2 = new ArrayList<>();
        list2.add(student);
        list2.add(student2);

        StudentReadDto save = studentService.createStudent(student);
        StudentReadDto save1 = studentService.createStudent(student2);

        StudentReadDto student1 = new StudentReadDto(save.getId(), "Олег", 8,faculty1);
        StudentReadDto student3 = new StudentReadDto(save1.getId(), "Петя", 12,faculty1);
        List<StudentReadDto> list3 = new ArrayList<>();
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
        FacultyCreateDto faculty = new FacultyCreateDto("adv", "sdv");
        FacultyReadDto faculty1 = facultyService.createFaculty(faculty);
        Long lastID= facultyRepository.findLastID();
        StudentCreateDto student = new StudentCreateDto("Гарри", 22,lastID);
        StudentCreateDto student1 = new StudentCreateDto("Рон", 22,lastID);
        StudentCreateDto student2 = new StudentCreateDto("Гермиона", 21,lastID);
        StudentCreateDto student3 = new StudentCreateDto("Петя", 18,lastID);
        StudentCreateDto student4 = new StudentCreateDto("Вася", 17,lastID);
        StudentCreateDto student5 = new StudentCreateDto("Олеся", 12,lastID);
        StudentCreateDto student6 = new StudentCreateDto("Денис", 15,lastID);
        StudentReadDto student7 = studentService.createStudent(student);
        StudentReadDto student8 = studentService.createStudent(student1);
        StudentReadDto student9 = studentService.createStudent(student2);
        StudentReadDto student10 = studentService.createStudent(student3);
        StudentReadDto student11 = studentService.createStudent(student4);
        StudentReadDto student12 = studentService.createStudent(student5);
        StudentReadDto student13 = studentService.createStudent(student6);

        List<StudentReadDto> students = new ArrayList<>();
        students.add(student13);
        students.add(student12);
        students.add(student11);
        students.add(student10);
        students.add(student9);
        String json = new Gson().toJson(students);

        String forObject = testRestTemplate.getForObject("http://localhost:" + port + "/student/findFiveLastStudent", String.class);
        assertEquals(json,forObject);
    }
}
