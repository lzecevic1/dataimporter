package com.company.impl;

import com.company.model.ImportFile;
import com.company.repository.FileRepository;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
public class FTPFileFilterImpl implements FTPFileFilter {

    @Autowired
    private FileRepository fileRepository;

    public FTPFileFilterImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public boolean accept(FTPFile ftpFile) {
        String nameOfFile = ftpFile.getName();
        String[] splitedNameOfFile = nameOfFile.split("_");
        Calendar importDateOfFile = getDate(splitedNameOfFile);

        Optional<ImportFile> lastImportedFile = fileRepository.findFirstByOrderByDateOfImportDesc();
        if (!lastImportedFile.isPresent()) return true;
        if (isImportedFile(nameOfFile)) return false;

        Calendar dateOfLastImport = lastImportedFile.get().getDateOfImport();
        return importDateOfFile.compareTo(dateOfLastImport) > 0;
    }

    private boolean isImportedFile(String nameOfFile) {
        Optional<ImportFile> file = fileRepository.findByFileName(nameOfFile);
        return file.isPresent();
    }

    private Calendar getDate(String[] splitedNameOfFile) {
        Integer year = Integer.valueOf(splitedNameOfFile[1]);
        Integer month = Integer.valueOf(splitedNameOfFile[2]);
        Integer day = Integer.valueOf(splitedNameOfFile[3]);
        Calendar date = Calendar.getInstance();
        date.set(year, month, day);
        return date;
    }
}