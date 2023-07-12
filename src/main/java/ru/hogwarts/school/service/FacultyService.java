package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.Map;
@Service
public class FacultyService {
    private final Map<Long, Faculty> facultyMap = new HashMap<>();
    private Long COUNT = 1L;


    public Faculty createFaculty(Faculty faculty) {
        return facultyMap.put(COUNT,faculty);
    }
    public Faculty findFaculty(int id) {
        return facultyMap.get(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyMap.put(faculty.getId(),faculty);
    }

    public Faculty deleteFaculty(int id) {
        return facultyMap.remove(id);
    }



}
