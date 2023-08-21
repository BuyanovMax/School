package ru.hogwarts.school.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public final class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    public Faculty createFaculty(Faculty faculty) {
        log.trace("Был вызван метод: createFaculty");
        return facultyRepository.save(faculty);
    }

    public Optional<Faculty> findFaculty(Long id) {
        log.debug("Был вызван метод: findFaculty");
        return facultyRepository.findById(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        log.info("Был вызван метод: editFaculty");
        return facultyRepository.save(faculty);

    }

    public void deleteFaculty(Long id) {
        log.warn("Был вызван метод: deleteFaculty");
        facultyRepository.deleteById(id);
    }


    public List<Faculty> findAllByColor(String color) {
        log.error("Был вызван метод: findAllByColor");
        return facultyRepository.findByColor(color);
    }

    public List<Faculty> findFacultyByNameOrColor(String name, String color) {
        log.info("Был вызван метод: findFacultyByNameOrColor");
        return facultyRepository.findAllByNameOrColorIgnoreCase(name, color);

    }

    public Faculty findFacultyByStudent(Long id) {
        log.info("Был вызван метод: findFacultyByStudent");
        return facultyRepository.findByStudent_id(id);
    }

    public Long findLastID() {
        log.info("Был вызван метод: findLastID");
        return facultyRepository.findLastID();
    }

    public Optional<String> findLongestFacultyName() {
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .sorted(Comparator.comparing(String::length).reversed())
                .limit(1)
                .findFirst();

    }

}
