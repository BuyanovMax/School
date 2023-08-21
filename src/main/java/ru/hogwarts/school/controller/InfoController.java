package ru.hogwarts.school.controller;


import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.InfoInterface;
import ru.hogwarts.school.service.InfoService;

import javax.persistence.criteria.CriteriaBuilder;


@RestController
@Slf4j
public class InfoController {

    private final InfoInterface infoInterface;
    private final InfoService infoService;

    public InfoController(InfoInterface infoInterface, InfoService infoService) {
        this.infoInterface = infoInterface;
        this.infoService = infoService;
    }

    @GetMapping("/getPort")
    public Integer getPort() {
        log.info("Вызван метод: getPort");
        return infoInterface.getPort();
    }
    @GetMapping("getValue")
    public Integer getValue() {
        return infoService.getValue();
    }
}
