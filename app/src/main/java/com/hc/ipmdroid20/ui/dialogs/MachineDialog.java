package com.hc.ipmdroid20.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.hc.ipmdroid20.R;
import com.hc.ipmdroid20.api.models.Machine;
import com.hc.ipmdroid20.api.server.ServerManager;

public class MachineDialog extends AppCompatDialogFragment {
    EditText nameInput;
    EditText ipv4Input;
    EditText descriptionInput;
    EditText macInput;
    Switch localSwitch;
    Switch remoteSwitch;

    Machine machine;

    public MachineDialog(Machine machine) {
        if (machine != null) {
            this.machine = machine;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.machine_dialog, null);

        String title = machine == null ? "Create machine" : "See machine";

        builder.setView(view).setTitle(title).setNegativeButton("Cancel", (dialog, which) -> dismiss())
                .setPositiveButton("Save", (DialogInterface.OnClickListener) (dialog, which) -> {
                    String type = localSwitch.isChecked() ? "local" : "remote";
                    if (machine == null) {
                        ServerManager.Instance().getCurrentServer().createMachine(
                            new Machine(
                                nameInput.getText().toString(),
                                descriptionInput.getText().toString(),
                                type,
                                ipv4Input.getText().toString(),
                                macInput.getText().toString()
                            )
                        );
                    } else {
                        ServerManager.Instance().getCurrentServer().updateMachine(
                            machine.name,
                            new Machine(
                                nameInput.getText().toString(),
                                descriptionInput.getText().toString(),
                                type,
                                ipv4Input.getText().toString(),
                                macInput.getText().toString()
                            )
                        );
                    }
                });

        if (machine != null) {
            builder.setNeutralButton("Remove", (dialog, which) -> {
                ServerManager.Instance().getCurrentServer().deleteMachine(machine);
                dismiss();
            });
        }

        nameInput = view.findViewById(R.id.serverNameInput);
        descriptionInput = view.findViewById(R.id.serverHostnameInput);
        ipv4Input = view.findViewById(R.id.ipv4Input);
        macInput = view.findViewById(R.id.macInput);
        localSwitch = view.findViewById(R.id.localSwitch);
        remoteSwitch = view.findViewById(R.id.remoteSwitch);

        localSwitch.setChecked(true);

        localSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            localSwitch.setChecked(isChecked);
            remoteSwitch.setChecked(!isChecked);
        });

        remoteSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            remoteSwitch.setChecked(isChecked);
            localSwitch.setChecked(!isChecked);
        });

        if (this.machine != null) {
            nameInput.setText(machine.name);
            descriptionInput.setText(machine.description);
            ipv4Input.setText(machine.ipv4);
            macInput.setText(machine.mac);
            localSwitch.setChecked(machine.type.equals("local"));
            remoteSwitch.setChecked(machine.type.equals("remote"));
        }

        return builder.create();
    }
}
