package com.company;

import com.company.factory.ConnectionManagerFactory;
import com.company.service.FileProcessor;
import com.company.util.FileNameParser;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.slf4j.Logger;

@SpringBootApplication
public class DataImporterApplication {

    private static final Logger logger = LoggerFactory.getLogger(DataImporterApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DataImporterApplication.class, args);
    }

    @Bean
    public FileNameParser fileNameParser() {
        return new FileNameParser();
    }

    @Bean
    public FileProcessor fileProcessor() {
        return new FileProcessor();
    }

    @Bean
    public ConnectionManagerFactory connectionManagerFactory() {
        return new ConnectionManagerFactory();
    }
}
