package com.hc.ipmdroid20.ui.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hc.ipmdroid20.R;
import com.hc.ipmdroid20.api.models.Server;
import com.hc.ipmdroid20.api.server.EventManager;
import com.hc.ipmdroid20.api.server.ServerManager;
import com.hc.ipmdroid20.ui.dialogs.ServerDialog;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class ServerHolder extends RecyclerView.ViewHolder {
    public TextView serverNameLabel;
    public TextView serverURI;
    public ImageView healthIcon;
    public ImageView dockerStatusIcon;
    public ImageView mongoStatusIcon;
    public ImageView currentServerIcon;

    Function onLongPressCallback;

    public void setOnLongPressCallback(Function f) {
        onLongPressCallback = f;
    }

    public ServerHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        serverNameLabel = itemView.findViewById(R.id.machineName);
        serverURI = itemView.findViewById(R.id.machineDescription);
        healthIcon = itemView.findViewById(R.id.healthIcon);
        dockerStatusIcon = itemView.findViewById(R.id.dockerStatusIcon);
        mongoStatusIcon = itemView.findViewById(R.id.mongoStatusIcon);
        currentServerIcon = itemView.findViewById(R.id.currentServerIcon);

        itemView.setOnClickListener(o -> {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Server selected = ServerManager.Instance().getServers().get(position);
                Server current = ServerManager.Instance().getCurrentServer();

                if (current == null || !current.id.equals(selected.id)) {
                    ServerManager.Instance().setCurrentServer(selected);
                    EventManager.Instance().addEvent(
                        "Primary server set to: " + selected.displayName
                    );
                }
            }
        });

        itemView.setOnLongClickListener(o -> {
            if (onLongPressCallback != null) {
                onLongPressCallback.apply(null);
            }

            return true;
        });
    }
}
