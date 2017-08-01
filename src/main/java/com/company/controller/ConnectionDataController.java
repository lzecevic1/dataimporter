package com.company.controller;

import com.company.model.ConnectionData;
import com.company.repository.ConnectionDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/connectionData")
public class ConnectionDataController {
    private static final Logger LOGGER = LoggerFactory.getLogger("timeBased");

    @Autowired
    private ConnectionDataRepository connectionDataRepository;

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public void insertData(@RequestBody ConnectionData data) {
        try {
            connectionDataRepository.save(data);
            LOGGER.info("Data saved successfully");
        } catch (IllegalArgumentException exception) {
            LOGGER.info("Error occured while saving given data..." + exception);
            throw new IllegalArgumentException("Given entity is null!");
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public List<ConnectionData> getAllData(@RequestBody ConnectionData data) {
        return (List<ConnectionData>) connectionDataRepository.findAll();
    }
}
