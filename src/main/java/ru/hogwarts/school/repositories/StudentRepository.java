package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.List;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);

    List<Student> findAllByAgeBetween(int min, int max);

    List<Student> findAllByFaculty_id(Long id);
    @Query(value = "select s from Student s where s.faculty.id = :id")
    List<Student> findStudentsByFacultyId(long id);
    @Query(value = "select id from student order by id desc limit 1;",nativeQuery = true)
    Long findLastID();


}
