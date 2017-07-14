package com.company.model;

import com.company.enums.ConnectionType;

import javax.persistence.*;

@Entity
public class ConnectionData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String host;
    private Integer port;
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private ConnectionType type;

    public ConnectionData() {
    }

    public ConnectionData(String host, Integer port, String username, String password, ConnectionType type) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    @JsonProperty("connectionType")
    public ConnectionType getType() {
        return type;
    }

//    @JsonProperty("connectionType")
//    @JsonDeserialize(using = ConnectionTypeDeserializer.class)
    public void setType(ConnectionType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConnectionData that = (ConnectionData) o;

        if (host != null ? !host.equals(that.host) : that.host != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        return password != null ? password.equals(that.password) : that.password == null;
    }

    @Override
    public int hashCode() {
        int result = host != null ? host.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ConnectionData{" +
                "host='" + host + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
