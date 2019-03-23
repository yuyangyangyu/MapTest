package com.example.maptest.Plan;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maptest.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<com.example.maptest.Plan.MyAdapter.ViewHolder> {
    private List<String> mlist;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View click;
        private TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            click=itemView;
            textView=itemView.findViewById(R.id.plan_name);
        }
    }

    public MyAdapter(List<String> mlist) {
        this.mlist=mlist;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plan_item,viewGroup,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder viewHolder, int i) {
        String name=mlist.get(i);
        viewHolder.textView.setText(name);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
}
