package com.hc.ipmdroid20.api.models;

import com.hc.ipmdroid20.api.connector.IConnector;
import com.hc.ipmdroid20.api.models.api.ComplexResponse;
import com.hc.ipmdroid20.api.models.api.SimpleResponse;
import com.hc.ipmdroid20.api.models.status.Status;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Server {
    // Server data.
    public String hostname;
    public String port;
    public String displayName;
    public String id;

    // API Connector.
    private Retrofit connector;
    private IConnector service;

    public Server(String hostname, String port, String displayName, String id) {
        this.hostname = hostname;
        this.port = port;
        this.displayName = displayName;
        this.id = id;

        // Create a separate connector for each server.
        this.connector = new Retrofit.Builder()
            .baseUrl(hostname + ":" + port)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        this.service = this.connector.create(IConnector.class);
    }

    public void getStatus() {
        this.service.getStatus().enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                System.out.println(response);
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                System.out.println("rip");
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
