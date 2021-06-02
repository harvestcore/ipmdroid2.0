package com.hc.ipmdroid20.api.models;

import com.google.gson.Gson;

public class Query {
    public Gson query;
    public Gson filter;

    public Query(Gson query, Gson filter) {
        this.query = query;
        this.filter = filter;
    }
}
