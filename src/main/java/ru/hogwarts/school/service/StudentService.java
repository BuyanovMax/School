package ru.hogwarts.school.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.StudentCreateDto;
import ru.hogwarts.school.dto.StudentReadDto;
import ru.hogwarts.school.mapper.StudentCreateMapper;
import ru.hogwarts.school.mapper.StudentReadMapper;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.*;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
@Slf4j
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentCreateMapper studentCreateMapper;
    private final AvatarRepository avatarRepository;
    private final StudentReadMapper studentReadMapper;
    private final FacultyRepository facultyRepository;
    static Object flag = new Object();
    static int count = 0;

    public StudentReadDto createStudent(StudentCreateDto student) {
        log.trace("Был вызван метод: createStudent");
        return Optional.of(student)
                .map(studentCreateMapper::mapTo)
                .map(studentRepository::save)
                .map(studentReadMapper::mapTo)
                .orElseThrow();
    }

    public Optional<StudentReadDto> findStudent(Long id) {
        log.trace("Был вызван метод: findStudent");
        return (studentRepository.findById(id))
                .map(studentReadMapper::mapTo);
    }

    public StudentReadDto updateStudent(long studentId, StudentCreateDto studentCreateDto) {
        log.info("Был вызван метод: updateStudent");
        Student updatedStudent = studentRepository.findById(studentId)
                .map(student -> {
                    student.setName(studentCreateDto.getName());
                    student.setAge(studentCreateDto.getAge());
                    long facultyId = studentCreateDto.getFacultyId();
                    student.setFaculty(
                            facultyRepository.findById(facultyId)
                                    .orElse(null)
                    );
                    return student;
                })
                .orElseThrow();
        studentRepository.saveAndFlush(updatedStudent);

        return Optional.of(updatedStudent)
                .map(studentReadMapper::mapTo)
                .orElseThrow();
    }


    public StudentReadDto deleteStudent(Long id) {
        log.warn("Был вызван метод: deleteStudent");
        Optional<Student> byId = studentRepository.findById(id);
        studentRepository.deleteById(id);
        return byId.map(studentReadMapper::mapTo).orElseThrow();
    }

    public List<StudentReadDto> findStudentByAge(Integer age) {
        log.error("Был вызван метод: findStudentByAge");
        return studentRepository.findByAge(age).stream()
                .map(studentReadMapper::mapTo)
                .collect(toList());
    }

    public List<StudentReadDto> findAllByAgeBetween(int min, int max) {
        log.info("Был вызван метод: findAllByAgeBetween");

        return studentRepository.findAllByAgeBetween(min, max).stream()
                .map(studentReadMapper::mapTo)
                .collect(toList());
    }

    public List<StudentReadDto> findAllStudensByFaculty(Long id) {
        log.info("Был вызван метод: findAllStudensByFaculty");
        return studentRepository.findAllByFaculty_id(id).stream()
                .map(studentReadMapper::mapTo)
                .collect(toList());
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

    @EntityGraph(attributePaths = "faculty")
    public List<StudentReadDto> findFiveLastStudent() {
        log.info("Был вызван метод: findFiveLastStudent");
        return studentRepository.findFiveLastStudent().stream()
                .map(studentReadMapper::mapTo)
                .collect(toList());
    }

    public List<String> findAllStudentsStartsWithLetter(Character letter) {

        return studentRepository.findAll().stream()
                .filter(student -> student.getName().charAt(0) == letter)
                .sorted(Comparator.comparing(Student::getName))
                .map(student -> student.getName().toUpperCase())
                .collect(toList());

    }

    public OptionalDouble findAverageAgeFromAllStudent() {
        return studentRepository.findAll().stream()
                .map(student -> student.getAge())
                .mapToDouble(Integer::doubleValue)
                .average();
    }

    public List<StudentReadDto> checkStudentsMultithreading() {
        List<StudentReadDto> list = studentRepository.findAll().stream()
                .map(studentReadMapper::mapTo)
                .collect(toList());

        Thread main = new Thread(() -> {
            System.out.println(list.get(0).getName());
            System.out.println(list.get(1).getName());
        });
        main.start();
        Thread thread1 = new Thread(() -> {
            System.out.println(list.get(2).getName());
            System.out.println(list.get(4).getName());
        });
        thread1.start();
        Thread thread2 = new Thread(() -> {
            System.out.println(list.get(3).getName());
            System.out.println(list.get(5).getName());
        });
        thread2.start();

        return list;
    }

    public List<StudentReadDto> checkStudentsMultithreadingSynchronized() {
        List<StudentReadDto> list = studentRepository.findAll().stream()
                .map(studentReadMapper::mapTo)
                .collect(toList());

        Thread main = new Thread(() -> {
            writeToTheConsole(list);
            writeToTheConsole(list);
        });
        main.start();
        Thread thread1 = new Thread(() -> {
            writeToTheConsole(list);
            writeToTheConsole(list);
        });
        thread1.start();
        Thread thread2 = new Thread(() -> {
            writeToTheConsole(list);
            writeToTheConsole(list);
        });
        thread2.start();


        return list;

    }

    public static synchronized void writeToTheConsole(List<StudentReadDto> list) {
        synchronized (flag) {
            System.out.println(list.get(count).getName());
            count++;
        }
    }
}
