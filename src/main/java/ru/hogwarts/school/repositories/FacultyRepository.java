package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Faculty;
import java.util.List;
@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    List<Faculty> findByColor(String color);

    List<Faculty> findAllByNameOrColorIgnoreCase(String name, String color);

    Faculty findByStudent_id(Long id);

    @Query(value = "select id from faculty order by id desc limit 1;",nativeQuery = true)
    Long findLastID();
}
