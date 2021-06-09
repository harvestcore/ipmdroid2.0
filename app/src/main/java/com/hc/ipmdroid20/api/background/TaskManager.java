package com.hc.ipmdroid20.api.background;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.hc.ipmdroid20.App;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;
import java.util.function.Function;

public class TaskManager {
    private static final int MS = 1000;
    private static TaskManager manager;
    private HashMap<UUID, Function> tasks;
    private ArrayList<Function> persistentTasks;
    private LinkedList<Function> queuedTasks;
    private int humanInterval = 5;
    private int interval = humanInterval * MS;

    private Context context;

    private TaskManager() {
        tasks = new HashMap<>();
        context = App.getContext();
        queuedTasks = new LinkedList<>();
        persistentTasks = new ArrayList<>();
    }

    public static TaskManager Instance() {
        if (manager == null) {
            manager = new TaskManager();
        }

        return manager;
    }

    public void setHumanInterval(int humanInterval) {
        this.humanInterval = humanInterval;
        this.interval = humanInterval * MS;
    }

    public int getHumanInterval() {
        return humanInterval;
    }

    public int getInterval() {
        return interval;
    }

    Function getTask(String uuid) {
        if (uuid == null || uuid.equals("")) {
            return null;
        }

        return tasks.get(UUID.fromString(uuid));
    }

    void removeTask(String uuid) {
        if (uuid == null || uuid.equals("")) {
            tasks.remove(UUID.fromString(uuid));
        }
    }

    public void runTask(Function f) {
        UUID uuid = UUID.randomUUID();
        tasks.put(uuid, f);

        @SuppressLint("RestrictedApi")
        Data data = new Data.Builder().put("task", uuid.toString()).build();

        WorkManager.getInstance(context).enqueue(
            new OneTimeWorkRequest.Builder(TaskWorker.class)
                .setInputData(data)
                .build()
        );
    }

    public void addPersistentTask(Function f) {
        if (f != null) {
            persistentTasks.add(f);
        }
    }

    public ArrayList<Function> getPersistentTasks() {
        return persistentTasks;
    }

    public void enqueueTask(Function f) {
        queuedTasks.add(f);
    }

    boolean hasQueuedTask() {
        return !queuedTasks.isEmpty();
    }

    Function popQueuedTask() {
        return queuedTasks.poll();
    }
}

