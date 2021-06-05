package com.hc.ipmdroid20;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.hc.ipmdroid20.api.background.TaskManager;
import com.hc.ipmdroid20.api.server.ServerManager;
import com.hc.ipmdroid20.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static MainActivity instance;
    private View contentView;
    private ActivityMainBinding binding;
    private BottomNavigationView bottomNavigationView;
    private NavController navController;

    public static View getView() {
        return instance.contentView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get the main view.
        contentView = findViewById(android.R.id.content).getRootView();

        // Setup bottom navigation.
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        // Restore the servers in the background.
        TaskManager.Instance().runTask(o -> {
            ServerManager.Instance().restoreServers();
            return null;
        });
    }
}