package com.hc.ipmdroid20.api.models.api;

public class UpdateElement<T> {
    public String name;
    public T data;

    public UpdateElement(String name, T data) {
        this.name = name;
        this.data = data;
    }
}
