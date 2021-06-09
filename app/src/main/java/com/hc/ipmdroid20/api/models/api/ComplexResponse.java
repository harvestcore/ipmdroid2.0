package com.hc.ipmdroid20.api.models.api;

import java.util.ArrayList;

/**
 * Complex response. This class represents a dictionary with two keys:
 * - total (an integer)
 * - items (an array of T elements)
 * @param <T> The type of the elements.
 */
public class ComplexResponse<T> {
    public int total;
    public ArrayList<T> items;

    /**
     * Basic constructor.
     * @param total Total number of elements.
     * @param items Array of elements.
     */
    public ComplexResponse(int total, ArrayList<T> items) {
        this.total = total;
        this.items = items;
    }
}
