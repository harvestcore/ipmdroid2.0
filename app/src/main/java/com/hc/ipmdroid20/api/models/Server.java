package com.hc.ipmdroid20.api.models;

public class Server {
    public String hostname;
    public String port;
    public String displayName;
    public String id;

    public Server(String hostname, String port, String displayName, String id) {
        this.hostname = hostname;
        this.port = port;
        this.displayName = displayName;
        this.id = id;
    }
}
