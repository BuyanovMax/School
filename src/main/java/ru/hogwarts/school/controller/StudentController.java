package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;

import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping("{id}")
    public Student findFaculty(@PathVariable int id) {
        return studentService.findStudent(id);
    }

    @PostMapping("/create")
    public Student createFaculty(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping
    public Student editFaculty(Student student) {
        return studentService.editStudent(student);
    }


    @DeleteMapping("{id}")
    public Student deleteFaculty(@PathVariable int id) {
        return studentService.deleteStudent(id);
    }

    @GetMapping("/findAllById/{id}")
    public List<Student> findStudentByAge(int age) {
        return studentService.findStudentByAge(age);
    }

}
