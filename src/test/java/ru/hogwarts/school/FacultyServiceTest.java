package ru.hogwarts.school;

import org.junit.jupiter.api.AfterEach;
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
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceTest {

    @Mock
    private FacultyRepository facultyRepository;
    @InjectMocks
    private FacultyService facultyService;

    @BeforeEach
    public void beforeEach() {
        facultyService = new FacultyService(facultyRepository);
    }

    @Test
    public void createFacultyTest() {
        Mockito.when(facultyRepository.save(new Faculty(1L, "Name", "Color"))).thenReturn(new Faculty(1L, "Name", "Color"));

        Faculty expected = new Faculty(1L, "Name", "Color");

        Faculty actual = facultyService.createFaculty(new Faculty(1L, "Name", "Color"));

        assertEquals(expected, actual);
    }

//    @Test
//    public void createFacultyNegativeTest() {
//        assertThrows(BadRequestException.class, () -> facultyService.findFaculty(null));
//    }

    @Test
    public void findFacultyTest() {
        Mockito.when(facultyRepository.save(new Faculty(1L, "Name", "Color"))).thenReturn(new Faculty(1L, "Name", "Color"));
        Faculty faculty = facultyService.createFaculty(new Faculty(1L, "Name", "Color"));

        Mockito.when(facultyRepository.findById(1L)).thenReturn(Optional.ofNullable(faculty));

        Optional<Faculty> expected = Optional.ofNullable(new Faculty(1L, "Name", "Color"));

        Optional<Faculty> actual = facultyService.findFaculty(1L);

        assertEquals(expected, actual);
    }

    @Test
    public void editFacultyTest() {
        Mockito.when(facultyRepository.save(new Faculty(1L, "Name", "Color"))).thenReturn(new Faculty(1L, "Name", "Color"));

        Faculty expected = new Faculty(1L, "Name", "Color");

        Faculty actual = facultyService.editFaculty(new Faculty(1L, "Name", "Color"));

        assertEquals(expected, actual);

    }

    @Test
    public void deleteFacultyTest() {
        Mockito.when(facultyRepository.save(new Faculty(1L, "Name", "Color"))).thenReturn(new Faculty(1L, "Name", "Color"));
        facultyService.createFaculty(new Faculty(1L, "Name", "Color"));
        Faculty expected = new Faculty(1L, "Name", "Color");
        facultyService.deleteFaculty(1L);

        verify(facultyRepository, times(1)).deleteById(1L);

    }


    @Test
    public void findAllByColorTest() {
        Mockito.when(facultyRepository.save(new Faculty(1L, "Name", "Color"))).thenReturn(new Faculty(1L, "Name", "Color"));
        Mockito.when(facultyRepository.save(new Faculty(2L, "Name1", "Color1"))).thenReturn(new Faculty(2L, "Name1", "Color1"));
        Mockito.when(facultyRepository.save(new Faculty(3L, "Name2", "Color"))).thenReturn(new Faculty(3L, "Name2", "Color"));
        Mockito.when(facultyRepository.save(new Faculty(4L, "Name3", "Color1"))).thenReturn(new Faculty(4L, "Name3", "Color1"));

        Faculty faculty = facultyService.createFaculty(new Faculty(1L, "Name", "Color"));
        Faculty faculty1 = facultyService.createFaculty(new Faculty(2L, "Name1", "Color1"));
        Faculty faculty2 = facultyService.createFaculty(new Faculty(3L, "Name2", "Color"));
        Faculty faculty3 = facultyService.createFaculty(new Faculty(4L, "Name3", "Color1"));
        List<Faculty> expected = new ArrayList<>();
        expected.add(faculty1);
        expected.add(faculty3);

        Mockito.when(facultyRepository.findByColor("Color1")).thenReturn(expected);

        List<Faculty> actual = facultyService.findAllByColor("Color1");

        assertEquals(expected, actual);
    }


}
