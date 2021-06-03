package com.hc.ipmdroid20.api.background;

import java.util.HashMap;
import java.util.UUID;

public class NotifierManager {
    private static NotifierManager manager;
    private HashMap<UUID, Notifier> notifiers;
    
    private NotifierManager() {
        notifiers = new HashMap<>();
    }
    
    public static NotifierManager Instance() {
        if (manager == null) {
            manager = new NotifierManager();
        }

        return manager;
    }

    public void registerManager(Notifier notifier) {
        if (!notifiers.containsKey(notifier.uuid)) {
            notifiers.put(notifier.uuid, notifier);
        }
    }

    public Notifier getNotifier(String uuid) {
        if (uuid == null || uuid.equals("")) {
            return null;
        }

        return notifiers.get(UUID.fromString(uuid));
    }
}
