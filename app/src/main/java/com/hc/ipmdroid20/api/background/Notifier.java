package com.hc.ipmdroid20.api.background;

import android.content.Context;

import java.util.ArrayList;
import java.util.function.Function;

public class Notifier {
    private ArrayList<Function> callbacks;
    private NotifierWorker worker;

    public Notifier(Context context) {
        callbacks = new ArrayList<>();
        worker = new NotifierWorker(context, null, this);
    }

    public Function addCallback(Function f) {
        callbacks.add(f);

        return o -> {
            callbacks.remove(f);
            return null;
        };
    }

    void backgroundExecuteCallbacks() {
        for (Function f: callbacks) {
            f.apply(null);
        }
    }

    public void executeCallbacks() {
        worker.doWork();
    }
}
