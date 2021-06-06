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
import com.hc.ipmdroid20.api.models.Server;
import com.hc.ipmdroid20.api.server.ServerManager;
import com.hc.ipmdroid20.ui.holders.BaseAdapter;
import com.hc.ipmdroid20.ui.holders.LogHolder;
import com.hc.ipmdroid20.ui.holders.ServerHolder;

import java.util.ArrayList;
import java.util.function.Function;

public class ServersFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Function> removeCallbacks = new ArrayList<>();

    private int red = android.R.color.holo_red_dark;
    private int green = android.R.color.holo_green_dark;

    public ServersFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_servers, container, false);

        recyclerView = view.findViewById(R.id.serversRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new BaseAdapter<Server>(
                getContext(), ServerManager.Instance().getServers()) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(getContext())
                        .inflate(R.layout.server_row, parent, false);
                return new ServerHolder(view);
            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onBindData(RecyclerView.ViewHolder holder, Server val) {
                ServerHolder serverHolder = (ServerHolder) holder;
                Server currentServer = ServerManager.Instance().getCurrentServer();
                boolean isCurrentServer = currentServer != null && currentServer.id.equals(val.id);

                serverHolder.serverNameLabel.setText(val.displayName);
                serverHolder.serverURI.setText(val.hostname + ":" + val.port);
                DrawableCompat.setTint(
                        DrawableCompat.wrap(serverHolder.healthIcon.getDrawable()),
                        ContextCompat.getColor(getContext(), val.isHealty() ? green : red)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(serverHolder.mongoStatusIcon.getDrawable()),
                        ContextCompat.getColor(getContext(), val.isMongoHealty() ? green : red)
                );
                DrawableCompat.setTint(
                        DrawableCompat.wrap(serverHolder.dockerStatusIcon.getDrawable()),
                        ContextCompat.getColor(getContext(), val.isDockerHealty() ? green : red)
                );
                serverHolder.currentServerIcon.setImageResource(isCurrentServer ? R.drawable.icon_pin_filled : R.drawable.icon_pin);
            }
        });

        registerCallbacks();

        return view;
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
        removeCallbacks.add(ServerManager.Instance().notifier.addUICallback(getActivity(), o -> {
            recyclerView.getAdapter().notifyDataSetChanged();
            return null;
        }));
    }

    private void removeCallbacks() {
        for (Function f: removeCallbacks) {
            f.apply(null);
        }
    }
}