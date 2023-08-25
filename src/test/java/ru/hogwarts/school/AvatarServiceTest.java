package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
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
    @Test
    void findAllAvatarsByPagesTest() throws IOException {

        byte[] content = {2, 5, 6, 98, 7, 8, 9};
        MultipartFile multipartFile = new MockMultipartFile("name", "originalName.txt", "contentType", content);
        byte[] content2 = {2, 5, 6, 98, 7, 8, 95};
        MultipartFile multipartFile2 = new MockMultipartFile("name2", "origiiinalName.txt", "contentType2", content2);
        int pageNumber = 1;
        int pageSize = 2;
        Student student = new Student(1L, "Name", 1);
        Student student2 = new Student(2L, "Name2", 2);

//        when(studentService.createStudent(student)).thenReturn(student);
//        when(studentService.createStudent(student2)).thenReturn(student2);


        Avatar avatar = new Avatar();
        Avatar avatar2 = new Avatar();

        Path filePath = Path.of("/avatars", student.getId() + ".jpeg");
        Path filePath2 = Path.of("/avatars", student2.getId() + ".jpeg");
        avatar.setId(1L);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setLongSize(multipartFile.getSize());
        avatar.setMediaType(multipartFile.getContentType());
        avatar.setData(multipartFile.getBytes());

        avatar2.setId(2L);
        avatar2.setStudent(student2);
        avatar2.setFilePath(filePath2.toString());
        avatar2.setLongSize(multipartFile2.getSize());
        avatar2.setMediaType(multipartFile2.getContentType());
        avatar2.setData(multipartFile2.getBytes());

        when(avatarRepository.save(avatar)).thenReturn(avatar);
        when(avatarRepository.save(avatar2)).thenReturn(avatar2);

        List<Avatar> avatar1 = List.of(avatar, avatar2);

        ReflectionTestUtils.setField(avatarService, "avatarsDir", "/value");

        when(avatarRepository.findAll(any(PageRequest.class)).getContent()).thenReturn(avatar1);
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize);
        List<Avatar> expected = avatarRepository.findAll(pageRequest).getContent();
        assertEquals(expected,avatar1);
    }



}


