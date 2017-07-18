package com.company.connectionmanager;

import com.company.model.ConnectionData;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.IOException;
import java.util.List;

public class SftpConnectionManager implements ConnectionManager {
    ChannelSftp channelSftp;
    private JSch jsch;
    private ConnectionData connectionData;
    private Session session;

    public SftpConnectionManager(ConnectionData connectionData) {
        this.jsch = new JSch();
        this.connectionData = connectionData;
    }

    @Override
    public void connect() throws JSchException {
        try {
            getSession();
            openChannel();
        } catch (JSchException exception) {
            System.out.println("Connecting to SFTP server failed!");
            throw exception;
        }
    }

    private void getSession() throws JSchException {
        session = jsch.getSession(connectionData.getUsername(), connectionData.getHost(), connectionData.getPort());
        session.setPassword(connectionData.getPassword());
        session.connect();
    }

    private void openChannel() throws JSchException {
        channelSftp = (ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();
    }

    @Override
    public List<String> download() throws Exception {
        return null;
    }

    @Override
    public void disconnect() throws IOException {
        session.disconnect();
    }
}
