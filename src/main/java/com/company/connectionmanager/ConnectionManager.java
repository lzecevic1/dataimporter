package com.company.connectionmanager;

import java.util.List;

public interface ConnectionManager {
    void connect() throws Exception;

    List<String> download()throws Exception;
}
