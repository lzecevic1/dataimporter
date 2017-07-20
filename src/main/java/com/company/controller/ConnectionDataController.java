package com.company.controller;

import com.company.model.ConnectionData;
import com.company.repository.ConnectionDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/connectionData")
public class ConnectionDataController {

    @Autowired
    private ConnectionDataRepository connectionDataRepository;

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public void insertData(@RequestBody ConnectionData data) {
        try {
            connectionDataRepository.save(data);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Given entity is null!");
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public List<ConnectionData> getAllData(@RequestBody ConnectionData data) {
        return (List<ConnectionData>) connectionDataRepository.findAll();
    }
}
