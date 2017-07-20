package com.company.connectionmanager;

import java.io.IOException;
import java.util.List;

public interface ConnectionManager {
    void connect() throws Exception;

    List<String> download() throws Exception;

    void disconnect() throws IOException;
}
