package com.hc.ipmdroid20.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hc.ipmdroid20.R;
import com.hc.ipmdroid20.api.models.Event;
import com.hc.ipmdroid20.api.server.EventManager;
import com.hc.ipmdroid20.ui.holders.BaseAdapter;
import com.hc.ipmdroid20.ui.holders.LogHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;

public class LogFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Function> removeCallbacks = new ArrayList<>();

    public LogFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_log, container, false);

        // Log recycler view.
        recyclerView = root.findViewById(R.id.logRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new BaseAdapter<Event>(
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

                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

                logHolder.logText.setText(val.message);
                logHolder.logTime.setText(sdf.format(val.timestamp));

                switch (val.type) {
                    case DEPLOY:
                        logHolder.logIcon.setImageResource(R.drawable.docker);
                        break;
                    case MACHINE:
                        logHolder.logIcon.setImageResource(R.drawable.machine);
                        break;
                    case SETTINGS:
                        logHolder.logIcon.setImageResource(R.drawable.settings);
                        break;
                    case SERVER_UPDATE:
                        logHolder.logIcon.setImageResource(R.drawable.refresh);
                        break;
                    case ERROR:
                        logHolder.logIcon.setImageResource(R.drawable.icon_cancel);
                        DrawableCompat.setTint(
                            DrawableCompat.wrap(logHolder.logIcon.getDrawable()),
                            ContextCompat.getColor(requireContext(), android.R.color.holo_red_dark)
                        );
                        break;
                    default:
                        logHolder.logIcon.setImageResource(R.drawable.info);
                        break;
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
            Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
            return null;
        }));
    }

    private void removeCallbacks() {
        for (Function f: removeCallbacks) {
            f.apply(null);
        }
    }
}