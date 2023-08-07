package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;


    public StudentController(StudentService studentService) {
        this.studentService = studentService;
        ;
    }


    @PostMapping
    public ResponseEntity<Student> createFaculty(@RequestBody Student student) {
        if (student != null) {
            return ResponseEntity.ok(studentService.createStudent(student));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Student>> findFaculty(@PathVariable Long id) {
        try {
            if (id != null) {
                return ResponseEntity.ok(studentService.findStudent(id));
            }
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping
    public ResponseEntity<Student> editFaculty(@RequestBody Student student) {
        try {
            if (student != null) {
                return ResponseEntity.ok(studentService.editStudent(student));
            }
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }


    @DeleteMapping("{id}")
    public void deleteFaculty(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @GetMapping("/findAllByAge/")
    public ResponseEntity<List<Student>> findStudentByAge(Integer age) {
        if (age != null) {
            return ResponseEntity.ok(studentService.findStudentByAge(age));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping
    public ResponseEntity<List<Student>> findAllByAgeBetween(Integer min, Integer max) {
        if (min != null || max != null) {
            return ResponseEntity.ok(studentService.findAllByAgeBetween(min, max));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/StudentByFaculty/")
    public ResponseEntity<List<Student>> findByFacultyId(Long id) {
        if (id != null) {
            return ResponseEntity.ok(studentService.findAllStudensByFaculty(id));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    @GetMapping("/AllStudentCount")
    public ResponseEntity<Integer> findAllStudentCount() {
        return ResponseEntity.ok(studentService.findAllStudentCount());
    }

    @GetMapping("AllStudentAverage")
    public ResponseEntity<Double> findAllStudentAverage() {
        return ResponseEntity.ok(studentService.findAllStudentAgeAverage());
    }

    @GetMapping("findFiveLastStudent")
    public ResponseEntity<List<Student>> findFiveLastStudent() {
        return ResponseEntity.ok(studentService.findFiveLastStudent());
    }



}
