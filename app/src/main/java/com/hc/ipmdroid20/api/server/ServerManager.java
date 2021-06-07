package com.hc.ipmdroid20.api.server;

import com.hc.ipmdroid20.api.background.Notifier;
import com.hc.ipmdroid20.api.background.TaskManager;
import com.hc.ipmdroid20.api.models.Machine;
import com.hc.ipmdroid20.api.models.Server;
import com.hc.ipmdroid20.ui.MachinesFragment;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerManager {
    private static ServerManager manager;
    private HashMap<String, Server> servers;
    private Server currentServer;

    public Notifier notifier;

    private ServerManager() {
        servers = new HashMap<>();
        TaskManager.Instance().addPersistentTask(o -> {
            for (Server server: servers.values()) {
                server.executeCallbacks();
            }

            return null;
        });

        notifier = new Notifier();
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
                Server loadedServer = new Server(
                    server.hostname, server.port, server.displayName, server.id
                );
                this.servers.put(server.id, loadedServer);
                loadedServer.executeCallbacks();
            }
        }

        notifier.executeCallbacks();
    }

    public void saveServers() {
        Credentials.saveServers(new ArrayList<>(this.servers.values()));
    }

    public void restoreServers() {
        Credentials.restoreServers();
    }

    public void setCurrentServer(Server server) {
        currentServer = server;
        server.executeCallbacks();
        notifier.executeCallbacks();
    }

    public Server getCurrentServer() {
        return currentServer;
    }

    public ArrayList<Machine> getCurrentServerMachines() {
        if (currentServer == null) {
            return new ArrayList<>();
        }

        return currentServer.getMachines();
    }

    public ArrayList<Server> getServers() {
        return new ArrayList<>(servers.values());
    }
}
