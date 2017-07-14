package com.company.controller;

import com.company.connectionmanager.ConnectionManager;
import com.company.connectionmanager.FtpConnectionManager;
import com.company.enums.ConnectionType;
import com.company.impl.FTPFileFilterImpl;
import com.company.model.ConnectionData;
import com.company.repository.ConnectionDataRepository;
import com.company.service.FileProcessor;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/startImport")
public class ImportController {
    @Autowired
    private ConnectionDataRepository connectionDataRepository;

    // Vraca string?
    @RequestMapping(method = RequestMethod.POST)
    public void start(ConnectionType type) throws Exception {
        if (type.equals(ConnectionType.FTP)) {
            Optional<ConnectionData> connectionData = connectionDataRepository.findByType(ConnectionType.FTP);
            FtpConnectionManager connectionManager = new FtpConnectionManager(connectionData.orElseThrow(Exception::new));

            try {
                retrieveFile(connectionManager);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    // Putanja do file-ova na serveru?
    private void retrieveFile(ConnectionManager manager) throws Exception {
        FTPClient ftpClient = manager.connect();
        FTPFile[] files = ftpClient.listFiles("", new FTPFileFilterImpl());
        manager.download(files);
        processFiles(files);
    }

    private void processFiles(FTPFile[] files) throws IOException {
        FileProcessor fileProcessor = new FileProcessor();
        fileProcessor.processFiles(files);
    }
}
