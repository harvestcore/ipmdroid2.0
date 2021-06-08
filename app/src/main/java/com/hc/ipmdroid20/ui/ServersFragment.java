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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.hc.ipmdroid20.R;
import com.hc.ipmdroid20.api.models.Server;
import com.hc.ipmdroid20.api.server.ServerManager;
import com.hc.ipmdroid20.ui.dialogs.MachineDialog;
import com.hc.ipmdroid20.ui.dialogs.ServerDialog;
import com.hc.ipmdroid20.ui.holders.BaseAdapter;
import com.hc.ipmdroid20.ui.holders.ServerHolder;

import java.util.ArrayList;
import java.util.function.Function;

public class ServersFragment extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton addServerFAB;

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
            getContext(), ServerManager.Instance().getServers()
        ) {
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

                serverHolder.setOnLongPressCallback(o -> {
                    ServerDialog serverDialog = new ServerDialog(val);
                    serverDialog.show(getActivity().getSupportFragmentManager(), "Server");
                    return null;
                });

                Server currentServer = ServerManager.Instance().getCurrentServer();
                boolean isCurrentServer = currentServer != null && currentServer.id.equals(val.id);

                serverHolder.serverNameLabel.setText(val.displayName);
                serverHolder.serverURI.setText(val.hostname + ":" + val.port);

                if (val.isHealty()) {
                    serverHolder.healthIcon.setImageResource(R.drawable.icon_check);
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(serverHolder.healthIcon.getDrawable()),
                        ContextCompat.getColor(getContext(), green)
                    );
                } else {
                    serverHolder.healthIcon.setImageResource(R.drawable.icon_cancel);
                    DrawableCompat.setTint(
                            DrawableCompat.wrap(serverHolder.healthIcon.getDrawable()),
                            ContextCompat.getColor(getContext(), red)
                    );
                }

                if (val.isMongoHealty()) {
                    serverHolder.mongoStatusIcon.setImageResource(R.drawable.icon_check);
                    DrawableCompat.setTint(
                            DrawableCompat.wrap(serverHolder.mongoStatusIcon.getDrawable()),
                            ContextCompat.getColor(getContext(), green)
                    );
                } else {
                    serverHolder.mongoStatusIcon.setImageResource(R.drawable.icon_cancel);
                    DrawableCompat.setTint(
                            DrawableCompat.wrap(serverHolder.mongoStatusIcon.getDrawable()),
                            ContextCompat.getColor(getContext(), red)
                    );
                }

                if (val.isDockerHealty()) {
                    serverHolder.dockerStatusIcon.setImageResource(R.drawable.icon_check);
                    DrawableCompat.setTint(
                            DrawableCompat.wrap(serverHolder.dockerStatusIcon.getDrawable()),
                            ContextCompat.getColor(getContext(), green)
                    );
                } else {
                    serverHolder.dockerStatusIcon.setImageResource(R.drawable.icon_cancel);
                    DrawableCompat.setTint(
                            DrawableCompat.wrap(serverHolder.dockerStatusIcon.getDrawable()),
                            ContextCompat.getColor(getContext(), red)
                    );
                }

                serverHolder.currentServerIcon.setImageResource(
                    isCurrentServer ? R.drawable.icon_pin_filled : R.drawable.icon_pin
                );
            }
        });

        addServerFAB = view.findViewById(R.id.addServerFAB);
        addServerFAB.setOnClickListener(v -> {
            ServerDialog serverDialog = new ServerDialog(null);
            serverDialog.show(getActivity().getSupportFragmentManager(), "Server");
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