package com.hc.ipmdroid20.api.server;

import com.hc.ipmdroid20.api.background.Notifier;
import com.hc.ipmdroid20.api.models.Event;
import com.hc.ipmdroid20.api.models.EventType;

import java.util.ArrayList;

/**
 * Event manager.
 */
public class EventManager {
    private static EventManager manager;
    private ArrayList<Event> events;
    public Notifier notifier;

    /**
     * Basic constructor.
     */
    private EventManager() {
        events = new ArrayList<>();
        notifier = new Notifier();
    }

    /**
     * Returns the unique instance of the manager.
     * @return The manager instance.
     */
    public static EventManager Instance() {
        if (manager == null) {
            manager = new EventManager();
        }

        return manager;
    }

    /**
     * Returns all the events.
     * @return An array of events.
     */
    public ArrayList<Event> getEvents() {
        return events;
    }

    /**
     * Adds a new event.
     * @param type Type of the event.
     * @param message Message of the event.
     */
    public void addEvent(EventType type, String message) {
        addEvent(new Event(type, message));
    }

    /**
     * Adds a new event.
     * @param message Message of the event.
     */
    public void addEvent(String message) {
        addEvent(new Event(EventType.NONE, message));
    }

    /**
     * Adds a new event.
     * @param e The event to be added.
     */
    public void addEvent(Event e) {
        events.add(0, e);
        notifier.executeCallbacks();
    }

    /**
     * Adds an error event.
     * @param message Message of the event.
     */
    public void addErrorEvent(String message) {
        addEvent(EventType.ERROR, message);
    }

    /**
     * Removes an event.
     * @param index Index of the event to be removed.
     */
    public void removeEvent(int index) {
        events.remove(index);
        notifier.executeCallbacks();
    }

    /**
     * Adds an status event.
     * @param serverName Name of the server that issues this event.
     * @param status Status.
     */
    public void statusEvent(String serverName, boolean status) {
        customUpdateEvent("Status", serverName, status);
    }

    /**
     * Adds a health event.
     * @param serverName Name of the server that issues this event.
     * @param status Status.
     */
    public void healthEvent(String serverName, boolean status) {
        customUpdateEvent("Health", serverName, status);
    }

    /**
     * Adds a custom update event.
     * @param event Event.
     * @param serverName Name of the server that issues this event.
     * @param status Status.
     */
    private void customUpdateEvent(String event, String serverName, boolean status) {
        if (status) {
            addEvent(
                EventType.SERVER_UPDATE, "[" + serverName + "] " + event + " updated."
            );
        } else {
            addErrorEvent("[" + serverName + "] " + event + " update failed.");
        }
    }
}
