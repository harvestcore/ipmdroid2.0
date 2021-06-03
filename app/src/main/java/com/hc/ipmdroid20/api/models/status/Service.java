package com.hc.ipmdroid20.api.models.status;

import com.google.gson.annotations.SerializedName;

public class Service {
    @SerializedName(value="is_up")
    public boolean isUp;

    public Service(boolean isUp) {
        this.isUp = isUp;
    }
}
