package com.hc.ipmdroid20.api.server;

import com.hc.ipmdroid20.api.models.Server;

import java.util.HashMap;
import java.util.UUID;

public class ServerManager {
    private static ServerManager manager;
    private HashMap<UUID, Server> servers;
    private Server currentServer;

    private ServerManager() {
        servers = new HashMap<>();
    }

    public static ServerManager Instance() {
        if (manager == null) {
            manager = new ServerManager();
        }

        return manager;
    }

    public void loadServers() {

    }

    public void setCurrentServer(String uuid) {
        if (uuid != null && uuid.equals("")) {
            currentServer = servers.get(UUID.fromString(uuid));
        }
    }

    public Server getCurrentServer() {
        return currentServer;
    }
}
