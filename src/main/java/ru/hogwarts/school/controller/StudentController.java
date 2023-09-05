package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.dto.StudentCreateDto;
import ru.hogwarts.school.dto.StudentReadDto;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;


@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;


    public StudentController(StudentService studentService) {
        this.studentService = studentService;
        ;
    }


    @PostMapping
    public ResponseEntity<StudentReadDto> createStudent(@RequestBody StudentCreateDto student) {
        if (student != null) {
            return ResponseEntity.ok(studentService.createStudent(student));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<StudentReadDto>> findStudent(@PathVariable Long id) {
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
    public ResponseEntity<StudentReadDto> updateStudent(@RequestBody StudentCreateDto student, Long studentId) {
        try {
            if (student != null) {
                return ResponseEntity.ok(studentService.updateStudent(studentId,student));
            }
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }


    @DeleteMapping("{id}")
    public StudentReadDto deleteFaculty(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }

    @GetMapping("/findAllByAge/")
    public ResponseEntity<List<StudentReadDto>> findStudentByAge(Integer age) {
        if (age != null) {
            return ResponseEntity.ok(studentService.findStudentByAge(age));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping
    public ResponseEntity<List<StudentReadDto>> findAllByAgeBetween(Integer min, Integer max) {
        if (min != null || max != null) {
            return ResponseEntity.ok(studentService.findAllByAgeBetween(min, max));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/StudentByFaculty/")
    public ResponseEntity<List<StudentReadDto>> findByFacultyId(Long id) {
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
    public ResponseEntity<List<StudentReadDto>> findFiveLastStudent() {
        return ResponseEntity.ok(studentService.findFiveLastStudent());
    }

    @GetMapping("findAllStudentsStartsWithLetter")
    public ResponseEntity<List<String>> findAllStudentsStartsWithLetter(Character letter) {
        return ResponseEntity.ok(studentService.findAllStudentsStartsWithLetter(letter));
    }

    @GetMapping("findAverageAgeFromAllStudent")
    public ResponseEntity<OptionalDouble> findAverageAgeFromAllStudent() {
        return ResponseEntity.ok(studentService.findAverageAgeFromAllStudent());
    }

    @GetMapping("checkStudentsMultithreading")
    public ResponseEntity<List<StudentReadDto>> checkStudentsMultithreading() {
        return ResponseEntity.ok(studentService.checkStudentsMultithreading());

    }  @GetMapping("checkStudentsMultithreadingSynchronized")
    public ResponseEntity<List<StudentReadDto>> checkStudentsMultithreadingSynchronized() {
        return ResponseEntity.ok(studentService.checkStudentsMultithreadingSynchronized());
    }































}
