package com.company;

import com.company.factory.ConnectionManagerFactory;
import com.company.service.FileProcessor;
import com.company.util.FileFilter;
import com.company.util.FileNameParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DataImporterApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataImporterApplication.class, args);
    }

/*
    @Bean
    public FileFilter fileFilter() {
        return new FileFilter();
    }
*/

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
