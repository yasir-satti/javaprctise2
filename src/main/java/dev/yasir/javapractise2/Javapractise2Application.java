package dev.yasir.javapractise2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "dev.yasir*")
public class Javapractise2Application {

    public static void main(String[] args) {
        SpringApplication.run(Javapractise2Application.class, args);
    }
}
