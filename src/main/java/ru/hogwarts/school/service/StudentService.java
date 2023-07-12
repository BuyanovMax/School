package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final Map<Long, Student> studentMap = new HashMap<>();
    private Long COUNT = 0L;

    public Student createStudent(Student student) {
        student.setId(++COUNT);
        studentMap.put(COUNT,student);
        return student;
    }
    public Student findStudent(long id) {
        return studentMap.get(id);
    }

    public Student editStudent(Student student) {
        studentMap.put(student.getId(),student);
        return student;
    }

    public Student deleteStudent(long id) {
        return studentMap.remove(id);
    }

    public List<Student> findStudentByAge(int age) {


        List<Student> list = studentMap.values().stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
        return list;

    }
}
