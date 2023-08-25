package ru.hogwarts.school.mapper;

import org.springframework.stereotype.Component;
import ru.hogwarts.school.dto.FacultyCreateDto;
import ru.hogwarts.school.model.Faculty;

@Component
public class FacultyCreateMapper implements Mapper<FacultyCreateDto, Faculty> {
    @Override
    public Faculty mapTo(FacultyCreateDto obj) {

        Faculty faculty = new Faculty();
        faculty.setName(obj.getName());
        faculty.setColor(obj.getColor());

        return faculty;

    }
}
