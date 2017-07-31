package com.company.enums;

public enum ConnectionType {
    FTP("FTP"),
    SFTP("SFTP");

    private String connectionType;

    ConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public static ConnectionType fromString(String connectionType) {
        for (ConnectionType element : values()) {
            if (element.getConnectionType().equals(connectionType)) {
                return element;
            }
        }
        throw new TypeNotPresentException(connectionType, null);
    }
}