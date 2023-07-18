package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping("/create")
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
//        try {
//            if (id != 0) {
//                return ResponseEntity.ok(studentService.deleteStudent(id));
//            }
//        } catch (NotFoundException e) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        studentService.deleteStudent(id);
    }

    @GetMapping("/findAllByAge/{age}")
    public ResponseEntity<List<Student>> findStudentByAge(Integer age) {
        if (age != null) {
            return ResponseEntity.ok(studentService.findStudentByAge(age));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
