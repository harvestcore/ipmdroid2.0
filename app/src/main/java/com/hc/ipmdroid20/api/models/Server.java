package com.hc.ipmdroid20.api.models;

import com.hc.ipmdroid20.api.background.Notifier;
import com.hc.ipmdroid20.api.connector.IConnector;
import com.hc.ipmdroid20.api.models.api.ComplexResponse;
import com.hc.ipmdroid20.api.models.api.RemoveElement;
import com.hc.ipmdroid20.api.models.api.SimpleResponse;
import com.hc.ipmdroid20.api.models.api.UpdateElement;
import com.hc.ipmdroid20.api.models.status.Health;
import com.hc.ipmdroid20.api.models.status.Service;
import com.hc.ipmdroid20.api.models.status.Status;
import com.hc.ipmdroid20.api.server.EventManager;
import com.hc.ipmdroid20.api.server.ServerManager;

import java.util.ArrayList;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Server class.
 */
public class Server {
    // Server static data.
    public String hostname;
    public String port;
    public String displayName;
    public String id;

    // Server dynamic data.
    private transient ArrayList<Machine> machines;
    private transient Status status;
    private transient Health health;

    // Server notifier.
    public transient Notifier notifier;

    // API Connector.
    private transient Retrofit connector;
    private transient IConnector service;

    public static Server createServer(String hostname, String port, String displayName) {
        return new Server(hostname, port, displayName, UUID.randomUUID().toString());
    }

    /**
     * Basic constructor.
     * @param hostname The hostname of the server.
     * @param port The hostname of the server.
     * @param displayName The display name of the server.
     * @param id The id of this server.
     */
    public Server(String hostname, String port, String displayName, String id) {
        // Static data.
        this.hostname = hostname;
        this.port = port;
        this.displayName = displayName;
        this.id = id;

        // Dynamic data.
        this.machines = new ArrayList<>();
        this.status = new Status(new Service(false), new Service(false));
        this.health = new Health(false);

        // Notifier.
        this.notifier = new Notifier();
        this.registerCallbacks();

        // API Connector, a separate instance for each server.
        this.connector = new Retrofit.Builder()
            .baseUrl(hostname + ":" + port)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        this.service = this.connector.create(IConnector.class);
    }

    /**
     * Returns whether the server is healthy or not. (healthy = reachable)
     * @return True if healthy, false otherwise.
     */
    public boolean isHealty() {
        return health.ok;
    }

    /**
     * Returns whether the Mongo service server is healthy or not.
     * @return True if healthy, false otherwise.
     */
    public boolean isMongoHealty() {
        return status.mongo.isUp;
    }

    /**
     * Returns whether the Docker service server is healthy or not.
     * @return True if healthy, false otherwise.
     */
    public boolean isDockerHealty() {
        return status.docker.isUp;
    }

    /**
     * Executes the callbacks of the server notifier.
     */
    public void executeCallbacks() {
        if (notifier != null) {
            notifier.executeCallbacks();
        }
    }

    /**
     * Registers some callbacks in this server notifier.
     */
    private void registerCallbacks() {
        this.notifier.addCallback(o -> {
            // Update health.
            this.getHealth();
            return null;
        });

        this.notifier.addCallback(o -> {
            // Update status.
            this.getStatus();
            return null;
        });
    }

    /**
     * Returns the machines of this server.
     * @return An array of machines.
     */
    public ArrayList<Machine> getMachines() {
        return machines;
    }

    /**
     * API. Issues an API call to the backend to update the health.
     */
    private void getHealth() {
        this.service.getHealth().enqueue(new Callback<Health>() {
            @Override
            public void onResponse(Call<Health> call, Response<Health> response) {
                health = response.body();
                EventManager.Instance().healthEvent(displayName, true);
                ServerManager.Instance().notifier.executeCallbacks();
            }

            @Override
            public void onFailure(Call<Health> call, Throwable t) {
                health = new Health(false);
                machines.clear();
                EventManager.Instance().healthEvent(displayName, false);
            }
        });
    }

    /**
     * API. Issues an API call to the backend to update the status.
     */
    private void getStatus() {
        this.service.getStatus().enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                status = response.body();
                EventManager.Instance().statusEvent(displayName, true);
                ServerManager.Instance().notifier.executeCallbacks();
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                status = new Status(new Service(false), new Service(false));
                machines.clear();
                EventManager.Instance().statusEvent(displayName, false);
            }
        });
    }

    /**
     * API. Issues an API call to the backend to create a new machine.
     */
    public void createMachine(Machine machine) {
        this.service.postMachine(machine).enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                EventManager.Instance().addEvent(
                    "[" + displayName + "] Machine " + machine.name + " has been created."
                );

                notifier.executeCallbacks();
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                EventManager.Instance().addErrorEvent(
                    "[" + displayName + "] Failed to create the machine: " + machine.name
                );
            }
        });
    }

    /**
     * API. Issues an API call to the backend to update a machine.
     */
    public void updateMachine(String name, Machine machine) {
        this.service.putMachine(new UpdateElement<>(name, machine))
            .enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                EventManager.Instance().addEvent(
                    "[" + displayName + "] Machine " + name + " has been updated."
                );

                notifier.executeCallbacks();
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                EventManager.Instance().addErrorEvent(
                    "[" + displayName + "] Failed to update the machine: " + name
                );
            }
        });
    }

    /**
     * API. Issues an API call to the backend to delete a machine.
     */
    public void deleteMachine(Machine machine) {
        this.service.deleteMachine(new RemoveElement(machine.name))
            .enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                EventManager.Instance().addEvent(
                    "[" + displayName + "] Machine " + machine.name + " has been deleted."
                );

                notifier.executeCallbacks();
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                EventManager.Instance().addErrorEvent(
                    "[" + displayName + "] Failed to delete the machine: " + machine.name
                );
            }
        });
    }

    /**
     * API. Issues an API call to the backend to fetch the machines.
     */
    public void queryMachine() {
        queryMachine(new Query());
    }

    /**
     * API. Issues an API call to the backend to fetch the machines.
     * @param query A query.
     */
    private void queryMachine(Query query) {
        this.service.postMachineQuery(query).enqueue(new Callback<ComplexResponse<Machine>>() {
            @Override
            public void onResponse(
                    Call<ComplexResponse<Machine>> call, Response<ComplexResponse<Machine>> response
            ) {
                machines = response.body().items;
            }

            @Override
            public void onFailure(Call<ComplexResponse<Machine>> call, Throwable t) {
                EventManager.Instance().addErrorEvent(
                    "[" + displayName + "] Failed to fetch the machines."
                );

                machines.clear();
            }
        });
    }
}
