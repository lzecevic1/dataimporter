package com.company.connectionmanager;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public interface ConnectionManager {
    FTPClient connect() throws Exception;

    void download(FTPFile[] files) throws Exception;
}
