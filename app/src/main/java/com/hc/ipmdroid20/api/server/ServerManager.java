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
    private ArrayList<Server> currentServers;
    private ArrayList<Machine> currentServerMachines;

    public Notifier notifier;

    private ServerManager() {
        servers = new HashMap<>();
        TaskManager.Instance().addPersistentTask(o -> {
            for (Server server: servers.values()) {
                server.executeCallbacks();
            }

            updateCurrentServerMachines();
            notifier.executeCallbacks();
            return null;
        });

        currentServerMachines = new ArrayList<>();
        currentServers = new ArrayList<>();
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

    public void addServer(Server server) {
        this.servers.put(server.id, server);
        this.saveServers();
    }

    public void updateServer(String uuid, String displayName, String hostname, String port) {
        Server current = this.servers.get(uuid);
        current.displayName = displayName;
        current.hostname = hostname;
        current.port = port;

        this.saveServers();
    }

    public void deleteServer(String uuid) {
        this.servers.remove(uuid);
    }

    public void restoreServers() {
        Credentials.restoreServers();
    }

    public void setCurrentServer(Server server) {
        currentServer = server;

        // Execute callbacks.
        server.executeCallbacks();
        notifier.executeCallbacks();
    }

    public void updateCurrentServerMachines() {
        if (hasCurrentServer()) {
            currentServerMachines.clear();
            currentServerMachines.addAll(currentServer.getMachines());
        }
    }

    public Server getCurrentServer() {
        return currentServer;
    }

    public boolean hasCurrentServer() {
        return currentServer != null;
    }

    public ArrayList<Machine> getCurrentServerMachines() {
        return currentServerMachines;
    }

    public ArrayList<Server> getServers() {
        currentServers.clear();
        currentServers.addAll(servers.values());
        return currentServers;
    }
}
