package com.hc.ipmdroid20.api.background;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class TaskWorker extends Worker {
    public TaskWorker(
        @NonNull @NotNull Context context,
        @NonNull @NotNull WorkerParameters workerParams)
    {
        super(context, workerParams);
    }

    @NonNull
    @NotNull
    @Override
    public Result doWork() {
        try {
            String uuid = getInputData().getString("task");
            if (!uuid.equals("") && uuid != null) {
                Function task = TaskManager.Instance().getTask(uuid);
                if (task != null) {
                    task.apply(null);
                    TaskManager.Instance().removeTask(uuid);
                }
            }
        } catch (Exception e) {
            return Result.failure();
        }

        return Result.success();
    }
}
