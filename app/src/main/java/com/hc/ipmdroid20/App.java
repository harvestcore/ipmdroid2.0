package com.hc.ipmdroid20;

import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

import com.hc.ipmdroid20.api.background.BackgroundService;

public class App extends Application {
    private static App instance;
    private static final int IPM_SERVICE_ID = 12345;

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();

        ComponentName componentName = new ComponentName(this, BackgroundService.class);
        JobInfo info = new JobInfo.Builder(IPM_SERVICE_ID, componentName)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setPersisted(true)
            .setPeriodic(15 * 60000)
            .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.schedule(info);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(IPM_SERVICE_ID);
    }
}
