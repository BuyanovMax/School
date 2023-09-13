package ru.hogwarts.school.mapper;

public interface Mapper <F,T>{
    T mapTo(F obj);
}
