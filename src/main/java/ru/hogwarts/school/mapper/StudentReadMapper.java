package ru.hogwarts.school.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hogwarts.school.dto.StudentReadDto;
import ru.hogwarts.school.model.Student;

import java.util.Optional;
@Component
@RequiredArgsConstructor
public class StudentReadMapper implements Mapper<Student, StudentReadDto>{
    private final FacultyReadMapper facultyReadMapper;
    @Override
    public StudentReadDto mapTo(Student obj) {
        StudentReadDto studentReadDto = new StudentReadDto();
        studentReadDto.setId(obj.getId());
        studentReadDto.setName(obj.getName());
        studentReadDto.setAge(obj.getAge());
        Optional.ofNullable(obj.getFaculty()).ifPresent
                        (faculty -> studentReadDto.setFaculty(facultyReadMapper.mapTo(faculty))
        );

        return studentReadDto;
    }
}
