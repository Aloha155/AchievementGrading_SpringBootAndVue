package com.hsf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableJpaAuditing
@SpringBootApplication
@EnableWebMvc
public class HsfbsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HsfbsApplication.class, args);
    }

}
