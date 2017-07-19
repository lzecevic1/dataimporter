package com.company.impl;

import com.company.repository.FileRepository;
import com.company.util.FileFilter;
import com.company.util.FileNameParser;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.springframework.stereotype.Service;

@Service
public class FTPFileFilterImpl implements FTPFileFilter {
    private FileFilter fileFilter;

    public FTPFileFilterImpl(FileRepository fileRepository, FileNameParser fileNameParser) {
        fileFilter = new FileFilter(fileRepository, fileNameParser);
    }

    @Override
    public boolean accept(FTPFile ftpFile) {
        return fileFilter.acceptFile(ftpFile.getName());
    }
}