package com.hc.ipmdroid20.ui.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hc.ipmdroid20.R;

import org.jetbrains.annotations.NotNull;

public class LogHolder extends RecyclerView.ViewHolder {
    public TextView logText;
    public TextView logTime;
    public ImageView logIcon;

    public LogHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        logText = itemView.findViewById(R.id.logText);
        logTime = itemView.findViewById(R.id.logTime);
        logIcon = itemView.findViewById(R.id.logIcon);
    }
}
