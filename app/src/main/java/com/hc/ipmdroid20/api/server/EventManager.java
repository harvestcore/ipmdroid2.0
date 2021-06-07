package com.hc.ipmdroid20.api.server;

import com.hc.ipmdroid20.api.background.Notifier;
import com.hc.ipmdroid20.api.models.Event;
import com.hc.ipmdroid20.api.models.EventType;

import java.util.ArrayList;

public class EventManager {
    private static EventManager manager;
    private ArrayList<Event> events;
    public Notifier notifier;

    private EventManager() {
        events = new ArrayList<>();
        notifier = new Notifier();
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

    public void addEvent(EventType type, String message) {
        addEvent(new Event(type, message));
    }

    public void addEvent(String message) {
        addEvent(new Event(EventType.NONE, message));
    }

    public void addEvent(Event e) {
        events.add(0, e);
        notifier.executeCallbacks();
    }

    public void removeEvent(int index) {
        events.remove(index);
        notifier.executeCallbacks();
    }

    // Specific events.
    public void statusEvent(String serverName, boolean status) {
        customUpdateEvent("Status", serverName, status);
    }

    public void healthEvent(String serverName, boolean status) {
        customUpdateEvent("Health", serverName, status);
    }

    private void customUpdateEvent(String event, String serverName, boolean status) {
        if (status) {
            addEvent(EventType.SERVER_UPDATE, "[" + serverName + "] " + event + " updated.");
        } else {
            addEvent(EventType.SERVER_UPDATE, "[" + serverName + "] " + event + " update failed.");
        }
    }
}
