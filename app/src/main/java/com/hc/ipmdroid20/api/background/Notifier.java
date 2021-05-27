package com.hc.ipmdroid20.api.background;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Function;

public class Notifier {
    private ArrayList<Function> callbacks;
    private Context context;
    UUID uuid;

    public Notifier(Context context) {
        callbacks = new ArrayList<>();
        this.context = context;
        this.uuid = UUID.randomUUID();
        NotifierManager.Instance().registerManager(this);
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
        @SuppressLint("RestrictedApi") Data data =
                new Data.Builder().put("notifier", uuid.toString()).build();

        WorkManager.getInstance(context).enqueue(
                new OneTimeWorkRequest.Builder(NotifierWorker.class).setInputData(data).build()
        );
    }
}
