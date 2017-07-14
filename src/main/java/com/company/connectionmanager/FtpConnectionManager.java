package com.company.connectionmanager;

import com.company.model.ConnectionData;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FtpConnectionManager implements ConnectionManager {
    private static String FILE_PATH = "/opt/importer/cache/files/";
    private FTPClient ftpClient;
    private ConnectionData connectionData;

    public FtpConnectionManager(ConnectionData connectionData) {
        this.ftpClient = new FTPClient();
        this.connectionData = connectionData;
    }

    @Override
    public FTPClient connect() throws Exception {
        ftpClient.connect(connectionData.getHost(), connectionData.getPort());
        checkReplyCode(ftpClient);
        login(connectionData, ftpClient);

        return ftpClient;
    }

    @Override
    public void download(FTPFile[] files) throws Exception {
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

        for (FTPFile ftpFile : files) {
            Path path = Paths.get(ftpClient.printWorkingDirectory() + ftpFile.getName());
            File file = new File(FILE_PATH + ftpFile.getName());
            try (OutputStream outputStream = new FileOutputStream(file)) {
                Boolean success = ftpClient.retrieveFile(path.toString(), outputStream);
                if (!success) {
                    throw new IOException("File retrieving failed!");
                }
            }
        }
    }

    private void login(ConnectionData connectionData, FTPClient ftp) throws Exception {
        Boolean loginSuccessful = ftp.login(connectionData.getUsername(), connectionData.getPassword());
        if (!loginSuccessful) {
            throw new LoginException("Login failed!");
        }
    }

    private void checkReplyCode(FTPClient ftp) throws Exception {
        int replyCode = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(replyCode)) {
            throw new ConnectException("Server refused connection. Reply code: " + String.valueOf(replyCode));
        }
    }
}
