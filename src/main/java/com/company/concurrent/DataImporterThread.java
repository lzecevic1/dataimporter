package com.company.concurrent;

import com.company.model.PortedNumber;
import com.company.repository.PortedNumberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class DataImporterThread implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger("timeBased");

    @Autowired
    private PortedNumberRepository portedNumberRepository;

    private LinkedBlockingQueue<PortedNumber> portedNumbers;
    private AtomicBoolean fileRead;

    public DataImporterThread(LinkedBlockingQueue<PortedNumber> portedNumbers,
                              PortedNumberRepository portedNumberRepository,
                              AtomicBoolean fileRead) {
        this.portedNumbers = portedNumbers;
        this.portedNumberRepository = portedNumberRepository;
        this.fileRead = fileRead;
    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                while (!fileRead.get() || portedNumbers.size() > 0) {
                    portedNumberRepository.save(portedNumbers.take());
                }
            }
        } catch (InterruptedException e) {
            LOGGER.info("Interrupted while saving numbers to db!");
            e.printStackTrace();
        }
    }
}
