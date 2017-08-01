package com.company.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class ConnectionData {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Setter(AccessLevel.PROTECTED)
    private Integer id;

    private String host;
    private Integer port;
    private String username;
    private String password;
    private String path;

    @Enumerated(EnumType.STRING)
    private ConnectionType type;

    public ConnectionData(String host, Integer port, String username, String password, String path, ConnectionType type) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.path = path;
        this.type = type;
    }
}
