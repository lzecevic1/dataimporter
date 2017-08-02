package com.company.factory;

import com.company.connectionmanager.ConnectionManager;
import com.company.connectionmanager.impl.FTPFileFilterImpl;
import com.company.connectionmanager.impl.FtpConnectionManager;
import com.company.connectionmanager.impl.SftpConnectionManager;
import com.company.model.ConnectionType;
import com.company.model.ConnectionData;
import com.company.repository.ConnectionDataRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ConnectionManagerFactory {

    @Autowired
    private ConnectionDataRepository connectionDataRepository;
    @Autowired
    private FTPFileFilterImpl ftpFileFilter;

    public ConnectionManager getConnectionManager(ConnectionType type) throws Exception {
        Optional<ConnectionData> connectionDataOptional = connectionDataRepository.findByType(type);
        ConnectionData connectionData = connectionDataOptional.orElseThrow(Exception::new);
        if (type.equals(ConnectionType.FTP)) {
            return new FtpConnectionManager(connectionData, ftpFileFilter);
        } else if (type.equals(ConnectionType.SFTP)) {
            return new SftpConnectionManager(connectionData);
        }
        throw new TypeNotPresentException("ConnectionType", null);
    }
}