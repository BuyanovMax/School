package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }


    @GetMapping("{id}")
    public ResponseEntity<Optional<Faculty>> findFaculty(@PathVariable Long id) {
        try {
            if (id != null) {
                return ResponseEntity.ok(facultyService.findFaculty(id));
            }
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        if (faculty != null) {
            return ResponseEntity.ok(facultyService.createFaculty(faculty));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        try {
            if (faculty != null) {
                return ResponseEntity.ok(facultyService.editFaculty(faculty));
            }
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    @DeleteMapping("{id}")
    public void deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
    }


    @GetMapping("/getAllFacultyByColor/")
    public ResponseEntity<List<Faculty>> getAllFacultyByColor(String color) {
        if (color != null) {
            return ResponseEntity.ok(facultyService.findAllByColor(color));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping
    public ResponseEntity<List<Faculty>> findFacultyByNameOrColor(@RequestParam(required = false) String name, @RequestParam(required = false) String color) {
        return ResponseEntity.ok(facultyService.findFacultyByNameOrColor(name, color));
    }

    @GetMapping("/facultyByStudent")
    public ResponseEntity<Faculty> findFacultyByStudent(@RequestParam Long id) {
        if (id != null) {
            return ResponseEntity.ok(facultyService.findFacultyByStudent(id));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("findLongestFacultyName")
    public ResponseEntity<Optional<String>> findLongestFacultyName() {
        return ResponseEntity.ok(facultyService.findLongestFacultyName());
    }
}
