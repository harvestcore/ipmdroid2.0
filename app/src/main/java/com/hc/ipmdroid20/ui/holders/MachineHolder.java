package com.hc.ipmdroid20.ui.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hc.ipmdroid20.R;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class MachineHolder extends RecyclerView.ViewHolder {
    public TextView machineName;
    public TextView machineDescription;
    public TextView machineIPv4;
    public TextView machineMac;
    public ImageView machineTypeIcon;

    Function onClickCallback;

    public void setOnClickCallback(Function f) {
        onClickCallback = f;
    }

    public MachineHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        machineName = itemView.findViewById(R.id.machineName);
        machineDescription = itemView.findViewById(R.id.machineDescription);
        machineIPv4 = itemView.findViewById(R.id.machineIPv4);
        machineMac = itemView.findViewById(R.id.machineMac);
        machineTypeIcon = itemView.findViewById(R.id.machineTypeIcon);

        itemView.setOnClickListener(o -> {
            if (onClickCallback != null) {
                onClickCallback.apply(null);
            }
        });
    }
}
