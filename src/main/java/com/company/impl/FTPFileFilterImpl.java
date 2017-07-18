package com.company.impl;

import com.company.model.ImportFile;
import com.company.repository.FileRepository;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.company.util.FileNameParser;

import java.util.Calendar;
import java.util.Optional;

@Service
public class FTPFileFilterImpl implements FTPFileFilter {

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private FileNameParser fileNameParser;

    public FTPFileFilterImpl(FileRepository fileRepository, FileNameParser fileNameParser) {
        this.fileRepository = fileRepository;
        this.fileNameParser = fileNameParser;
    }

    @Override
    public boolean accept(FTPFile ftpFile) {
        String nameOfFtpFile = ftpFile.getName();
        Calendar importDateOfFile = fileNameParser.getTimestampFromFileName(nameOfFtpFile);

        Optional<ImportFile> lastImportedFile = fileRepository.findFirstByOrderByDateOfImportDesc();
        if (!lastImportedFile.isPresent()) return true;
        if (isImportedFile(nameOfFtpFile)) return false;

        Calendar dateOfLastImport = lastImportedFile.get().getDateOfImport();
        return importDateOfFile.compareTo(dateOfLastImport) > 0;
    }

    private boolean isImportedFile(String nameOfFile) {
        Optional<ImportFile> file = fileRepository.findByFileName(nameOfFile);
        return file.isPresent();
    }
}