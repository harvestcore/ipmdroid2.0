package com.hc.ipmdroid20.api.server;

import com.hc.ipmdroid20.api.background.Notifier;
import com.hc.ipmdroid20.api.background.TaskManager;
import com.hc.ipmdroid20.api.models.Machine;
import com.hc.ipmdroid20.api.models.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class ServerManager {
    private static ServerManager manager;
    private HashMap<String, Server> servers;
    private Server currentServer;
    private ArrayList<Server> currentServers;
    private ArrayList<Machine> currentServerMachines;
    ArrayList<Function> currentServerRemoveCallbacks = new ArrayList<>();

    public Notifier notifier;

    private ServerManager() {
        servers = new HashMap<>();
        TaskManager.Instance().addPersistentTask(o -> {
            for (Server server: servers.values()) {
                server.executeCallbacks();
            }

            notifier.executeCallbacks();
            return null;
        });

        currentServerMachines = new ArrayList<>();
        currentServers = new ArrayList<>();
        notifier = new Notifier();

        notifier.addCallback(o -> {
            updateCurrentServerList();
            return null;
        });

        notifier.addCallback(o -> {
            updateCurrentServerMachines();
            return null;
        });
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

        EventManager.Instance().addEvent("Servers have been loaded.");
        notifier.executeCallbacks();
    }

    public void saveServers() {
        Credentials.saveServers(new ArrayList<>(this.servers.values()));
    }

    public void addServer(Server server) {
        server.executeCallbacks();
        this.servers.put(server.id, server);
        this.saveServers();
        this.notifier.executeCallbacks();

        EventManager.Instance().addEvent("Server [" + server.displayName + "] has been added.");
    }

    public void updateServer(String uuid, String hostname, String port, String displayName) {
        Server current = this.servers.get(uuid);
        current.displayName = displayName;
        current.hostname = hostname;
        current.port = port;

        this.saveServers();
        this.notifier.executeCallbacks();

        EventManager.Instance().addEvent("Server [" + displayName + "] has been updated.");
    }

    public void deleteServer(String uuid) {
        Server server = this.servers.get(uuid);
        this.servers.remove(uuid);
        if (currentServer != null && currentServer.id.equals(uuid)) {
            currentServer = null;
        }

        this.saveServers();
        this.notifier.executeCallbacks();

        EventManager.Instance().addEvent("Server [" + server.displayName + "] has been updated.");
    }

    public void restoreServers() {
        Credentials.restoreServers();
    }

    public void setCurrentServer(Server server) {
        currentServer = server;
        for (Function f: currentServerRemoveCallbacks) {
            f.apply(null);
        }

        currentServerRemoveCallbacks.clear();
        currentServerRemoveCallbacks.add(currentServer.notifier.addCallback(o -> {
           currentServer.queryMachine();
           return null;
        }));

        currentServerRemoveCallbacks.add(currentServer.notifier.addCallback(o -> {
           updateCurrentServerMachines();
           return null;
        }));

        // Execute callbacks.
        currentServer.executeCallbacks();
        notifier.executeCallbacks();
    }

    public void updateCurrentServerMachines() {
        currentServerMachines.clear();
        if (hasCurrentServer()) {
            currentServerMachines.addAll(currentServer.getMachines());
        }
    }

    public void updateCurrentServerList() {
        currentServers.clear();
        currentServers.addAll(servers.values());
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
        return currentServers;
    }
}
