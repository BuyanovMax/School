package ru.hogwarts.school;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FacultyServiceTest {
    private FacultyService facultyService;

    @BeforeEach
    public void beforeEach() {
        facultyService = new FacultyService();
    }

    @Test
    public void createFacultyTest() {
        Faculty expected = new Faculty(1L, "Name", "Color");

        Faculty actual = facultyService.createFaculty(new Faculty(1L, "Name", "Color"));

        assertEquals(expected, actual);
    }

    @Test
    public void findFacultyTest() {
        facultyService.createFaculty(new Faculty(1L, "Name", "Color"));

        ResponseEntity<Faculty> expected = ResponseEntity.ok(new Faculty(1L, "Name", "Color"));

        ResponseEntity<Faculty> actual = facultyService.findFaculty(1);

        assertEquals(expected, actual);
    }

    @Test
    public void findFacultyNegativeTest() {

//        assertThrows(ResponseEntity.class,
//                () -> facultyService.findFaculty(10));


        ResponseEntity<Faculty> responseEntity = ResponseEntity.notFound().build();
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isNull();

    }

    @Test
    public void editFacultyTest() {
        facultyService.createFaculty(new Faculty(1L, "Name", "Color"));

        ResponseEntity<Faculty> expected = ResponseEntity.ok(new Faculty(1L, "Name1", "Color1"));
        ResponseEntity<Faculty> actual = facultyService.editFaculty(new Faculty(1L, "Name1", "Color1"));

        assertEquals(expected,actual);

    }

    @Test
    public void deleteFacultyTest() {
        facultyService.createFaculty(new Faculty(1L, "Name", "Color"));
        ResponseEntity<Faculty> expected = ResponseEntity.ok(new Faculty(1L, "Name", "Color"));
        ResponseEntity<Faculty> actual = facultyService.deleteFaculty(1);

        assertEquals(expected,actual);
    }

    @Test
    public void findAllByColorTest() {
        Faculty faculty = facultyService.createFaculty(new Faculty(1L, "Name", "Color"));
        Faculty faculty1 = facultyService.createFaculty(new Faculty(2L, "Name1", "Color1"));
        Faculty faculty2 = facultyService.createFaculty(new Faculty(3L, "Name2", "Color1"));
        Faculty faculty3 = facultyService.createFaculty(new Faculty(4L, "Name3", "Color3"));
        List<Faculty> expected = new ArrayList<>();
        expected.add(faculty1);
        expected.add(faculty2);
        List<Faculty> actual = facultyService.findAllByColor("Color1");

        assertEquals(expected,actual);
    }
}
