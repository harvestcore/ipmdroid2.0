package com.hc.ipmdroid20.api.background;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Function;

public class TaskManager {
    private static TaskManager manager;
    private HashMap<UUID, Function> tasks;
    private Context context;

    private TaskManager() {
        tasks = new HashMap<>();
    }

    static TaskManager Instance() {
        if (manager == null) {
            manager = new TaskManager();
        }

        return manager;
    }

    void setContext(Context context) {
        this.context = context;
    }

    Function getTask(String uuid) {
        if (uuid == null || uuid.equals("")) {
            return null;
        }

        return tasks.get(UUID.fromString(uuid));
    }

    public void enqueueTask(Function f) {
        UUID uuid = UUID.randomUUID();
        tasks.put(uuid, f);

        @SuppressLint("RestrictedApi") Data data =
                new Data.Builder().put("task", uuid.toString()).build();

        WorkManager.getInstance(context).enqueue(
            new OneTimeWorkRequest.Builder(TaskWorker.class)
                .setInputData(data)
                .build()
        );
    }
}

