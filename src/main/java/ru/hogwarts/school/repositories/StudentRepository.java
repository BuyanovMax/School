package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.List;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);

    Student save(Student student);
    List<Student> findAllByAgeBetween(int min, int max);

    List<Student> findAllByFaculty_id(Long id);

    @Query(value = "select id from student order by id desc limit 1;",nativeQuery = true)
    Long findLastID();

    @Query(value = "select count(name) from student", nativeQuery = true)
    Integer findAllStudentCount();

    @Query(value = "select avg(age) from student", nativeQuery = true)
    Double findAllStudentAgeAverage();

    @Query(value = "select * from student order by id desc limit 5", nativeQuery = true)
    List<Student> findFiveLastStudent();






}
