package com.company.util;

import java.util.Calendar;

public class FileNameParser {
    public Calendar getTimestampFromFileName(String fileName) {
        String[] splitedNameOfFile = fileName.split("_");
        return getDate(splitedNameOfFile);
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
