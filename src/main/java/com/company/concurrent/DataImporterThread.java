package com.company.concurrent;

import com.company.model.PortedNumber;
import com.company.repository.PortedNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.LinkedBlockingQueue;

public class DataImporterThread implements Runnable {

    @Autowired
    private PortedNumberRepository numberRepository;

    private LinkedBlockingQueue<String> portedNumbers;

    public DataImporterThread(LinkedBlockingQueue<String> portedNumbers) {
        this.portedNumbers = portedNumbers;
    }

    @Override
    public void run() {
        try {
            numberRepository.save(new PortedNumber(portedNumbers.take()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
