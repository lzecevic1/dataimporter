package com.company.service;

import com.company.concurrent.DataImporterThread;
import com.company.model.ImportFile;
import com.company.repository.FileRepository;
import com.company.repository.PortedNumberRepository;
import com.company.util.FileNameParser;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class FileProcessor {
    private static String HOME_PATH = "/home/lzecevic/Desktop/importer/";
    @Autowired
    private PortedNumberRepository portedNumberRepository;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private FileNameParser fileNameParser;

    public void saveFiles(List<String> fileNames) {
        fileNames.forEach(fileName -> {
            ImportFile newFile = new ImportFile(fileName, fileNameParser.getTimestampFromFileName(fileName));
            fileRepository.save(newFile);
        });
    }

    public void processFiles(List<String> fileNames) throws IOException, InterruptedException {
        for (String fileName : fileNames) {
            LinkedBlockingQueue<String> portedNumbers = new LinkedBlockingQueue<>();
            readAllPortedNumbersFromFile(fileName, portedNumbers);
            saveNumbers(portedNumbers);
        }
    }

    private void readAllPortedNumbersFromFile(String fileName, LinkedBlockingQueue<String> portedNumbers) throws IOException, InterruptedException {
        FileReader fileReader = new FileReader(HOME_PATH + fileName);
        try (BufferedReader br = new BufferedReader(fileReader)) {
            String currentNumber;
            while (((currentNumber = br.readLine()) != null) && !currentNumber.equals("")) {
                portedNumbers.put(currentNumber);
            }
        }
    }

    private void saveNumbers(LinkedBlockingQueue<String> portedNumbers) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        startThreads(portedNumbers, executorService);
        executorService.shutdown();
    }

    private void startThreads(LinkedBlockingQueue<String> portedNumbers, ExecutorService executorService) {
        for (int i = 0; i < 4; i++) {
            executorService.execute(new DataImporterThread(portedNumbers, portedNumberRepository));
        }
    }
}
