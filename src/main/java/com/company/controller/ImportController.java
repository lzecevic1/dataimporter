package com.company.controller;

import com.company.connectionmanager.ConnectionManager;
import com.company.enums.ConnectionType;
import com.company.factory.ConnectionManagerFactory;
import com.company.service.FileProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/startImport")
public class ImportController {
    private static final Logger logger = LoggerFactory.getLogger("timeBased");

    @Autowired
    private FileProcessor fileProcessor;
    @Autowired
    private ConnectionManagerFactory connectionManagerFactory;

    @RequestMapping(method = RequestMethod.POST)
    public String start(@RequestBody String connectionType) {
        try {

            ConnectionType enumValueOfType = ConnectionType.fromString(connectionType);
            ConnectionManager connectionManager = connectionManagerFactory.getConnectionManager(enumValueOfType);
            manageFiles(connectionManager);
        } catch (Exception exception) {
            logger.info("Error while importing files. " + exception);
            return "Exception: " + exception.getMessage();
        }
        return "Successful data import!";
    }

    private void manageFiles(ConnectionManager connectionManager) throws Exception {
        try {
            connectionManager.connect();
            List<String> files = connectionManager.download();
            fileProcessor.processFiles(files);
        } catch (Exception e) {
            throw e;
        } finally {
            connectionManager.disconnect();
        }
    }
}
