package com.hc.ipmdroid20.ui.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hc.ipmdroid20.R;

import org.jetbrains.annotations.NotNull;

public class ServerHolder extends RecyclerView.ViewHolder {
    public TextView serverNameLabel;
    public TextView serverURI;
    public ImageView healthIcon;
    public ImageView dockerStatusIcon;
    public ImageView mongoStatusIcon;
    public ImageView currentServerIcon;

    public ServerHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        serverNameLabel = itemView.findViewById(R.id.serverNameLabel);
        serverURI = itemView.findViewById(R.id.serverURI);
        healthIcon = itemView.findViewById(R.id.healthIcon);
        dockerStatusIcon = itemView.findViewById(R.id.dockerStatusIcon);
        mongoStatusIcon = itemView.findViewById(R.id.mongoStatusIcon);
        currentServerIcon = itemView.findViewById(R.id.currentServerIcon);
    }
}
