package com.github.benchdoos.meetroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MeetroomApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeetroomApplication.class, args);
    }

}
