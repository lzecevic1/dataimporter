package com.company.concurrent;

import com.company.model.PortedNumber;
import com.company.repository.PortedNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.LinkedBlockingQueue;

public class DataImporterThread implements Runnable {

    @Autowired
    private PortedNumberRepository portedNumberRepository;

    private LinkedBlockingQueue<String> portedNumbers;

    private String threadName = "thread";
    int index=0;
    public DataImporterThread(LinkedBlockingQueue<String> portedNumbers,
                              PortedNumberRepository portedNumberRepository,
                              int index) {
        this.portedNumbers = portedNumbers;
        this.portedNumberRepository = portedNumberRepository;
        this.index = index;
    }

    @Override
    public void run() {
        try {
            System.out.println("Thread name: " + threadName + index);
            while (portedNumbers.size() > 0) {
                portedNumberRepository.save(new PortedNumber(portedNumbers.take()));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
