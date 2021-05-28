package com.hc.ipmdroid20.api.models;

import java.util.Date;
import java.util.UUID;

public class Event {
    public EventType type;
    public String message;
    public Date timestamp;
    public String id;

    public Event(EventType type, String message) {
        this.type = type;
        this.message = message;
        this.timestamp = new Date(System.currentTimeMillis());
        this.id = UUID.randomUUID().toString();
    }
}
