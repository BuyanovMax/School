package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.Map;
@Service
public class StudentService {

    private final Map<Long, Student> studentMap = new HashMap<>();
    private Long COUNT = 1L;

    public Student createStudent(Student student) {
        return studentMap.put(COUNT,student);
    }
    public Student findStudent(int id) {
        return studentMap.get(id);
    }

    public Student editStudent(Student student) {
        return studentMap.put(student.getId(),student);
    }

    public Student deleteStudent(int id) {
        return studentMap.remove(id);
    }
}
