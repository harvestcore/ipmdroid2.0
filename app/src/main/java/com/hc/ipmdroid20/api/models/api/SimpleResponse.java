package com.hc.ipmdroid20.api.models.api;

/**
 * Simple response.
 */
public class SimpleResponse {
    public boolean ok;
    public String message;

    public SimpleResponse(boolean ok, String message) {
        this.ok = ok;
        this.message = message;
    }
}
