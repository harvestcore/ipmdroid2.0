package com.hc.ipmdroid20.api.server;

import com.hc.ipmdroid20.api.models.Event;

import java.util.ArrayList;

public class EventManager {
    private static EventManager manager;
    private ArrayList<Event> events;

    private EventManager() {
        events = new ArrayList<>();
    }

    public static EventManager Instance() {
        if (manager == null) {
            manager = new EventManager();
        }

        return manager;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void addEvent(Event e) {
        events.add(0, e);
    }
}
