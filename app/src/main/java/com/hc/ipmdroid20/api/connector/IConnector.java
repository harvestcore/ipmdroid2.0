package com.hc.ipmdroid20.api.connector;

import com.hc.ipmdroid20.api.models.status.Service;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IConnector {
    @GET("/status")
    Call<Service> getStatus();
}
