package com.hc.ipmdroid20.ui.holders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    ArrayList<T> data;

    public abstract RecyclerView.ViewHolder setViewHolder(ViewGroup parent);
    public abstract void onBindData(RecyclerView.ViewHolder holder, T val);

    public BaseAdapter(Context context, ArrayList<T> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = setViewHolder(parent);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindData(holder, data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
