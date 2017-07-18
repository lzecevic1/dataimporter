package com.company.controller;

import com.company.connectionmanager.ConnectionManager;
import com.company.enums.ConnectionType;
import com.company.factory.ConnectionManagerFactory;
import com.company.service.FileProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/startImport")
public class ImportController {
    @Autowired
    private FileProcessor fileProcessor;
    @Autowired
    private ConnectionManagerFactory connectionManagerFactory;

    @RequestMapping(method = RequestMethod.POST)
    public String start(@RequestBody Integer connectionType) {
        try {
            ConnectionType enumValueOfType = ConnectionType.fromValue(connectionType);
            Optional<ConnectionManager> connectionManagerOptional = connectionManagerFactory.get(enumValueOfType);
            ConnectionManager connectionManager = connectionManagerOptional.orElseThrow(Exception::new);
            manageFiles(connectionManager);
        } catch (Exception exception) {
            System.out.println("Exception: " + exception.getMessage());
            return "Exception";
        }
        return "Successful data import!";
    }

    private void manageFiles(ConnectionManager connectionManager) throws Exception {
        connectionManager.connect();
        List<String> files = connectionManager.download();
        fileProcessor.saveFiles(files);
        fileProcessor.processFiles(files);
    }
}
