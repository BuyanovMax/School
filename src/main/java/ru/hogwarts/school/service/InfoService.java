package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
public class InfoService implements InfoInterface {

    private final ServerProperties server;
    public InfoService(ServerProperties server) {
        this.server = server;
    }

    @Override
    public Integer getPort() {
        return server.getPort();
    }
}
