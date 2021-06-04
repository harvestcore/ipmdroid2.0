package com.hc.ipmdroid20.api.models;

import com.hc.ipmdroid20.api.background.Notifier;
import com.hc.ipmdroid20.api.background.NotifierManager;
import com.hc.ipmdroid20.api.connector.IConnector;
import com.hc.ipmdroid20.api.models.api.ComplexResponse;
import com.hc.ipmdroid20.api.models.api.SimpleResponse;
import com.hc.ipmdroid20.api.models.status.Health;
import com.hc.ipmdroid20.api.models.status.Service;
import com.hc.ipmdroid20.api.models.status.Status;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    private transient Notifier notifier;

    // API Connector.
    private transient Retrofit connector;
    private transient IConnector service;

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
        NotifierManager.Instance().registerManager(this.notifier);
        this.registerCallbacks();

        // API Connector, a separate instance for each server.
        this.connector = new Retrofit.Builder()
            .baseUrl(hostname + ":" + port)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        this.service = this.connector.create(IConnector.class);
    }

    public void executeCallbacks() {
        this.notifier.executeCallbacks();
    }

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

        this.notifier.addCallback(o -> {
            // Update machines.
            this.queryMachine(new Query());
            return null;
        });
    }

    public void getHealth() {
        this.service.getHealth().enqueue(new Callback<Health>() {
            @Override
            public void onResponse(Call<Health> call, Response<Health> response) {
            }

            @Override
            public void onFailure(Call<Health> call, Throwable t) {
            }
        });
    }

    public void getStatus() {
        this.service.getStatus().enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
            }
        });
    }

    public void createMachine(Machine machine) {
        this.service.postMachine(machine).enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
            }
        });
    }

    public void updateMachine(Machine machine) {
        this.service.putMachine(machine).enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
            }
        });
    }

    public void deleteMachine(Machine machine) {
        this.service.deleteMachine(machine.name).enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
            }
        });
    }

    public void queryMachine(Query query) {
        this.service.postMachineQuery(query).enqueue(new Callback<ComplexResponse<Machine>>() {
            @Override
            public void onResponse(Call<ComplexResponse<Machine>> call, Response<ComplexResponse<Machine>> response) {
            }

            @Override
            public void onFailure(Call<ComplexResponse<Machine>> call, Throwable t) {
            }
        });
    }
}
