package com.example.shree.jsonsqlite;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shree on 6/20/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<Data> arrayList;
    private Context context;

    public RecyclerAdapter(ArrayList<Data> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvId.setText(arrayList.get(position).getId());
        holder.tvName.setText(arrayList.get(position).getName());
        holder.tvEmail.setText(arrayList.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvId,tvName,tvEmail;
        public ViewHolder(View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.id);
            tvName = itemView.findViewById(R.id.name);
            tvEmail = itemView.findViewById(R.id.email);
        }

    }
}
