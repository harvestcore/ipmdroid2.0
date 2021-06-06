package com.hc.ipmdroid20.api.background;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;

import java.util.ArrayList;
import java.util.function.Function;

@SuppressLint("SpecifyJobSchedulerIdRange")
public class BackgroundService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(TaskManager.Instance().getInterval());

                    if (TaskManager.Instance().hasQueuedTask()) {
                        Function f = TaskManager.Instance().popQueuedTask();
                        if (f != null) {
                            f.apply(null);
                        }
                    }

                    ArrayList<Function> persistentTasks = TaskManager.Instance().getPersistentTasks();
                    if (persistentTasks.size() > 0) {
                        for (Function task: persistentTasks) {
                            if (task != null) {
                                task.apply(null);
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}
