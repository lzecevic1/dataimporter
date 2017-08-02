package com.company.connectionmanager.impl;

import com.company.util.FileFilter;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.springframework.beans.factory.annotation.Autowired;

public class FTPFileFilterImpl implements FTPFileFilter {

    @Autowired
    private FileFilter fileFilter;

    @Override
    public boolean accept(FTPFile ftpFile) {
        return fileFilter.acceptFile(ftpFile.getName());
    }
}