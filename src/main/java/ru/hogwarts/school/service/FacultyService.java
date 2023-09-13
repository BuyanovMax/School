package ru.hogwarts.school.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.FacultyCreateDto;
import ru.hogwarts.school.dto.FacultyReadDto;
import ru.hogwarts.school.mapper.FacultyCreateMapper;
import ru.hogwarts.school.mapper.FacultyReadMapper;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.*;

import static java.util.stream.Collectors.*;

@RequiredArgsConstructor
@Service
@Slf4j
public final class FacultyService {

    private final FacultyRepository facultyRepository;
    private final FacultyCreateMapper facultyCreateMapper;
    private final FacultyReadMapper facultyReadMapper;

    /**
     *  creation of a faculty in the database and mapping to DTO
     */
    public FacultyReadDto createFaculty(FacultyCreateDto faculty) {
        log.trace("Был вызван метод: createFaculty");
        return Optional.of(faculty)
                .map(facultyCreateMapper::mapTo)
                .map(facultyRepository::save)
                .map(facultyReadMapper::mapTo)
                .orElseThrow();
    }


    /**
     *  faculty search in the database and mapping in DTO
     */
    public Optional<FacultyReadDto> findFaculty(Long id) {
        log.debug("Был вызван метод: findFaculty");
        return (facultyRepository.findById(id))
                .map(facultyReadMapper::mapTo);
    }

    /**
     *  changing the faculty in the database and mapping to DTO
     */
    public FacultyReadDto updateFaculty(long facultyId,FacultyCreateDto facultyCreateDto) {
        log.info("Был вызван метод: editFaculty");
        Faculty updateFaculty = facultyRepository.findById(facultyId)
                .map(faculty -> {
                    faculty.setName(facultyCreateDto.getName());
                    faculty.setColor(facultyCreateDto.getColor());
                    return faculty;
                })
                .orElseThrow();
        facultyRepository.saveAndFlush(updateFaculty);

        return Optional.of(updateFaculty)
                .map(facultyReadMapper::mapTo)
                .orElseThrow();


    }

    /**
     *  removal of faculty in the database and returning the DTO of the deleted entity
     */
    public FacultyReadDto deleteFaculty(Long id) {
        log.warn("Был вызван метод: deleteFaculty");
        Optional<Faculty> byId = facultyRepository.findById(id);
        Optional<Faculty> faculty = byId;
        facultyRepository.deleteById(id);
        return faculty.map(facultyReadMapper::mapTo).orElseThrow();

    }

    /**
     *  search for all faculties by color and mapping in DTO
     */
    public List<FacultyReadDto> findAllFacultyByColor(String color) {
        log.error("Был вызван метод: findAllByColor");
        return facultyRepository.findByColor(color).stream()
                .map(facultyReadMapper::mapTo)
                .collect(toList());
    }

    /**
     *  search for all faculties by name or color
     */
    public List<FacultyReadDto> findFacultyByNameOrColor(String name, String color) {
        log.info("Был вызван метод: findFacultyByNameOrColor");
        return facultyRepository.findAllByNameOrColorIgnoreCase(name, color).stream()
                .map(facultyReadMapper::mapTo)
                .collect(toList());

    }

    /**
     *   search faculty by student
     */
    public FacultyReadDto findFacultyByStudent(Long id) {
        log.info("Был вызван метод: findFacultyByStudent");
        return Optional.of(facultyRepository.findByStudent_id(id))
                .map(facultyReadMapper::mapTo)
                .orElseThrow();
    }

    /**
     *   getting the last faculty id saved in the database
     */
    public Long findLastID() {
        log.info("Был вызван метод: findLastID");
        return facultyRepository.findLastID();
    }

    /**
     *   search for the longest faculty name
     */
    public Optional<String> findLongestFacultyName() {
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .sorted(Comparator.comparing(String::length).reversed())
                .limit(1)
                .findFirst();

    }

}
