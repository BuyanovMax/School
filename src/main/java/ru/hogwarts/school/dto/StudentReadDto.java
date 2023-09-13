package ru.hogwarts.school.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentReadDto {

    Long id;
    String name;
    Integer age;
    FacultyReadDto faculty;
}
