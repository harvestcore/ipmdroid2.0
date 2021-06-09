package com.hc.ipmdroid20.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.hc.ipmdroid20.R;
import com.hc.ipmdroid20.api.background.TaskManager;
import com.hc.ipmdroid20.api.server.EventManager;

public class SettingsFragment extends Fragment {
    EditText updateIntervalInput;
    Button saveSettingsButton;

    public SettingsFragment() {}

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        updateIntervalInput = view.findViewById(R.id.updateIntervalInput);
        updateIntervalInput.setText(Integer.toString(TaskManager.Instance().getHumanInterval()));

        saveSettingsButton = view.findViewById(R.id.saveSettingsButton);
        saveSettingsButton.setOnClickListener(v -> {
            int updateIntervalValue = Integer.parseInt(updateIntervalInput.getText().toString());

            if (updateIntervalValue > 0) {
                TaskManager.Instance().setHumanInterval(updateIntervalValue);
                EventManager.Instance().addEvent(
                    "Update interval set to " + updateIntervalValue + "s."
                );

                Snackbar.make(
                    view, "Settings saved.", BaseTransientBottomBar.LENGTH_LONG
                ).show();
            }
        });

        return view;
    }
}