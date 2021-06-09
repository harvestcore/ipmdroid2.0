package com.hc.ipmdroid20.api.background;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * Task worker.
 */
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
            // Get the task uuid from the worker data.
            String uuid = getInputData().getString("task");
            if (uuid != null && !uuid.equals("")) {
                // Get the task from the manager.
                Function task = TaskManager.Instance().getTask(uuid);
                if (task != null) {
                    // Execute the callback and remove it.
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
