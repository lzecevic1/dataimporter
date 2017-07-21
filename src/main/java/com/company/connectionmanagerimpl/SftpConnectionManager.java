package com.company.connectionmanagerimpl;

import com.company.connectionmanager.ConnectionManager;
import com.company.model.ConnectionData;
import com.company.repository.FileRepository;
import com.company.util.FileFilter;
import com.company.util.FileNameParser;
import com.company.util.Properties;
import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static com.jcraft.jsch.ChannelSftp.SSH_FX_FAILURE;

public class SftpConnectionManager implements ConnectionManager {
    private static final Logger logger = LoggerFactory.getLogger(SftpConnectionManager.class);

    private ConnectionData connectionData;
    private JSch jsch;
    private Session session;
    private ChannelSftp channelSftp;
    private FileFilter fileFilter;

    public SftpConnectionManager(ConnectionData connectionData, FileRepository fileRepository, FileNameParser fileNameParser) {
        this.jsch = new JSch();
        this.connectionData = connectionData;
        fileFilter = new FileFilter(fileRepository, fileNameParser);
    }

    @Override
    public void connect() throws JSchException {
        try {
            session = jsch.getSession(connectionData.getUsername(), connectionData.getHost(), connectionData.getPort());
            jsch.setKnownHosts("~/.ssh/known_hosts");
            session.setPassword(connectionData.getPassword());
            session.connect();
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            logger.info("Connected to server: " + connectionData.getHost());
        } catch (JSchException exception) {
            logger.info("Connecting to server " + connectionData.getHost() + " failed...");
            throw new JSchException("Cannot instantiate Session object or cannot open SFTP channel!");
        }
    }

    @Override
    public List<String> download() throws Exception {
        List<String> filesToDownload = new ArrayList<>();

        Vector allFiles = channelSftp.ls(connectionData.getPath() + "PortedNumbers*");
        for (Object file : allFiles) {
            String nameOfFile = ((ChannelSftp.LsEntry) file).getFilename();
            if (fileShouldBeProcessed(nameOfFile)) {
                try {
                    downloadFile(nameOfFile);
                    filesToDownload.add(nameOfFile);
                    logger.info("File " + nameOfFile + " retrieved.");
                } catch (SftpException exception) {
                    logger.info("Error ocurred while retrieving file: " + exception);
                    throw new SftpException(SSH_FX_FAILURE, "File retrieving failed!");
                } catch (IOException exception) {
                    logger.info("Error ocurred while creating output stream: " + exception);
                    throw new IOException("Cannot create output stream with given path!");
                }
            }
        }
        return filesToDownload;
    }

    @Override
    public void disconnect() throws IOException {
        channelSftp.disconnect();
        session.disconnect();
        logger.info("Disconnected from server " + connectionData.getHost());
    }

    private boolean fileShouldBeProcessed(String nameOfFile) {
        return fileFilter.acceptFile(nameOfFile);
    }

    private void downloadFile(String nameOfFile) throws IOException, SftpException {
        Path path = Paths.get(connectionData.getPath() + nameOfFile);
        File file = new File(Properties.getProperty("file.location") + nameOfFile);
        try (OutputStream outputStream = new FileOutputStream(file)) {
            channelSftp.get(path.toString(), outputStream);
        }
    }
}
