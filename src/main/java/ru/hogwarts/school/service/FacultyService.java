package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final Map<Long, Faculty> facultyMap = new HashMap<>();
    private Long COUNT = 0L;


    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++COUNT);
        facultyMap.put(COUNT, faculty);
        return faculty;
    }

    public Faculty findFaculty(long id) {
        return facultyMap.get(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        facultyMap.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty deleteFaculty(long id) {
        return facultyMap.remove(id);
    }


    public List<Faculty> findAllByColor(String color) {

        List<Faculty> list = facultyMap.values().stream()
                .filter(a -> (a.getColor()).equals(color))
                .collect(Collectors.toList());

        return list;
    }
}
