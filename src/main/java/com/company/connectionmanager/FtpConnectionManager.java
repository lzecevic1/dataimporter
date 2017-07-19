package com.company.connectionmanager;

import com.company.impl.FTPFileFilterImpl;
import com.company.model.ConnectionData;
import com.company.repository.FileRepository;
import com.company.util.FileNameParser;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FtpConnectionManager implements ConnectionManager {
    private static String HOST_PATH = "/home/lzecevic/Desktop/importer/";
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private FileNameParser fileNameParser;
    private FTPClient ftpClient;
    private ConnectionData connectionData;

    public FtpConnectionManager(ConnectionData connectionData, FileRepository fileRepository, FileNameParser fileNameParser) {
        this.ftpClient = new FTPClient();
        this.connectionData = connectionData;
        this.fileRepository = fileRepository;
        this.fileNameParser = fileNameParser;
    }

    @Override
    public void connect() throws Exception {
        ftpClient.connect(connectionData.getHost(), connectionData.getPort());
        checkReplyCode(ftpClient);
        login(connectionData, ftpClient);
    }

    @Override
    public List<String> download() throws Exception {
        List<String> fileNames = new ArrayList<>();
        prepareFtpClient();

        FTPFile[] files = getFtpFiles();
        for (FTPFile ftpFile : files) {
            String ftpFileName = ftpFile.getName();
            Path path = Paths.get(connectionData.getPath() + ftpFileName);
            File file = new File(System.getProperty("file.location") + ftpFileName);
            try (OutputStream outputStream = new FileOutputStream(file)) {
                Boolean success = ftpClient.retrieveFile(path.toString(), outputStream);
                if (!success) {
                    throw new IOException("File retrieving failed!");
                }
                fileNames.add(ftpFileName);
            }
        }
        return fileNames;
    }

    @Override
    public void disconnect() throws IOException {
        if(ftpClient.isConnected()) {
            ftpClient.disconnect();
        }
    }

    private void prepareFtpClient() throws IOException {
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
    }

    private FTPFile[] getFtpFiles() throws IOException {
        return ftpClient.listFiles(connectionData.getPath(), new FTPFileFilterImpl(fileRepository, fileNameParser));
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
