package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("faculty")
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
//        try {
//            if (id != null) {
//                return ResponseEntity.ok(facultyService.deleteFaculty(id));
//            }
//        } catch (NotFoundException e) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        facultyService.deleteFaculty(id);
    }


    @GetMapping("/getAllFacultyByCalor/{id}")
    public ResponseEntity<List<Faculty>> getAllFacultyByColor(String color) {
        if (color != null) {
            return ResponseEntity.ok(facultyService.findAllByColor(color));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
