package com.company.connectionmanagerimpl;

import com.company.connectionmanager.ConnectionManager;
import com.company.ftpfilefilterimpl.FTPFileFilterImpl;
import com.company.model.ConnectionData;
import com.company.repository.FileRepository;
import com.company.util.FileNameParser;
import com.company.util.Properties;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger("timeBased");

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
        logger.info("Connected to server: " + connectionData.getHost());
        checkReplyCode();
        login(connectionData.getUsername(), connectionData.getPassword());
    }

    @Override
    public List<String> download() throws Exception {
        List<String> fileNames = new ArrayList<>();
        prepareFtpClient();

        FTPFile[] files = getFtpFiles();
        logger.info(String.valueOf(files.length) + " files for download");
        for (FTPFile ftpFile : files) {
            String ftpFileName = ftpFile.getName();
            Path path = Paths.get(connectionData.getPath() + ftpFileName);
            File file = new File(Properties.getProperty("file.location") + ftpFileName);
            try (OutputStream outputStream = new FileOutputStream(file)) {
                Boolean success = ftpClient.retrieveFile(path.toString(), outputStream);
                if (!success) {
                    logger.info(ftpFileName + " cannot be retrieved from server.");
                    throw new IOException("File retrieving failed!");
                }
                fileNames.add(ftpFileName);
                logger.info("File " + ftpFileName + " retrieved.");
            }
        }
        return fileNames;
    }

    @Override
    public void disconnect() throws IOException {
        if (ftpClient.isConnected()) {
            ftpClient.disconnect();
            logger.info("Disconnected from server " + connectionData.getHost());
        }
    }

    private void checkReplyCode() throws Exception {
        int replyCode = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(replyCode)) {
            throw new ConnectException("Server refused connection. Reply code: " + String.valueOf(replyCode));
        }
        logger.info("Reply code: " + replyCode);
    }

    private void login(String username, String password) throws Exception {
        Boolean loginSuccessful = ftpClient.login(username, password);
        if (!loginSuccessful) {
            logger.info("Login with credentials " + username + ", " + password + " failed!");
            throw new LoginException("Login failed!");
        }
        logger.info("Login successful!");
    }

    private void prepareFtpClient() throws IOException {
        logger.info("Preparing FTP client for file download...");
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
    }

    private FTPFile[] getFtpFiles() throws IOException {
        return ftpClient.listFiles(connectionData.getPath(), new FTPFileFilterImpl(fileRepository, fileNameParser));
    }
}
