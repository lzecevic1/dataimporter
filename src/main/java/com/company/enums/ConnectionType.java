package com.company.enums;

import java.util.HashMap;
import java.util.Map;

public enum ConnectionType {
    FTP(1, "FTP"),
    SFTP(2, "SFTP");

    private int value;
    private String connectionType;

    ConnectionType(int value, String connectionType) {
        this.value = value;
        this.connectionType = connectionType;
    }

    public int getValue() {
        return value;
    }

    public String getConnectionType() {
        return connectionType;
    }

    private static final Map<Integer, ConnectionType> integerToEnum = new HashMap<>();

    static { // Initialize map from constant name to enum constant
        for (ConnectionType element : values())
            integerToEnum.put(element.getValue(), element);
    }

    public static ConnectionType fromValue(Integer value) {
        return integerToEnum.get(value);
    }
}