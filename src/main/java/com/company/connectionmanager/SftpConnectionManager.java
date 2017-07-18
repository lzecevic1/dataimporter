package com.company.connectionmanager;

import com.company.model.ConnectionData;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.util.List;

public class SftpConnectionManager implements ConnectionManager {
    private JSch jsch;
    private ConnectionData connectionData;

    public SftpConnectionManager(ConnectionData connectionData) {
        this.jsch = new JSch();
        this.connectionData = connectionData;
    }

    @Override
    public void connect() throws Exception {
        Session session = jsch.getSession(connectionData.getUsername(), connectionData.getHost(), connectionData.getPort());
        session.setPassword(connectionData.getPassword());
        session.connect();


        ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
    }

    @Override
    public List<String> download() throws Exception {
        return null;
    }

}
