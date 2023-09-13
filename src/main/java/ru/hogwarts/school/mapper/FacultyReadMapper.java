package ru.hogwarts.school.mapper;

import org.springframework.stereotype.Component;
import ru.hogwarts.school.dto.FacultyReadDto;
import ru.hogwarts.school.model.Faculty;
@Component
public class FacultyReadMapper implements Mapper<Faculty, FacultyReadDto>{
    @Override
    public FacultyReadDto mapTo(Faculty obj) {
        return new FacultyReadDto(
                obj.getName()
        );
    }
}
