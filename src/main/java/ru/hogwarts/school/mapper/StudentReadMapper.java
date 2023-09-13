package ru.hogwarts.school.mapper;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.hogwarts.school.dto.StudentReadDto;
import ru.hogwarts.school.model.Student;

import java.util.Optional;

@Component
//@RequiredArgsConstructor
@NoArgsConstructor
public class StudentReadMapper implements Mapper<Student, StudentReadDto>{
    @Autowired
    private  FacultyReadMapper facultyReadMapper;

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
