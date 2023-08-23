package ru.hogwarts.school.dto;

import lombok.Value;

@Value
public class StudentCreateDto {

    String name;
    Integer age;
    Long facultyId;

}
