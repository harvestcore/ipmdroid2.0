package com.hc.ipmdroid20.api.background;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;


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
            String uuid = getInputData().getString("notifier");
            if (!uuid.equals("") && uuid != null) {
                Notifier notifier = NotifierManager.Instance().getNotifier(uuid);

                if (notifier != null) {
                    notifier.backgroundExecuteCallbacks();
                }
            }
        } catch (Exception e) {
            return Result.failure();
        }

        return Result.success();
    }
}