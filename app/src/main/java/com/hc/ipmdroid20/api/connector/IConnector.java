package com.hc.ipmdroid20.api.connector;

import com.hc.ipmdroid20.api.models.Machine;
import com.hc.ipmdroid20.api.models.Query;
import com.hc.ipmdroid20.api.models.api.ComplexResponse;
import com.hc.ipmdroid20.api.models.api.SimpleResponse;
import com.hc.ipmdroid20.api.models.status.Service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IConnector {
    // STATUS

    @GET("/status")
    Call<Service> getStatus();

    // MACHINES

    @POST("/machine")
    Call<SimpleResponse> postMachine(@Body Machine machine);

    @PUT("/machine")
    Call<SimpleResponse> putMachine(@Body Machine machine);

    @DELETE("/machine/{name}")
    Call<SimpleResponse> deleteMachine(@Path("name") String name);

    @POST("/machine/query")
    Call<ComplexResponse<Machine>> postMachineQuery(@Body Query query);

    // IMAGES AND CONTAINERS.
    // TODO: This is disabled for now. Investigate the models needed in the backend.

    // @POST("/deploy/container")
    // Call<ComplexResponse> postDeployContainer();

    // @POST("/deploy/container/single")
    // Call<ComplexResponse> postDeployContainerSingle();

    // @POST("/deploy/image")
    // Call<ComplexResponse> postDeployImage();
}
