package ru.hogwarts.school.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!production")
public class InfoServiceTest implements InfoInterface {

    @Override
    public Integer getPort() {
        return 324165446;
    }
}
