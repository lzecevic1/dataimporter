package com.company.connectionmanager;

import com.company.model.ConnectionData;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.util.List;

public class SftpConnectionManager implements ConnectionManager {
    private JSch jsch;
    private ConnectionData connectionData;

    public SftpConnectionManager(ConnectionData connectionData) {
        this.connectionData = connectionData;
    }

    @Override
    public void connect() throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(connectionData.getUsername(), connectionData.getHost());
    }

    @Override
    public List<String> download() throws Exception {
        return null;
    }

}
