package com.victor.library2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Library2Application {

    public static void main(String[] args) {
        SpringApplication.run(Library2Application.class, args);
    }
}
