package com.hc.ipmdroid20.api.models.api;

/**
 * Update element. This class represents a dictionary with two keys:
 * - name (an string)
 * - items (an object of type T)
 * @param <T> The type of the element.
 */
public class UpdateElement<T> {
    public String name;
    public T data;

    public UpdateElement(String name, T data) {
        this.name = name;
        this.data = data;
    }
}
