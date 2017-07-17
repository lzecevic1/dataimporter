package com.company.controller;

import com.company.connectionmanager.ConnectionManager;
import com.company.enums.ConnectionType;
import com.company.factory.ConnectionManagerFactory;
import com.company.impl.FTPFileFilterImpl;
import com.company.repository.FileRepository;
import com.company.service.FileProcessor;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/startImport")
public class ImportController {

    @Autowired
    private FileProcessor fileProcessor;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ConnectionManagerFactory connectionManagerFactory;

    @RequestMapping(method = RequestMethod.POST)
    public String start(@RequestBody Integer connectionType) {
        try {
            ConnectionType enumValueOfType = ConnectionType.fromValue(connectionType);
            Optional<ConnectionManager> connectionManager = connectionManagerFactory.get(enumValueOfType);
            FTPFile[] files = retrieveFiles(connectionManager.orElseThrow(Exception::new));
            fileProcessor.processFiles(files);
        } catch (Exception exception) {
            System.out.println("Exception: " + exception.getMessage());
        }
        return "Successful data import!";
    }

    private FTPFile[] retrieveFiles(ConnectionManager manager) throws Exception {
        FTPClient ftpClient = manager.connect();
        FTPFile[] files = ftpClient.listFiles("/DataImporter/test/Test/", new FTPFileFilterImpl(fileRepository));
        manager.download(files);
        fileProcessor.saveFiles(files);
        return files;
    }
}
