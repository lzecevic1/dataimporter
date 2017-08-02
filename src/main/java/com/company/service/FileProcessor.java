package com.company.service;

import com.company.concurrent.DataImporterThread;
import com.company.model.ImportFile;
import com.company.model.PortedNumber;
import com.company.repository.FileRepository;
import com.company.repository.PortedNumberRepository;
import com.company.util.FileNameParser;
import com.company.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class FileProcessor {
    private static int THREAD_POOL_SIZE = 4;
    private static final Logger LOGGER = LoggerFactory.getLogger("timeBased");

    @Autowired
    private PortedNumberRepository portedNumberRepository;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private FileNameParser fileNameParser;

    private ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    public void processFiles(List<String> fileNames) throws IOException, InterruptedException {
        for (String fileName : fileNames) {
            LinkedBlockingQueue<PortedNumber> portedNumbers = new LinkedBlockingQueue<>();
            AtomicBoolean fileRead = new AtomicBoolean(false);
            startThreads(portedNumbers, fileRead);
            readAllPortedNumbersFromFile(portedNumbers, fileName);
            fileRead.set(true);
            saveFile(fileName);
            LOGGER.info("Processing file " + fileName + " finished.");
        }
    }

    private void readAllPortedNumbersFromFile(LinkedBlockingQueue<PortedNumber> portedNumbers, String fileName) throws IOException, InterruptedException {
        FileReader fileReader = new FileReader(Properties.getProperty("file.location") + fileName);
        try (BufferedReader br = new BufferedReader(fileReader)) {
            String currentNumber;
            while (((currentNumber = br.readLine()) != null) && !currentNumber.equals("")) {
                portedNumbers.put(new PortedNumber(currentNumber, fileName));
            }
        }
    }

    private void startThreads(LinkedBlockingQueue<PortedNumber> portedNumbers, AtomicBoolean fileRead) {
        for (int i = 0; i < THREAD_POOL_SIZE; i++) {
            executorService.execute(new DataImporterThread(portedNumbers, portedNumberRepository, fileRead));
        }
    }

    private void saveFile(String fileName) {
        ImportFile newFile = new ImportFile(fileName, fileNameParser.getTimestampFromFileName(fileName));
        fileRepository.save(newFile);
    }
}
