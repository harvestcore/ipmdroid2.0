package com.hc.ipmdroid20.api.connector;

import com.hc.ipmdroid20.api.models.Machine;
import com.hc.ipmdroid20.api.models.Query;
import com.hc.ipmdroid20.api.models.api.ComplexResponse;
import com.hc.ipmdroid20.api.models.api.RemoveElement;
import com.hc.ipmdroid20.api.models.api.SimpleResponse;
import com.hc.ipmdroid20.api.models.api.UpdateElement;
import com.hc.ipmdroid20.api.models.status.Health;
import com.hc.ipmdroid20.api.models.status.Status;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface IConnector {
    // STATUS
    @GET("/api/healthcheck")
    Call<Health> getHealth();

    @GET("/status")
    Call<Status> getStatus();

    // MACHINES
    @POST("/machine")
    Call<SimpleResponse> postMachine(@Body Machine machine);

    @PUT("/machine")
    Call<SimpleResponse> putMachine(@Body UpdateElement<Machine> machine);

    @HTTP(method="DELETE", path="/machine", hasBody=true)
    Call<SimpleResponse> deleteMachine(@Body RemoveElement machine);

    @POST("/machine/query")
    Call<ComplexResponse<Machine>> postMachineQuery(@Body Query query);
}
