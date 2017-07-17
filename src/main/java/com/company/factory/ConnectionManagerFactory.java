package com.company.factory;

import com.company.connectionmanager.ConnectionManager;
import com.company.connectionmanager.FtpConnectionManager;
import com.company.enums.ConnectionType;
import com.company.model.ConnectionData;
import com.company.repository.ConnectionDataRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ConnectionManagerFactory {

    @Autowired
    private ConnectionDataRepository connectionDataRepository;

    public Optional<ConnectionManager> get(ConnectionType type) throws Exception {
        Optional<ConnectionData> connectionData = connectionDataRepository.findByType(type);
        if (type.equals(ConnectionType.FTP)) {
            return Optional.of(new FtpConnectionManager(connectionData.orElseThrow(Exception::new)));
        }
        return Optional.empty();
    }
}