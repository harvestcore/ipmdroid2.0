package com.hc.ipmdroid20.ui.dialogs;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.hc.ipmdroid20.api.models.Machine;

public class MachineDialog extends AppCompatDialogFragment {
    Machine machine;

    MachineDialog(Machine machine) {
        if (machine != null) {
            this.machine = machine;
        }
    }
}
