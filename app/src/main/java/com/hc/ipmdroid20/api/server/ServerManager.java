package com.hc.ipmdroid20.api.server;

import com.hc.ipmdroid20.api.models.Server;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerManager {
    private static ServerManager manager;
    private HashMap<String, Server> servers;
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

    public void loadServers(ArrayList<Server> servers) {
        this.servers.clear();
        for (Server server: servers) {
            if (!this.servers.containsKey(server.id)) {
                this.servers.put(server.id, server);
                server.executeCallbacks();
            }
        }
    }

    public void saveServers() {
        Credentials.saveServers(new ArrayList<>(this.servers.values()));
    }

    public void restoreServers() {
        Credentials.restoreServers();
    }

    public void setCurrentServer(String uuid) {
        if (uuid != null && uuid.equals("")) {
            currentServer = servers.get(uuid);
        }
    }

    public Server getCurrentServer() {
        return currentServer;
    }
}
