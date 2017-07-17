package com.company.concurrent;

import com.company.model.PortedNumber;
import com.company.repository.PortedNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.LinkedBlockingQueue;

public class DataImporterThread implements Runnable {

    @Autowired
    private PortedNumberRepository portedNumberRepository;

    private LinkedBlockingQueue<String> portedNumbers;

    public DataImporterThread(LinkedBlockingQueue<String> portedNumbers, PortedNumberRepository portedNumberRepository) {
        this.portedNumbers = portedNumbers;
        this.portedNumberRepository = portedNumberRepository;
    }

    @Override
    public void run() {
        try {
            while (portedNumbers.size() > 0) {
                portedNumberRepository.save(new PortedNumber(portedNumbers.take()));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
