package com.hc.ipmdroid20.api.models.status;

public class Service {
    public boolean is_up;
    public String info;

    public Service(boolean is_up, String info) {
        this.is_up = is_up;
        this.info = info;
    }
}
