package com.hc.ipmdroid20.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hc.ipmdroid20.R;
import com.hc.ipmdroid20.api.models.Event;
import com.hc.ipmdroid20.api.models.EventType;
import com.hc.ipmdroid20.api.server.EventManager;
import com.hc.ipmdroid20.ui.holders.BaseAdapter;
import com.hc.ipmdroid20.ui.holders.LogHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.function.Function;

public class LogFragment extends Fragment {
    RecyclerView reciclerView;
    ArrayList<Function> removeCallbacks = new ArrayList<>();

    public LogFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_log, container, false);

        // Log recycler view.
        reciclerView = root.findViewById(R.id.logRecyclerView);
        reciclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reciclerView.setAdapter(new BaseAdapter<Event>(
                getContext(), EventManager.Instance().getEvents()) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(getContext())
                        .inflate(R.layout.log_row, parent, false);
                return new LogHolder(view);
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, Event val) {
                LogHolder logHolder = (LogHolder) holder;
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

                logHolder.logText.setText(val.message);
                logHolder.logTime.setText(sdf.format(val.timestamp));

                if (val.type == EventType.DEPLOY) {
                    logHolder.logIcon.setImageResource(R.drawable.docker);
                } else if (val.type == EventType.MACHINE) {
                    logHolder.logIcon.setImageResource(R.drawable.machine);
                } else if (val.type == EventType.SETTINGS) {
                    logHolder.logIcon.setImageResource(R.drawable.settings);
                } else if (val.type == EventType.SERVER_UPDATE) {
                    logHolder.logIcon.setImageResource(R.drawable.refresh);
                } else {
                    logHolder.logIcon.setImageResource(R.drawable.info);
                }
            }
        });

        // Register callbacks.
        registerCallbacks();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        removeCallbacks();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeCallbacks();
    }

    private void registerCallbacks() {
        removeCallbacks.add(EventManager.Instance().notifier.addUICallback(getActivity(), o -> {
            reciclerView.getAdapter().notifyDataSetChanged();
            return null;
        }));
    }

    private void removeCallbacks() {
        for (Function f: removeCallbacks) {
            f.apply(null);
        }
    }
}