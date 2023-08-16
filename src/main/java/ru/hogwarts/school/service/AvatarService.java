package ru.hogwarts.school.service;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;



@Service
@Slf4j
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;

    public AvatarService(AvatarRepository avatarRepository, StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    public void uploadFile(Long studentId, MultipartFile avatarFile) throws IOException {
        log.info("Был вызван метод: uploadFile");
        Student student = studentRepository.getById(studentId);
        Path filePath = Path.of(avatarsDir, student.getId() + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (InputStream inputStream = avatarFile.getInputStream();
             OutputStream outputStream = Files.newOutputStream(filePath,CREATE_NEW);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream,1024);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream,1024)) {
            bufferedInputStream.transferTo(bufferedOutputStream);
        }

        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setLongSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);

    }

    public Avatar findAvatar(Long studentId) {
        log.info("Был вызван метод: findAvatar");
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }

    private String getExtensions(String fileName) {
        log.info("Был вызван метод: getExtensions");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public List<Avatar> findAllAvatarsByPages(Integer pageNumber, Integer pageSize) {
        log.info("Был вызван метод: findAllAvatarsByPages");
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }

}

