package com.hc.ipmdroid20.api.models;

public class Container {
    public String id;
    public String short_id;
    public String name;
    public String status;
    public Image image;

    public Container(String id, String short_id, String name, String status, Image image) {
        this.id = id;
        this.short_id = short_id;
        this.name = name;
        this.status = status;
        this.image = image;
    }
}
