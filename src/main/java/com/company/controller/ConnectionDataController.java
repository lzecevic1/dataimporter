package com.company.controller;

import com.company.model.ConnectionData;
import com.company.repository.ConnectionDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/connectionData")
public class ConnectionDataController {

    @Autowired
    private ConnectionDataRepository connectionDataRepository;

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public void insertData(@RequestBody ConnectionData data) {
        connectionDataRepository.save(data);
    }
}
