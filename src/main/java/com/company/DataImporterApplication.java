package com.company;

import com.company.factory.ConnectionManagerFactory;
import com.company.service.FileProcessor;
import com.company.util.FileNameParser;
import org.apache.log4j.FileAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class DataImporterApplication {

    private static final Logger logger = LoggerFactory.getLogger(DataImporterApplication.class);

    public static void main(String[] args) throws IOException {
        logger.info("Starting application...");
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
