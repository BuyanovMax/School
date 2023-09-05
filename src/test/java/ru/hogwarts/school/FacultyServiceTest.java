package ru.hogwarts.school;

import org.junit.jupiter.api.BeforeAll;
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
import ru.hogwarts.school.mapper.FacultyCreateMapper;
import ru.hogwarts.school.mapper.FacultyReadMapper;
import ru.hogwarts.school.model.Faculty;
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
public class FacultyServiceTest {

    @Mock
    private FacultyRepository facultyRepository;
    @Mock
    private StudentRepository studentRepository;
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
    public void createFacultyTest() {
        Mockito.when(facultyRepository.save(new Faculty("Когтевран", "Коричневый"))).thenReturn(new Faculty("Когтевран", "Коричневый"));
        FacultyReadDto expected = new FacultyReadDto("Когтевран");
        FacultyCreateDto facultyCreateDto = new FacultyCreateDto("Когтевран", "Коричневый");
        FacultyReadDto actual = facultyService.createFaculty(facultyCreateDto);
        assertEquals(expected, actual);
    }


    @Test
    public void findFacultyTest() {
        Mockito.when(facultyRepository.save(new Faculty("Name", "Color"))).thenReturn(new Faculty("Name", "Color"));
        FacultyReadDto faculty = facultyService.createFaculty(new FacultyCreateDto("Name", "Color"));

        Mockito.when(facultyRepository.findById(1L)).thenReturn(Optional.of(new Faculty("Name", "Color")));

        Optional<FacultyReadDto> expected = Optional.of(new FacultyReadDto("Name"));

        Optional<FacultyReadDto> actual = facultyService.findFaculty(1L);

        assertEquals(expected, actual);
    }

    @Test
    public void editFacultyTest() {
        Mockito.when(facultyRepository.save(new Faculty("Name", "Color"))).thenReturn(new Faculty("Name", "Color"));
        Mockito.when(facultyRepository.findById(1L)).thenReturn(Optional.of(new Faculty("Name", "Color")));
        FacultyReadDto expected = facultyService.createFaculty(new FacultyCreateDto("Name", "Color"));

        FacultyReadDto actual = facultyService.updateFaculty(1L, new FacultyCreateDto("Name", "Color"));

        assertEquals(expected, actual);

    }

    @Test
    public void deleteFacultyTest() {
        Mockito.when(facultyRepository.save(new Faculty("Name", "Color"))).thenReturn(new Faculty("Name", "Color"));
        FacultyReadDto faculty = facultyService.createFaculty(new FacultyCreateDto("Name", "Color"));
        Mockito.when(facultyRepository.findById(1L)).thenReturn(Optional.of(new Faculty("Name", "Color")));
        FacultyReadDto facultyReadDto = facultyService.deleteFaculty(1L);

        verify(facultyRepository, times(1)).deleteById(1L);
        assertEquals(faculty, facultyReadDto);
    }


    @Test
    public void findAllByColorTest() {
        Mockito.when(facultyRepository.save(new Faculty("Name", "Color"))).thenReturn(new Faculty("Name", "Color"));
        Mockito.when(facultyRepository.save(new Faculty("Name1", "Color1"))).thenReturn(new Faculty("Name1", "Color1"));
        Mockito.when(facultyRepository.save(new Faculty("Name2", "Color"))).thenReturn(new Faculty("Name2", "Color"));
        Mockito.when(facultyRepository.save(new Faculty("Name3", "Color1"))).thenReturn(new Faculty("Name3", "Color1"));

        FacultyReadDto faculty = facultyService.createFaculty(new FacultyCreateDto("Name", "Color"));
        FacultyReadDto faculty1 = facultyService.createFaculty(new FacultyCreateDto("Name1", "Color1"));
        FacultyReadDto faculty2 = facultyService.createFaculty(new FacultyCreateDto("Name2", "Color"));
        FacultyReadDto faculty3 = facultyService.createFaculty(new FacultyCreateDto("Name3", "Color1"));
        List<FacultyReadDto> expected = new ArrayList<>();
        expected.add(faculty1);
        expected.add(faculty3);
        List<Faculty> list = new ArrayList<>();
        list.add(new Faculty("Name1", "Color1"));
        list.add(new Faculty("Name3", "Color1"));

        Mockito.when(facultyRepository.findByColor("Color1")).thenReturn(list);

        List<FacultyReadDto> actual = facultyService.findAllByColor("Color1");

        assertEquals(expected, actual);
    }


    @Test
    void findFacultyByNameOrColor() {
        Mockito.when(facultyRepository.save(new Faculty("Name", "Color"))).thenReturn(new Faculty("Name", "Color"));
        Mockito.when(facultyRepository.save(new Faculty("Name1", "Color1"))).thenReturn(new Faculty("Name1", "Color1"));
        Mockito.when(facultyRepository.save(new Faculty("Name2", "Color"))).thenReturn(new Faculty("Name2", "Color"));
        Mockito.when(facultyRepository.save(new Faculty("Name3", "Color1"))).thenReturn(new Faculty("Name3", "Color1"));

        FacultyReadDto faculty = facultyService.createFaculty(new FacultyCreateDto("Name", "Color"));
        FacultyReadDto faculty1 = facultyService.createFaculty(new FacultyCreateDto("Name1", "Color1"));
        FacultyReadDto faculty2 = facultyService.createFaculty(new FacultyCreateDto("Name2", "Color"));
        FacultyReadDto faculty3 = facultyService.createFaculty(new FacultyCreateDto("Name3", "Color1"));
        List<Faculty> list = new ArrayList<>();
        Faculty fac = new Faculty("Name", "Color");
        Faculty fac2 = new Faculty("Name2", "Color");
        list.add(fac);
        list.add(fac2);
        List<FacultyReadDto> expected = new ArrayList<>();
        expected.add(faculty);
        expected.add(faculty2);

        Mockito.when(facultyRepository.findAllByNameOrColorIgnoreCase(null, "Color")).thenReturn(list);

        List<FacultyReadDto> actual = facultyService.findFacultyByNameOrColor(null, "Color");

        assertEquals(expected, actual);
    }
    @Test
    void findFacultyByStudent() {

        FacultyReadDto facultyReadDto = new FacultyReadDto("Name");

        Mockito.when(facultyRepository.findByStudent_id(any(Long.class))).thenReturn(new Faculty("Name", "Color"));

        FacultyReadDto actual = facultyService.findFacultyByStudent(1L);

        assertEquals(facultyReadDto,actual);

    }


}
