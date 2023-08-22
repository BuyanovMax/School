package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.stream.LongStream;
import java.util.stream.Stream;

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

    public Integer getValue() {
        long l = System.currentTimeMillis();
        int sum = 0;
        for (int i = 1; i < 1_000_000; i++) {
            sum += i;
        }
        long l1 = System.currentTimeMillis() - l;
        System.out.println(l1);

        return sum;
    }
}
