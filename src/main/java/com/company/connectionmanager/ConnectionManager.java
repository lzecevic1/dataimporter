package com.company.connectionmanager;

import com.jcraft.jsch.SftpException;

import java.io.IOException;
import java.util.List;

public interface ConnectionManager {
    void connect() throws Exception;

    List<String> getFilesForDownload() throws IOException, SftpException;

    void download(List<String> filesForDownload) throws Exception;

    void disconnect() throws IOException;

}
