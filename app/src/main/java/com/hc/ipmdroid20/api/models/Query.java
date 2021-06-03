package com.hc.ipmdroid20.api.models;

import java.util.HashMap;

public class Query {
    public HashMap query;
    public HashMap filter;

    public Query() {
        this.query = new HashMap();
        this.filter = new HashMap();
    }

    public Query(HashMap query, HashMap filter) {
        this.query = query;
        this.filter = filter;
    }
}
