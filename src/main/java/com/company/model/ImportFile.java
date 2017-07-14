package com.company.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Calendar;

@Entity
public class ImportFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String fileName;
    private Calendar dateOfImport;

    public ImportFile(String fileName, Calendar dateOfImport) {
        this.fileName = fileName;
        this.dateOfImport = dateOfImport;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Calendar getDateOfImport() {
        return dateOfImport;
    }

    public void setDateOfImport(Calendar dateOfImport) {
        this.dateOfImport = dateOfImport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImportFile that = (ImportFile) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) return false;
        return dateOfImport != null ? dateOfImport.equals(that.dateOfImport) : that.dateOfImport == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (dateOfImport != null ? dateOfImport.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ImportFile{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", dateOfImport=" + dateOfImport +
                '}';
    }
}
