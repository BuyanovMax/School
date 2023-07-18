package ru.hogwarts.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.exception.BadRequestException;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    public void beforeEach() {
        studentService = new StudentService(studentRepository);
    }

    @Test
    public void createStudentTest() {
        Mockito.when(studentRepository.save(new Student(1L, "Name", 1)))
                .thenReturn(new Student(1L, "Name", 1));

        Student expected = new Student(1L, "Name", 1);

        Student actual = studentService.createStudent(new Student(1L, "Name", 1));

        assertEquals(expected, actual);
    }

    @Test
    public void findFacultyTest() {
        Mockito.when(studentRepository.save(new Student(1L, "Name", 1)))
                .thenReturn(new Student(1L, "Name", 1));


        Student student = new Student(1L, "Name", 1);
        Mockito.when(studentRepository.findById(1L)).thenReturn(Optional.ofNullable(student));

        studentService.createStudent(new Student(1L, "Name", 1));

        Optional<Student> expected = Optional.ofNullable(new Student(1L, "Name", 1));

        Optional<Student> actual = studentService.findStudent(1L);

        assertEquals(expected, actual);
    }

    @Test
    public void editFacultyTest() {
        Mockito.when(studentRepository.save(new Student(1L, "Name", 1)))
                .thenReturn(new Student(1L, "Name", 1));

        studentService.createStudent(new Student(1L, "Name", 1));

        Student expected = new Student(1L, "Name", 1);

        Student actual = studentService.editStudent(new Student(1L, "Name", 1));

        assertEquals(expected, actual);
    }

    @Test
    public void deleteFacultyTest() {
        Mockito.when(studentRepository.save(new Student(1L, "Name", 1)))
                .thenReturn(new Student(1L, "Name", 1));

        studentService.createStudent(new Student(1L, "Name", 1));

        Student expected = new Student(1L, "Name", 1);

        studentService.deleteStudent(1L);

        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    public void findAllByColorTest() {

        Mockito.when(studentRepository.save(new Student(1L, "Name", 1))).thenReturn(new Student(1L, "Name", 1));
        Mockito.when(studentRepository.save(new Student(2L, "Name1", 2))).thenReturn(new Student(2L, "Name1", 2));
        Mockito.when(studentRepository.save(new Student(3L, "Name2", 2))).thenReturn(new Student(3L, "Name2", 2));
        Mockito.when(studentRepository.save(new Student(4L, "Name3", 4))).thenReturn(new Student(4L, "Name3", 4));

        Student student = studentService.createStudent(new Student(1L, "Name", 1));
        Student student1 = studentService.createStudent(new Student(2L, "Name1", 2));
        Student student2 = studentService.createStudent(new Student(3L, "Name2", 2));
        Student student3 = studentService.createStudent(new Student(4L, "Name3", 4));


        List<Student> expected = new ArrayList<>();
        expected.add(student1);
        expected.add(student2);

        Mockito.when(studentRepository.findByAge(2)).thenReturn(expected);

        List<Student> actual = studentService.findStudentByAge(2);

        assertEquals(expected, actual);
    }
}
