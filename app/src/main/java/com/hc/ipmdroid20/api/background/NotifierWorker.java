package com.hc.ipmdroid20.api.background;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.jetbrains.annotations.NotNull;


public class NotifierWorker extends Worker {
    private Notifier notifier;

    public NotifierWorker(
            @NonNull @NotNull Context context,
            @NonNull @NotNull WorkerParameters workerParams,
            Notifier notifier
    ) {
        super(context, workerParams);
        this.notifier = notifier;
    }

    @NonNull
    @NotNull
    @Override
    public Result doWork() {
        try {
            notifier.backgroundExecuteCallbacks();
        } catch (Exception e) {
            return Result.failure();
        }

        return Result.success();
    }
}