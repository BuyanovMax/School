package ru.hogwarts.school.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hogwarts.school.dto.FacultyCreateDto;
import ru.hogwarts.school.dto.StudentCreateDto;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Optional;
@Component
@RequiredArgsConstructor
public class StudentCreateMapper implements Mapper<StudentCreateDto, Student> {
    private final FacultyRepository facultyRepository;
    private final FacultyCreateMapper facultyCreateMapper;

    @Override
    public Student mapTo(StudentCreateDto obj) {
        Student student = new Student();
        student.setName(obj.getName());
        student.setAge(obj.getAge());
        Optional.ofNullable(obj.getFacultyId()).ifPresent
                (facultyId -> student.setFaculty(facultyRepository.findById(facultyId).orElse(null))
                );

        return student;


    }
}
