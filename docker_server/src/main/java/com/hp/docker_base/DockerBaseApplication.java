package com.hp.docker_base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DockerBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(DockerBaseApplication.class, args);
    }

    @GetMapping("/")
    public String hello() {
        return "hello docker with springboot !!!";
    }
}
