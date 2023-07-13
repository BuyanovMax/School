package ru.hogwarts.school.service;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
    public ResponseEntity<Student> findStudent(long id) {
        if (studentMap.containsKey(id)) {
            return ResponseEntity.ok(studentMap.get(id));
        }
        return ResponseEntity.notFound().build();

    }

    public ResponseEntity<Student> editStudent(Student student) {
        if (studentMap.containsKey(student.getId())) {
            return ResponseEntity.ok(studentMap.put(student.getId(),student));
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Student> deleteStudent(long id) {
        if (studentMap.containsKey(id)) {
            return ResponseEntity.ok(studentMap.remove(id));
        }
        return ResponseEntity.notFound().build();
    }

    public List<Student> findStudentByAge(int age) {
        return studentMap.values().stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());

    }
}
