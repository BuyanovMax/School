package ru.hogwarts.school.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StudentService {
    private final StudentRepository studentRepository;

    private final AvatarRepository avatarRepository;


    public StudentService(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    public Student createStudent(Student student) {
        log.trace("Был вызван метод: createStudent");
        return studentRepository.save(student);
    }

    public Optional<Student> findStudent(Long id) {
        log.trace("Был вызван метод: findStudent");
        return studentRepository.findById(id);
    }

    public Student editStudent(Student student) {
        log.info("Был вызван метод: editStudent");
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        log.warn("Был вызван метод: deleteStudent");
        studentRepository.deleteById(id);
    }

    public List<Student> findStudentByAge(Integer age) {
        log.error("Был вызван метод: findStudentByAge");
        return studentRepository.findByAge(age);
    }

    public List<Student> findAllByAgeBetween(int min, int max) {
        log.info("Был вызван метод: findAllByAgeBetween");
        return studentRepository.findAllByAgeBetween(min, max);
    }

    public List<Student> findAllStudensByFaculty(Long id) {
        log.info("Был вызван метод: findAllStudensByFaculty");
        return studentRepository.findAllByFaculty_id(id);
    }

    public Long findLastID() {
        log.info("Был вызван метод: findLastID");
        return studentRepository.findLastID();
    }

    public Integer findAllStudentCount() {
        log.info("Был вызван метод: findAllStudentCount");
        return studentRepository.findAllStudentCount();
    }

    public Double findAllStudentAgeAverage() {
        log.info("Был вызван метод: findAllStudentAgeAverage");
        return studentRepository.findAllStudentAgeAverage();
    }

    public List<Student> findFiveLastStudent() {
        log.info("Был вызван метод: findFiveLastStudent");
        return studentRepository.findFiveLastStudent();
    }

}
