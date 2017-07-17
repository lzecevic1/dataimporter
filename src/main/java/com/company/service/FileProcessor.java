package com.company.service;

import com.company.concurrent.DataImporterThread;
import com.company.model.ImportFile;
import com.company.repository.FileRepository;
import com.company.repository.PortedNumberRepository;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.concurrent.*;

public class FileProcessor {

    @Autowired
    private PortedNumberRepository portedNumberRepository;
    @Autowired
    private FileRepository fileRepository;

    public void processFiles(FTPFile[] files) throws IOException, InterruptedException {
        for (FTPFile file : files) {
            LinkedBlockingQueue<String> portedNumbers = new LinkedBlockingQueue<>();
            readAllPortedNumbers(file, portedNumbers);
            saveNumbers(portedNumbers);
        }
    }

    private void readAllPortedNumbers(FTPFile file, LinkedBlockingQueue<String> portedNumbers) throws IOException, InterruptedException {
        FileReader fileReader = new FileReader("/home/lzecevic/Desktop/importer/" + file.getName());
        try (BufferedReader br = new BufferedReader(fileReader)) {
            String currentNumber;
            while ((currentNumber = br.readLine()) != null && !currentNumber.equals("")) {
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

    public void saveFiles(FTPFile[] files) {
        for (FTPFile file : files) {
            ImportFile newFile = new ImportFile(file.getName(), file.getTimestamp());
            fileRepository.save(newFile);
        }
    }
}
