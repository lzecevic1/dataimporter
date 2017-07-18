package com.company.factory;

import com.company.connectionmanager.ConnectionManager;
import com.company.connectionmanager.FtpConnectionManager;
import com.company.connectionmanager.SftpConnectionManager;
import com.company.enums.ConnectionType;
import com.company.model.ConnectionData;
import com.company.repository.ConnectionDataRepository;
import com.company.repository.FileRepository;
import com.company.util.FileNameParser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ConnectionManagerFactory {

    @Autowired
    private ConnectionDataRepository connectionDataRepository;
    @Autowired
    private FileRepository fileRepository;

    public ConnectionManager getConnectionManager(ConnectionType type) throws Exception {
        Optional<ConnectionData> connectionDataOptional = connectionDataRepository.findByType(type);
        ConnectionData connectionData = connectionDataOptional.orElseThrow(Exception::new);

        if (type.equals(ConnectionType.FTP)) {
            return new FtpConnectionManager(connectionData, fileRepository, new FileNameParser());
        } else if (type.equals(ConnectionType.SFTP)) {
            return new SftpConnectionManager(connectionData);
        }
        throw new TypeNotPresentException("ConnectionType", null);
    }
}