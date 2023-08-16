package ru.hogwarts.school.controller;


import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.InfoInterface;


@RestController
@Slf4j
public class InfoController {

    private final InfoInterface infoInterface;

    public InfoController(InfoInterface infoInterface) {
        this.infoInterface = infoInterface;
    }

    @GetMapping("/getPort")
    public Integer getPort() {
        log.info("Вызван метод: getPort");
        return infoInterface.getPort();
    }
}
