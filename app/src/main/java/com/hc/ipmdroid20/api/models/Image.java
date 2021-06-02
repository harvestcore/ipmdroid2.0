package com.hc.ipmdroid20.api.models;

import java.util.ArrayList;

public class Image {
    public String id;
    public String short_id;
    public ArrayList<String> tags;

    public Image(String id, String short_id, ArrayList<String> tags) {
        this.id = id;
        this.short_id = short_id;
        this.tags = tags;
    }
}
