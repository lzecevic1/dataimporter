package com.company;

import com.company.factory.ConnectionManagerFactory;
import com.company.service.FileProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DataImporterApplication {

    @Bean
    public FileProcessor fileProcessor() {
        return new FileProcessor();
    }

    @Bean
    public ConnectionManagerFactory connectionManagerFactory() {
        return new ConnectionManagerFactory();
    }

    public static void main(String[] args) {
        SpringApplication.run(DataImporterApplication.class, args);
    }
}
