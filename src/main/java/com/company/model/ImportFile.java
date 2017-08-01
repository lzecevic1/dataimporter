package com.company.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Calendar;

@Entity
@Data
@NoArgsConstructor
public class ImportFile {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Setter(AccessLevel.PROTECTED)
    private Integer id;

    private String fileName;
    private Calendar dateOfImport;

    public ImportFile(String fileName, Calendar dateOfImport) {
        this.fileName = fileName;
        this.dateOfImport = dateOfImport;
    }
}
