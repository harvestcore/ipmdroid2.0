package com.hc.ipmdroid20;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hc.ipmdroid20.api.background.TaskManager;
import com.hc.ipmdroid20.api.models.Query;
import com.hc.ipmdroid20.api.models.Server;
import com.hc.ipmdroid20.api.server.Credentials;
import com.hc.ipmdroid20.api.server.ServerManager;
import com.hc.ipmdroid20.ui.main.SectionsPagerAdapter;
import com.hc.ipmdroid20.databinding.ActivityMainBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static MainActivity instance;
    private ActivityMainBinding binding;
    private View contentView;

    public static View getView() {
        return instance.contentView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fab;

        // Get the main view.
        contentView = findViewById(android.R.id.content).getRootView();

        // Restore the servers in the background.
        TaskManager.Instance().runTask(o -> {
            ServerManager.Instance().restoreServers();
            return null;
        });

        fab.setOnClickListener(view -> {});
    }
}