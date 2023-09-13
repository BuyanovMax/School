package ru.hogwarts.school.service;

import org.springframework.context.annotation.Configuration;

@Configuration
public interface InfoInterface {

    /**
     *  getting port
     */
    Integer getPort();
}
