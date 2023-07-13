package ru.hogwarts.school.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;
import java.util.List;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> findFaculty(@PathVariable int id) {
       return facultyService.findFaculty(id);
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
       return facultyService.editFaculty(faculty);

    }


    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable int id) {
      return facultyService.deleteFaculty(id);
    }


    @GetMapping("/getAllFacultyByCalor/{id}")
    public List<Faculty> getAllFacultyByColor(String color) {
        return facultyService.findAllByColor(color);
    }

}
