package ru.hogwarts.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentServiceTest {
    private StudentService studentService;

    @BeforeEach
    public void beforeEach() {
        studentService = new StudentService();
    }

    @Test
    public void createStudentTest() {
        Student expected = new Student(1L, "Name", 1);

        Student actual = studentService.createStudent(new Student(1L, "Name", 1));

        assertEquals(expected, actual);
    }

    @Test
    public void findFacultyTest() {
        studentService.createStudent(new Student(1L, "Name", 1));

        ResponseEntity<Student> expected = ResponseEntity.ok(new Student(1L, "Name", 1));

        ResponseEntity<Student> actual = studentService.findStudent(1);

        assertEquals(expected, actual);
    }

    @Test
    public void findFacultyNegativeTest() {

//        assertThrows(ResponseEntity.class,
//                () -> facultyService.findFaculty(10));


        ResponseEntity<Student> responseEntity = ResponseEntity.notFound().build();
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isNull();

    }

    @Test
    public void editFacultyTest() {
        studentService.createStudent(new Student(1L, "Name", 1));

        ResponseEntity<Student> expected = ResponseEntity.ok(new Student(1L, "Name", 1));
        ResponseEntity<Student> actual = studentService.editStudent(new Student(1L, "Name1", 1));

        assertEquals(expected, actual);

    }

    @Test
    public void deleteFacultyTest() {
        studentService.createStudent(new Student(1L, "Name", 1));
        ResponseEntity<Student> expected = ResponseEntity.ok(new Student(1L, "Name", 1));
        ResponseEntity<Student> actual = studentService.deleteStudent(1);

        assertEquals(expected, actual);
    }

    @Test
    public void findAllByColorTest() {

        Student student = studentService.createStudent(new Student(1L, "Name", 1));
        Student student1 = studentService.createStudent(new Student(2L, "Name1", 2));
        Student student2 = studentService.createStudent(new Student(3L, "Name2", 2));
        Student student3 = studentService.createStudent(new Student(4L, "Name3", 4));


        List<Student> expected = new ArrayList<>();
        expected.add(student1);
        expected.add(student2);

        List<Student> actual = studentService.findStudentByAge(2);

        assertEquals(expected, actual);
    }
}
