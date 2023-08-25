//package ru.hogwarts.school;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import ru.hogwarts.school.model.Avatar;
//import ru.hogwarts.school.model.Faculty;
//import ru.hogwarts.school.model.Student;
//import ru.hogwarts.school.repositories.AvatarRepository;
//import ru.hogwarts.school.repositories.FacultyRepository;
//import ru.hogwarts.school.repositories.StudentRepository;
//import ru.hogwarts.school.service.FacultyService;
//import ru.hogwarts.school.service.StudentService;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
//@ExtendWith(MockitoExtension.class)
//public class StudentServiceTest {
//    @Mock
//    private FacultyRepository facultyRepository;
//    @Mock
//    private StudentRepository studentRepository;
//    @Mock
//    private AvatarRepository avatarRepository;
//
//    @InjectMocks
//    private FacultyService facultyService;
//    @InjectMocks
//    private StudentService studentService;
//
//    @BeforeEach
//    public void beforeEach() {
//        studentRepository.deleteAll();
//    }
//
//    @Test
//    public void createStudentTest() {
//        Mockito.when(studentRepository.save(new Student(1L, "Name", 1)))
//                .thenReturn(new Student(1L, "Name", 1));
//
//        Student expected = new Student(1L, "Name", 1);
//
//        Student actual = studentService.createStudent(new Student(1L, "Name", 1));
//
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void findFacultyTest() {
//        Mockito.when(studentRepository.save(new Student(1L, "Name", 1)))
//                .thenReturn(new Student(1L, "Name", 1));
//
//
//        Student student = new Student(1L, "Name", 1);
//        Mockito.when(studentRepository.findById(1L)).thenReturn(Optional.ofNullable(student));
//
//        studentService.createStudent(new Student(1L, "Name", 1));
//
//        Optional<Student> expected = Optional.ofNullable(new Student(1L, "Name", 1));
//
//        Optional<Student> actual = studentService.findStudent(1L);
//
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void editFacultyTest() {
//        Mockito.when(studentRepository.save(new Student(1L, "Name", 1)))
//                .thenReturn(new Student(1L, "Name", 1));
//
//        studentService.createStudent(new Student(1L, "Name", 1));
//
//        Student expected = new Student(1L, "Name", 1);
//
//        Student actual = studentService.editStudent(new Student(1L, "Name", 1));
//
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void deleteFacultyTest() {
//        Mockito.when(studentRepository.save(new Student(1L, "Name", 1)))
//                .thenReturn(new Student(1L, "Name", 1));
//
//        studentService.createStudent(new Student(1L, "Name", 1));
//
//        studentService.deleteStudent(1L);
//
//        verify(studentRepository, times(1)).deleteById(1L);
//    }
//
//    @Test
//    public void findAllByColorTest() {
//
//        Mockito.when(studentRepository.save(new Student(1L, "Name", 1))).thenReturn(new Student(1L, "Name", 1));
//        Mockito.when(studentRepository.save(new Student(2L, "Name1", 2))).thenReturn(new Student(2L, "Name1", 2));
//        Mockito.when(studentRepository.save(new Student(3L, "Name2", 2))).thenReturn(new Student(3L, "Name2", 2));
//        Mockito.when(studentRepository.save(new Student(4L, "Name3", 4))).thenReturn(new Student(4L, "Name3", 4));
//
//        Student student = studentService.createStudent(new Student(1L, "Name", 1));
//        Student student1 = studentService.createStudent(new Student(2L, "Name1", 2));
//        Student student2 = studentService.createStudent(new Student(3L, "Name2", 2));
//        Student student3 = studentService.createStudent(new Student(4L, "Name3", 4));
//
//
//        List<Student> expected = new ArrayList<>();
//        expected.add(student1);
//        expected.add(student2);
//
//        Mockito.when(studentRepository.findByAge(2)).thenReturn(expected);
//
//        List<Student> actual = studentService.findStudentByAge(2);
//
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void findAllByAgeBetween() {
//        Mockito.when(studentRepository.save(new Student(1L, "Name", 1))).thenReturn(new Student(1L, "Name", 1));
//        Mockito.when(studentRepository.save(new Student(2L, "Name1", 2))).thenReturn(new Student(2L, "Name1", 2));
//        Mockito.when(studentRepository.save(new Student(3L, "Name2", 2))).thenReturn(new Student(3L, "Name2", 2));
//        Mockito.when(studentRepository.save(new Student(4L, "Name3", 4))).thenReturn(new Student(4L, "Name3", 4));
//
//        Student student = studentService.createStudent(new Student(1L, "Name", 1));
//        Student student1 = studentService.createStudent(new Student(2L, "Name1", 2));
//        Student student2 = studentService.createStudent(new Student(3L, "Name2", 2));
//        Student student3 = studentService.createStudent(new Student(4L, "Name3", 4));
//
//
//        List<Student> expected = new ArrayList<>();
//        expected.add(student1);
//        expected.add(student2);
//        expected.add(student3);
//
//        Mockito.when(studentRepository.findAllByAgeBetween(2,4)).thenReturn(expected);
//
//        List<Student> actual = studentService.findAllByAgeBetween(2,4);
//
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    void findStudentByFaculty() {
//        Mockito.when(studentRepository.save(new Student(1L, "Name", 1))).thenReturn(new Student(1L, "Name", 1));
//        Mockito.when(studentRepository.save(new Student(2L, "Name1", 2))).thenReturn(new Student(2L, "Name1", 2));
//        Mockito.when(studentRepository.save(new Student(3L, "Name2", 2))).thenReturn(new Student(3L, "Name2", 2));
//
//
//        Student student = studentService.createStudent(new Student(1L, "Name", 1));
//        Student student1 = studentService.createStudent(new Student(2L, "Name1", 2));
//        Student student2 = studentService.createStudent(new Student(3L, "Name2", 2));
//
//
//        List<Student> expected = new ArrayList<>();
//        expected.add(student);
//        expected.add(student1);
//        expected.add(student2);
//
//        Mockito.when(facultyRepository.save(new Faculty(1L, "Name", "Color"))).thenReturn(new Faculty(1L, "Name", "Color"));
//
//        Faculty faculty = facultyService.createFaculty(new Faculty(1L, "Name", "Color"));
//
//
//        Mockito.when(studentRepository.findAllByFaculty_id(faculty.getId())).thenReturn(expected);
//
//        List<Student> actual = studentService.findAllStudensByFaculty(faculty.getId());
//
//        assertEquals(expected,actual);
//    }
//
//
//
//    @Test
//    void findAvatarTest() {
//        Faculty faculty = new Faculty("sdvsvc", "sdvsdv");
//        facultyRepository.save(faculty);
//        Mockito.when(avatarRepository.findByStudentId(1L))
//                .thenReturn(Optional.of(new Avatar()));
//        Student student = new Student(1L, "Name", 1,faculty);
//        studentRepository.save(student);
//
//        Optional<Avatar> expected = Optional.of(new Avatar());
//
//        Optional<Avatar> actual = avatarRepository.findByStudentId(1L);
//
//        assertEquals(expected,actual);
//
//    }
//}
