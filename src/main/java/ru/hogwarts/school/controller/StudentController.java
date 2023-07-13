package ru.hogwarts.school.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;
import java.util.List;


@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping("/create")
    public Student createFaculty(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> findFaculty(@PathVariable int id) {
        return studentService.findStudent(id);
    }

    @PutMapping
    public ResponseEntity<Student> editFaculty(@RequestBody Student student) {
        return studentService.editStudent(student);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteFaculty(@PathVariable int id) {
        return studentService.deleteStudent(id);
    }

    @GetMapping("/findAllByAge/{age}")
    public List<Student> findStudentByAge(int age) {
        return studentService.findStudentByAge(age);
    }

}
