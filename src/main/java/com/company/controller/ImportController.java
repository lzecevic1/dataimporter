package com.company.controller;

import com.company.connectionmanager.ConnectionManager;
import com.company.model.ConnectionType;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/startImport")
public class ImportController {
    private static final Logger LOGGER = LoggerFactory.getLogger("timeBased");

    @Autowired
    private FileProcessor fileProcessor;
    @Autowired
    private ConnectionManagerFactory connectionManagerFactory;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @RequestMapping(method = RequestMethod.POST)
    public String start(@RequestBody String connectionType) {
        try {
            ConnectionType enumValueOfType = ConnectionType.fromString(connectionType);
            ConnectionManager connectionManager = connectionManagerFactory.getConnectionManager(enumValueOfType);
            executorService.submit(() -> {
                try {
                    manageFiles(connectionManager);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception exception) {
            LOGGER.info("Error while importing files. " + exception);
            return "Exception: " + exception.getMessage();
        }
        return "Import started...";
    }

    private void manageFiles(ConnectionManager connectionManager) throws Exception {
        try {
            connectionManager.connect();
            List<String> files = connectionManager.getFilesForDownload();
            connectionManager.download(files);
            fileProcessor.processFiles(files);
            LOGGER.info("Import finished.");
        } catch (Exception e) {
            throw e;
        } finally {
            connectionManager.disconnect();
        }
    }
}
