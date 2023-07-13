package ru.hogwarts.school.service;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<Faculty> findFaculty(long id) {
        if (facultyMap.containsKey(id)) {
            return ResponseEntity.ok(facultyMap.get(id));
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Faculty> editFaculty(Faculty faculty) {
        if (facultyMap.containsKey(faculty.getId())) {
            facultyMap.put(faculty.getId(), faculty);
            return ResponseEntity.ok(faculty);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Faculty> deleteFaculty(long id) {
        if (facultyMap.containsKey(id)) {
            return ResponseEntity.ok(facultyMap.remove(id));
        }
        return ResponseEntity.notFound().build();
    }


    public List<Faculty> findAllByColor(String color) {
        return facultyMap.values().stream()
                .filter(a -> (a.getColor()).equals(color))
                .collect(Collectors.toList());
    }
}
