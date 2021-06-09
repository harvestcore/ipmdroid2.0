package com.hc.ipmdroid20.api.background;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.jetbrains.annotations.NotNull;

/**
 * Notifier worker.
 */
public class NotifierWorker extends Worker {
    public NotifierWorker(
        @NonNull @NotNull Context context,
        @NonNull @NotNull WorkerParameters workerParams
    ) {
        super(context, workerParams);
    }

    @NonNull
    @NotNull
    @Override
    public Result doWork() {
        try {
            // Get the notifier uuid from the worker data.
            String uuid = getInputData().getString("notifier");
            if (uuid != null && !uuid.equals("")) {
                // Get the notifier from the manager.
                Notifier notifier = NotifierManager.Instance().getNotifier(uuid);
                if (notifier != null) {
                    // Execute its callbacks.
                    notifier.backgroundExecuteCallbacks();
                }
            }
        } catch (Exception e) {
            return Result.failure();
        }

        return Result.success();
    }
}