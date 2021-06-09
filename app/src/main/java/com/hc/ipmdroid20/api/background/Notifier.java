package com.hc.ipmdroid20.api.background;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.hc.ipmdroid20.App;

import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Function;

/**
 * Notifier class.
 */
public class Notifier {
    private ArrayList<Function> callbacks;
    private Context context;
    UUID uuid;

    /**
     * Notifier basic constructor.
     */
    public Notifier() {
        callbacks = new ArrayList<>();
        this.context = App.getContext();
        this.uuid = UUID.randomUUID();

        // Register the notifier in the manager so it can be used by the background tasks.
        NotifierManager.Instance().registerNotifier(this);
    }

    /**
     * Adds a new callback to the list.
     * @param f The callback to be added.
     * @return The callback that removes the added callback from the list.
     */
    public Function addCallback(Function f) {
        // Add the new callback to the list.
        callbacks.add(f);

        // Return the callback to remove the new callback.
        return o -> {
            callbacks.remove(f);
            return null;
        };
    }

    /**
     * Adds a new UI callback. An UI callback is a callback that must be run in the UI thread.
     * @param activity The activity where to run the callback.
     * @param f The callback to be added.
     * @return The callback that removes the added callback from the list.
     */
    public Function addUICallback(Activity activity, Function f) {
        // This callback must be run in the UI thread.
        return addCallback(o -> {
            activity.runOnUiThread(() -> f.apply(null));
            return null;
        });
    }

    /**
     * Executes all the callbacks. This method is called by the background process.
     */
    void backgroundExecuteCallbacks() {
        // Execute all the callbacks.
        for (Function f: callbacks) {
            f.apply(null);
        }
    }

    /**
     * Executes all the callbacks. To do so it enqueues a background job.
     */
    public void executeCallbacks() {
        // Input data of the job.
        // Used to identify the notifier.
        @SuppressLint("RestrictedApi")
        Data data = new Data.Builder().put("notifier", uuid.toString()).build();

        // Run the callbacks in the background.
        WorkManager.getInstance(context).enqueue(
            new OneTimeWorkRequest.Builder(NotifierWorker.class)
                .setInputData(data)
                .build()
        );
    }
}
