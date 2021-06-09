package com.hc.ipmdroid20.api.background;

import java.util.HashMap;
import java.util.UUID;

/**
 * Notifier manager.
 */
public class NotifierManager {
    private static NotifierManager manager;
    private HashMap<UUID, Notifier> notifiers;

    /**
     * Basic constructor.
     */
    private NotifierManager() {
        notifiers = new HashMap<>();
    }

    /**
     * Returns the unique instance of the manager.
     * @return The manager instance.
     */
    public static NotifierManager Instance() {
        if (manager == null) {
            manager = new NotifierManager();
        }

        return manager;
    }

    /**
     * Registers a callback in the manager so it can be used by the background tasks.
     * @param notifier The callback to be registered.
     */
    public void registerNotifier(Notifier notifier) {
        if (!notifiers.containsKey(notifier.uuid)) {
            notifiers.put(notifier.uuid, notifier);
        }
    }

    /**
     * Returns the notifier that matches the given uuid.
     * @param uuid The uuid of the notifier.
     * @return The notifier or null.
     */
    public Notifier getNotifier(String uuid) {
        if (uuid == null || uuid.equals("")) {
            return null;
        }

        return notifiers.get(UUID.fromString(uuid));
    }
}
