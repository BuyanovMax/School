package ru.hogwarts.school.mapper;

import org.springframework.stereotype.Component;
import ru.hogwarts.school.dto.FacultyCreateDto;
import ru.hogwarts.school.model.Faculty;
@Component
public class FacultyCreateMapper implements Mapper<FacultyCreateDto,Faculty>{
    @Override
    public Faculty mapTo(FacultyCreateDto obj) {
        return new Faculty(
                obj.getName(),
                obj.getColor()
        );
    }
}
