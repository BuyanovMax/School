package ru.hogwarts.school.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/avatar")
public class AvatarController {

    private final StudentService studentService;
    private final AvatarService avatarService;
    private final AvatarRepository avatarRepository;

    public AvatarController(StudentService studentService, AvatarService avatarService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarService = avatarService;
        this.avatarRepository = avatarRepository;
    }

    @PostMapping(value = "/{studentId}/cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@PathVariable Long studentId, @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() > 1024 * 900) {
            return ResponseEntity.badRequest().body("File is too big");
        }

        avatarService.uploadFile(studentId, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/avatar-from-db")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) {
        Avatar avatar = avatarService.findAvatar(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());

    }

    @GetMapping(value = "/{id}/avatar-from-file")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatar(id);
        Path path = Path.of(avatar.getFilePath());
        try (InputStream inputStream = Files.newInputStream(path);
             OutputStream outputStream = response.getOutputStream()) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int)avatar.getLongSize());
            inputStream.transferTo(outputStream);
        }
    }

    @GetMapping("/findAllAvatarsByPages")
    public List<Avatar> findAllAvatarsByPages(@RequestParam("page") Integer pageNumber, @RequestParam("size") Integer pageSize) {
        return avatarService.findAllAvatarsByPages(pageNumber,pageSize);
    }



}
