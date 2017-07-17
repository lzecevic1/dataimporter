package com.company.repository;

import com.company.model.ImportFile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FileRepository extends CrudRepository<ImportFile, Integer> {
    Optional<ImportFile> findFirstByOrderByDateOfImportDesc();

    Optional<ImportFile> findByFileName(String fileName);
}
