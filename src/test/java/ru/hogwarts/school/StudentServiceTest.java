package ru.hogwarts.school;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.hogwarts.school.dto.FacultyCreateDto;
import ru.hogwarts.school.dto.FacultyReadDto;
import ru.hogwarts.school.dto.StudentCreateDto;
import ru.hogwarts.school.dto.StudentReadDto;
import ru.hogwarts.school.mapper.FacultyCreateMapper;
import ru.hogwarts.school.mapper.FacultyReadMapper;
import ru.hogwarts.school.mapper.StudentCreateMapper;
import ru.hogwarts.school.mapper.StudentReadMapper;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    private FacultyRepository facultyRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private AvatarRepository avatarRepository;
    @Mock
    private StudentCreateMapper studentCreateMapper;
    @Mock
    private StudentReadMapper studentReadMapper;

    @InjectMocks
    private FacultyService facultyService;
    @InjectMocks
    private StudentService studentService;
    @Spy
    private FacultyReadMapper facultyReadMapper;
    @Spy
    private FacultyCreateMapper facultyCreateMapper;


    private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>("postgres")
            .withUsername("postgres")
            .withPassword("shadow")
            .withDatabaseName("postgres");

    @BeforeAll
    static void runContainer() {
        POSTGRE_SQL_CONTAINER.start();
    }


    @Test
    public void createStudentTest() {
        Mockito.when(studentCreateMapper.mapTo(any(StudentCreateDto.class)))
                .thenReturn(new Student("Name", 1));
        Mockito.when(studentReadMapper.mapTo(any(Student.class)))
                .thenReturn(new StudentReadDto(1L, "Name", 1, null));


        Mockito.when(studentRepository.save(new Student("Name", 1)))
                .thenReturn(new Student("Name", 1));

        StudentReadDto expected = new StudentReadDto(1L, "Name", 1, null);

        StudentReadDto actual = studentService.createStudent(new StudentCreateDto("Name", 1, null));

        assertEquals(expected, actual);
    }

    @Test
    public void findStudentTest() {

        Student student = new Student(1L, "Name", 1);

        Mockito.when(studentReadMapper.mapTo(any(Student.class)))
                .thenReturn(new StudentReadDto(1L, "Name", 1, null));
        Mockito.when(studentRepository.findById(any(Long.class)))
                .thenReturn(Optional.ofNullable(student));

        Optional<StudentReadDto> expected = Optional.of(new StudentReadDto(1L, "Name", 1, null));

        Optional<StudentReadDto> actual = studentService.findStudent(1L);

        assertEquals(expected, actual);
    }

    @Test
    public void editStudentTest() {
        Mockito.when(studentReadMapper.mapTo(any(Student.class)))
                .thenReturn(new StudentReadDto(1L, "Name", 1, new FacultyReadDto("Name")));

        Faculty faculty = new Faculty(1L, "Name", "Color");
        StudentReadDto expected = new StudentReadDto(1L, "Name", 1, new FacultyReadDto("Name"));
        Student student = new Student(1L, "Name", 1, faculty);

        Mockito.when(facultyRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(new Faculty("Name", "Color")));
        Mockito.when(studentRepository.findById(any(Long.class)))
                .thenReturn(Optional.ofNullable(student));

        StudentReadDto actual = studentService.updateStudent(1L, new StudentCreateDto("Name", 1, 1L));

        assertEquals(expected, actual);
    }

    @Test
    public void deleteStudentTest() {
        Mockito.when(studentCreateMapper.mapTo(any(StudentCreateDto.class)))
                .thenReturn(new Student("Name", 1));
        Mockito.when(studentReadMapper.mapTo(any(Student.class)))
                .thenReturn(new StudentReadDto(1L, "Name", 1, new FacultyReadDto("Name")));

        StudentReadDto expected = new StudentReadDto(1L, "Name", 1, new FacultyReadDto("Name"));
        Faculty faculty = new Faculty(1L, "Name", "Color");

        Student student = new Student(1L, "Name", 1, faculty);

        Mockito.when(studentRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(new Student("Name", 1)));
        Mockito.when(studentRepository.save(any(Student.class)))
                .thenReturn(student);

        studentService.createStudent(new StudentCreateDto("Name", 1, null));
        StudentReadDto actual = studentService.deleteStudent(1L);

        Mockito.verify(studentRepository, times(1)).deleteById(1L);
        assertEquals(expected, actual);


    }

    @Test
    public void findAllStudentsByAgeTest() {
        Mockito.when(studentCreateMapper.mapTo(any(StudentCreateDto.class)))
                .thenReturn(new Student("Name", 1));
        Mockito.when(studentReadMapper.mapTo(any(Student.class)))
                .thenReturn(new StudentReadDto(1L, "Name", 1, new FacultyReadDto("Name")));

        Mockito.when(studentRepository.save(any(Student.class))).thenReturn(new Student(4L, "Name3", 4));

        studentService.createStudent(new StudentCreateDto("Name", 1, null));
        studentService.createStudent(new StudentCreateDto("Name1", 2, null));
        studentService.createStudent(new StudentCreateDto("Name2", 2, null));
        studentService.createStudent(new StudentCreateDto("Name3", 4, null));

        Student student1 = new Student(2L, "Name1", 2, null);
        Student student2 = new Student(3L, "Name2", 2, null);
        List<Student> list = new ArrayList<>();
        list.add(student1);
        list.add(student2);

        List<StudentReadDto> expected = new ArrayList<>();
        expected.add(new StudentReadDto(1L, "Name", 1, new FacultyReadDto("Name")));
        expected.add(new StudentReadDto(1L, "Name", 1, new FacultyReadDto("Name")));
        Mockito.when(studentRepository.findByAge(2)).thenReturn(list);

        List<StudentReadDto> actual = studentService.findStudentByAge(2);

        assertEquals(expected, actual);
    }

    @Test
    void findAllStudentsByAgeBetween() {
        Mockito.when(studentCreateMapper.mapTo(any(StudentCreateDto.class)))
                .thenReturn(new Student("Name", 1));
        Mockito.when(studentReadMapper.mapTo(any(Student.class)))
                .thenReturn(new StudentReadDto(1L, "Name", 1, new FacultyReadDto("Name")));

        Mockito.when(studentRepository.save(any(Student.class))).thenReturn(new Student(4L, "Name3", 4));

        studentService.createStudent(new StudentCreateDto("Name", 1, null));
        studentService.createStudent(new StudentCreateDto("Name1", 2, null));
        studentService.createStudent(new StudentCreateDto("Name2", 2, null));
        studentService.createStudent(new StudentCreateDto("Name3", 4, null));

        Student student1 = new Student(2L, "Name1", 2, null);
        Student student2 = new Student(3L, "Name2", 2, null);
        List<Student> list = new ArrayList<>();
        list.add(student1);
        list.add(student2);

        List<Student> list1 = new ArrayList<>();
        list1.add(student1);
        list1.add(student2);
        List<StudentReadDto> expected = new ArrayList<>();
        expected.add(new StudentReadDto(1L, "Name", 1, new FacultyReadDto("Name")));
        expected.add(new StudentReadDto(1L, "Name", 1, new FacultyReadDto("Name")));

        Mockito.when(studentRepository.findAllByAgeBetween(2, 4)).thenReturn(list1);

        List<StudentReadDto> actual = studentService.findAllByAgeBetween(2, 4);

        assertEquals(expected, actual);
    }

    @Test
    void findStudentByFaculty() {
        Mockito.when(studentCreateMapper.mapTo(any(StudentCreateDto.class)))
                .thenReturn(new Student("Name", 1));
        Mockito.when(studentReadMapper.mapTo(any(Student.class)))
                .thenReturn(new StudentReadDto(1L, "Name", 1, new FacultyReadDto("Name")));
        Mockito.when(studentRepository.save(any(Student.class)))
                .thenReturn(new Student(1L, "Name", 1));
        Mockito.when(facultyRepository.save(any(Faculty.class)))
                .thenReturn(new Faculty(1L, "Name", "Color"));

        studentService.createStudent(new StudentCreateDto("Name", 1, null));
        studentService.createStudent(new StudentCreateDto("Name1", 2, null));
        studentService.createStudent(new StudentCreateDto("Name2", 2, null));
        facultyService.createFaculty(new FacultyCreateDto("Name", "Color"));
        Long lastID = facultyService.findLastID();

        List<StudentReadDto> studentReadDtoList = new ArrayList<>();
        studentReadDtoList.add(new StudentReadDto(1L, "Name", 1, new FacultyReadDto("Name")));
        studentReadDtoList.add(new StudentReadDto(1L, "Name", 1, new FacultyReadDto("Name")));
        studentReadDtoList.add(new StudentReadDto(1L, "Name", 1, new FacultyReadDto("Name")));

        List<Student> students = new ArrayList<>();
        students.add(new Student(1L, "Name", 1, null));
        students.add(new Student(2L, "Name1", 2, null));
        students.add(new Student(3L, "Name2", 2, null));


        Mockito.when(studentRepository.findAllByFaculty_id(any(Long.class))).thenReturn(students);

        List<StudentReadDto> actual = studentService.findAllStudensByFaculty(lastID);

        assertEquals(studentReadDtoList, actual);
    }


}
