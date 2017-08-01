package com.company.concurrent;

import com.company.model.PortedNumber;
import com.company.repository.PortedNumberRepository;
import com.company.service.FileProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.LinkedBlockingQueue;

public class DataImporterThread implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataImporterThread .class);

    @Autowired
    private PortedNumberRepository portedNumberRepository;

    private LinkedBlockingQueue<PortedNumber> portedNumbers;

    public DataImporterThread(LinkedBlockingQueue<PortedNumber> portedNumbers,
                              PortedNumberRepository portedNumberRepository) {
        this.portedNumbers = portedNumbers;
        this.portedNumberRepository = portedNumberRepository;
    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                while (portedNumbers.size() > 0) {
                    portedNumberRepository.save(portedNumbers.take());
                }
            }
        } catch (InterruptedException e) {
            LOGGER.info("Interrupted while saving numbers to db!");
            e.printStackTrace();
        }
    }
}
