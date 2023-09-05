package ru.hogwarts.school;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AvatarServiceTest {
    @InjectMocks
    private AvatarService avatarService;
    @Mock
    private AvatarRepository avatarRepository;
    @InjectMocks
    private StudentService studentService;
    @Mock
    private StudentRepository studentRepository;

    @Captor
    private ArgumentCaptor<Avatar> avatarArgumentCaptor;


    private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>("postgres")
            .withUsername("postgres")
            .withPassword("shadow")
            .withDatabaseName("postgres");

    @BeforeAll
    static void runContainer() {
        POSTGRE_SQL_CONTAINER.start();
    }
    @Test
    void uploadFileTest() throws IOException {

        Student student = new Student(1L, "Name", 1);
        Avatar avatar = new Avatar();
        avatar.setStudent(student);
        Mockito.when(studentRepository.getById(1L)).thenReturn(student);
        Mockito.when(avatarRepository.findByStudentId(1L)).thenReturn(Optional.of(avatar));
        byte[] content = {2, 5, 6, 98, 7, 8, 9};
        MultipartFile multipartFile = new MockMultipartFile("name", "originalName.txt", "contentType", content);
        ReflectionTestUtils.setField(avatarService, "avatarsDir", "/value");

        avatarService.uploadFile(student.getId(), multipartFile);
        verify(avatarRepository, times(1)).save(avatarArgumentCaptor.capture());
        Avatar value = avatarArgumentCaptor.getValue();
        assertEquals(content, value.getData());
        assertEquals("\\value\\1.txt", avatar.getFilePath());
    }


}


