package com.company.util;

import com.company.model.ImportFile;
import com.company.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Optional;

public class FileFilter {
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private FileNameParser fileNameParser;

    public boolean acceptFile(String nameOfFile) {
        Calendar importDateOfFile = fileNameParser.getTimestampFromFileName(nameOfFile);

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
}
