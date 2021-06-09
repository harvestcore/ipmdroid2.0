package com.hc.ipmdroid20.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.hc.ipmdroid20.R;
import com.hc.ipmdroid20.api.models.Server;
import com.hc.ipmdroid20.api.server.ServerManager;

public class ServerDialog extends AppCompatDialogFragment {
    EditText serverNameInput;
    EditText serverHostnameInput;
    EditText serverPortInput;

    Server server;

    public ServerDialog(Server server) {
        if (server != null) {
            this.server = server;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.server_dialog, null);

        String title = server == null ? "Create server" : "See server";

        builder.setView(view).setTitle(title).setNegativeButton("Cancel", (dialog, which) -> dismiss())
                .setPositiveButton("Save", (DialogInterface.OnClickListener) (dialog, which) -> {
                    if (server == null) {
                        ServerManager.Instance().addServer(
                            Server.createServer(
                                serverHostnameInput.getText().toString(),
                                serverPortInput.getText().toString(),
                                serverNameInput.getText().toString()
                            )
                        );
                    } else {
                        ServerManager.Instance().updateServer(
                            server.id,
                            serverHostnameInput.getText().toString(),
                            serverPortInput.getText().toString(),
                            serverNameInput.getText().toString()
                        );
                    }
                });

        if (server != null) {
            builder.setNeutralButton("Remove", (dialog, which) -> {
                ServerManager.Instance().deleteServer(server.id);
                dismiss();
            });
        }

        serverNameInput = view.findViewById(R.id.serverNameInput);
        serverHostnameInput = view.findViewById(R.id.serverHostnameInput);
        serverPortInput = view.findViewById(R.id.serverPortInput);

        if (this.server != null) {
            serverNameInput.setText(server.displayName);
            serverHostnameInput.setText(server.hostname);
            serverPortInput.setText(server.port);
        }

        return builder.create();
    }
}
