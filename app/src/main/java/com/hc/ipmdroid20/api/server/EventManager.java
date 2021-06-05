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
        events.add(new Event(EventType.DEPLOY, "test1"));
        events.add(new Event(EventType.DEPLOY, "test2"));

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

    public void addEvent(Event e) {
        events.add(0, e);
        notifier.executeCallbacks();
    }
}
