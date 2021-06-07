package com.hc.ipmdroid20;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hc.ipmdroid20.api.background.TaskManager;
import com.hc.ipmdroid20.api.server.ServerManager;

import java.util.function.Function;

public class SplashScreen extends AppCompatActivity {
    Function goToMainActivityRemoveCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set content view.
        setContentView(R.layout.splash_screen);

        // Go to the main activity as soon as the servers are loaded.
        goToMainActivityRemoveCallback = ServerManager.Instance().notifier.addCallback(o -> {
            goToMainActivity();
            return null;
        });

        // Restore the servers in the background.
        TaskManager.Instance().runTask(o -> {
            ServerManager.Instance().restoreServers();
            return null;
        });
    }

    private void goToMainActivity() {
        if (goToMainActivityRemoveCallback != null) {
            goToMainActivityRemoveCallback.apply(null);
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}