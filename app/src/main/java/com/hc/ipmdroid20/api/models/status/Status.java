package com.hc.ipmdroid20.api.models.status;

public class Status {
    public Service mongo;
    public Service docker;

    public Status(Service mongo, Service docker) {
        this.mongo = mongo;
        this.docker = docker;
    }
}
