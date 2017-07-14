package com.company.service;

import com.company.concurrent.DataImporterThread;
import org.apache.commons.net.ftp.FTPFile;

import java.io.*;
import java.util.concurrent.*;

public class FileProcessor {

    // File path ovdje ili da se procita/proslijedi iz FtpConnectionManager
    private static String FILE_PATH = "/opt/importer/cache/files/";

    public void processFiles(FTPFile[] files) throws IOException {
        for (FTPFile file : files) {
            LinkedBlockingQueue<String> portedNumbers = new LinkedBlockingQueue<>();
            readAllPortedNumbers(file, portedNumbers);
            ExecutorService executorService = Executors.newFixedThreadPool(4);
            startThreads(portedNumbers, executorService);
            executorService.shutdown();
        }
    }

    private void startThreads(LinkedBlockingQueue<String> portedNumbers, ExecutorService executorService) {
        for (int i = 0; i < 4; i++) {
            executorService.submit(new DataImporterThread(portedNumbers));
        }
    }

    private void readAllPortedNumbers(FTPFile file, LinkedBlockingQueue<String> portedNumbers) throws IOException {
        FileReader fileReader = new FileReader(FILE_PATH + file.getName());
        try (BufferedReader br = new BufferedReader(fileReader)) {
            while (br.readLine() != null) {
                portedNumbers.add(br.readLine());
            }
        }
    }
}
