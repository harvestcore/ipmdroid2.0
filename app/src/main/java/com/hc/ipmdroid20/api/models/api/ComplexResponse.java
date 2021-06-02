package com.hc.ipmdroid20.api.models.api;

import java.util.ArrayList;

public class ComplexResponse<T> {
    public int total;
    public ArrayList<T> items;

    public ComplexResponse(int total, ArrayList<T> items) {
        this.total = total;
        this.items = items;
    }
}
