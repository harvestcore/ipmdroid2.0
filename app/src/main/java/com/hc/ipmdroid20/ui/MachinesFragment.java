package com.hc.ipmdroid20.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.hc.ipmdroid20.R;
import com.hc.ipmdroid20.api.models.Machine;
import com.hc.ipmdroid20.api.models.Server;
import com.hc.ipmdroid20.api.server.ServerManager;
import com.hc.ipmdroid20.ui.dialogs.MachineDialog;
import com.hc.ipmdroid20.ui.holders.BaseAdapter;
import com.hc.ipmdroid20.ui.holders.MachineHolder;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;

public class MachinesFragment extends Fragment {
    ArrayList<Function> removeCallbacks = new ArrayList<>();
    RecyclerView recyclerView;
    TextView machinesCurrentServerName;
    FloatingActionButton addMachineFAB;

    public MachinesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_machines, container, false);

        Server currentServer = ServerManager.Instance().getCurrentServer();

        machinesCurrentServerName = view.findViewById(R.id.machineName);
        machinesCurrentServerName.setText(
            currentServer == null ? "[No server]" : currentServer.displayName
        );

        recyclerView = view.findViewById(R.id.machinesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new BaseAdapter<Machine>(
            getContext(), ServerManager.Instance().getCurrentServerMachines()
        ) {
            @Override
            public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.machine_row, parent, false);

                return new MachineHolder(view);
            }

            @Override
            public void onBindData(RecyclerView.ViewHolder holder, Machine val) {
                MachineHolder machineHolder = (MachineHolder) holder;

                machineHolder.setOnClickCallback(o -> {
                    MachineDialog machineDialog = new MachineDialog(val);
                    machineDialog.show(
                        requireActivity().getSupportFragmentManager(), "Machine"
                    );
                    return null;
                });

                machineHolder.machineName.setText(val.name);
                machineHolder.machineDescription.setText(
                    valueOrDefault(val.description, "[No description]")
                );
                machineHolder.machineIPv4.setText(valueOrDefault(val.ipv4, "[No IPv4]"));
                machineHolder.machineMac.setText(valueOrDefault(val.mac, "[No MAC]"));
                machineHolder.machineTypeIcon.setImageResource(
                    val.type.equals("local") ? R.drawable.local : R.drawable.cloud
                );
            }
        });

        addMachineFAB = view.findViewById(R.id.addMachineFAB);
        addMachineFAB.setOnClickListener(v -> {
            if (ServerManager.Instance().hasCurrentServer()) {
                MachineDialog machineDialog = new MachineDialog(null);
                machineDialog.show(getActivity().getSupportFragmentManager(), "Machine");
            } else {
                Snackbar.make(
                    view,
                    "Please, select a server.",
                    BaseTransientBottomBar.LENGTH_LONG
                ).show();
            }
        });

        // Register callbacks.
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
        if (ServerManager.Instance().hasCurrentServer()) {
            removeCallbacks.add(
                ServerManager.Instance().getCurrentServer().notifier.addUICallback(
                    getActivity(),
                    o -> {
                        recyclerView.getAdapter().notifyDataSetChanged();
                        return null;
                    }
                )
            );
        }
    }

    private void removeCallbacks() {
        for (Function f: removeCallbacks) {
            f.apply(null);
        }
    }

    private String valueOrDefault(String value, String defaultValue) {
        if (value != null && !value.isEmpty()) {
            return value;
        }

        return defaultValue;
    }
}