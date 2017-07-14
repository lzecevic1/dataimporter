package com.company.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum ConnectionType {
    FTP("FTP"),
    FTPS("FTPS");

    private final String connectionType;

    ConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    @JsonProperty("connectionType")
    public String getConnectionType() {
        return connectionType;
    }

    public static Optional<ConnectionType> fromType(final String connectionType) {
        if(connectionType != null) {
            for(ConnectionType type : ConnectionType.values()) {
                if(connectionType.equals(type.connectionType)) {
                    return Optional.of(type);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return connectionType;
    }
}