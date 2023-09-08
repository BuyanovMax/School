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

    /**
     *  creation of a student in the database and mapping to DTO
     */
    public StudentReadDto createStudent(StudentCreateDto student) {
        log.trace("Был вызван метод: createStudent");
        return Optional.of(student)
                .map(studentCreateMapper::mapTo)
                .map(studentRepository::save)
                .map(studentReadMapper::mapTo)
                .orElseThrow();
    }

    /**
     *  student search in the database and mapping in DTO
     */
    public Optional<StudentReadDto> findStudent(Long id) {
        log.trace("Был вызван метод: findStudent");
        return (studentRepository.findById(id))
                .map(studentReadMapper::mapTo);
    }

    /**
     *  changing the student in the database and mapping to DTO
     */
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

    /**
     *  removal of student in the database and returning the DTO of the deleted entity
     */
    public StudentReadDto deleteStudent(Long id) {
        log.warn("Был вызван метод: deleteStudent");
        Optional<Student> byId = studentRepository.findById(id);
        Optional<Student> student = byId;
        studentRepository.deleteById(id);
        return student.map(studentReadMapper::mapTo).orElseThrow();
    }

    /**
     *  getting all students by age and mapping to DTO
     */
    public List<StudentReadDto> findStudentByAge(Integer age) {
        log.error("Был вызван метод: findStudentByAge");
        return studentRepository.findByAge(age).stream()
                .map(studentReadMapper::mapTo)
                .collect(toList());
    }

    /**
     *  getting all students by age and mapping to DTO
     */
    public List<StudentReadDto> findAllByAgeBetween(int min, int max) {
        log.info("Был вызван метод: findAllByAgeBetween");

        return studentRepository.findAllByAgeBetween(min, max).stream()
                .map(studentReadMapper::mapTo)
                .collect(toList());
    }

    /**
     *  getting all students by faculty and mapping to DTO
     */
    public List<StudentReadDto> findAllStudensByFaculty(Long id) {
        log.info("Был вызван метод: findAllStudensByFaculty");
        return studentRepository.findAllByFaculty_id(id).stream()
                .map(studentReadMapper::mapTo)
                .collect(toList());
    }

    /**
     *   getting the last student id saved in the database
     */
    public Long findLastID() {
        log.info("Был вызван метод: findLastID");
        return studentRepository.findLastID();
    }

    /**
     *   getting the last student id saved in the database
     */
    public Integer findAllStudentCount() {
        log.info("Был вызван метод: findAllStudentCount");
        return studentRepository.findAllStudentCount();
    }


    /**
     *   getting the total number of students
     */
    public Double findAllStudentAgeAverage() {
        log.info("Был вызван метод: findAllStudentAgeAverage");
        return studentRepository.findAllStudentAgeAverage();
    }


    /**
     *   getting the last five students saved in the database
     */
    @EntityGraph(attributePaths = "faculty")
    public List<StudentReadDto> findFiveLastStudent() {
        log.info("Был вызван метод: findFiveLastStudent");
        return studentRepository.findFiveLastStudent().stream()
                .map(studentReadMapper::mapTo)
                .collect(toList());
    }


    /**
     *   getting all students whose name starts with a specific letter
     */
    public List<String> findAllStudentsStartsWithLetter(Character letter) {
        return studentRepository.findAll().stream()
                .filter(student -> student.getName().charAt(0) == letter)
                .sorted(Comparator.comparing(Student::getName))
                .map(student -> student.getName().toUpperCase())
                .collect(toList());
    }

    /**
     *   search for average age of students
     */
    public OptionalDouble findAverageAgeFromAllStudent() {
        return studentRepository.findAll().stream()
                .map(student -> student.getAge())
                .mapToDouble(Integer::doubleValue)
                .average();
    }

    /**
     *   getting all students using multithreading and mapping in DTO
     */
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

    /**
     *   getting all students using multithreading (using a synchronized method) and mapping in DTO
     */
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


    /**
     *   synchronized method (counter by names)
     */
    public static synchronized void writeToTheConsole(List<StudentReadDto> list) {
        synchronized (flag) {
            System.out.println(list.get(count).getName());
            count++;
        }
    }
}
