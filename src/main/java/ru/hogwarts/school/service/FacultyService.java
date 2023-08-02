package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.*;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Optional<Faculty> findFaculty(Long id) {
        return facultyRepository.findById(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);

    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }


    public List<Faculty> findAllByColor(String color) {
        return facultyRepository.findAllByColor(color);
    }

    public List<Faculty> findFacultyByNameOrColor(String name, String color) {
        return facultyRepository.findAllByNameOrColorIgnoreCase(name, color);

    }

    public Faculty findFacultyByStudent(Long id) {
        return facultyRepository.findByStudent_id(id);
    }

    public java.lang.Long findLastID() {
        return facultyRepository.findLastID();
    }

}
