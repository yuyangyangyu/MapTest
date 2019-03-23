package com.example.maptest.Search;


import android.content.Context;
import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maptest.R;

import java.util.List;
//增加点击事件
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<String> mlist;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View click;
        private TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            click=itemView;
            textView=itemView.findViewById(R.id.item_theme);
        }
    }

    public MyAdapter(Context context,List<String> mlist) {
        this.mlist=mlist;
        this.context=context;
    }

    @NonNull
    @Override//添加点击事件   跳转浏览器
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_item,viewGroup,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                String M=mlist.get(position);
                Toast.makeText(v.getContext(),"you click"+M,Toast.LENGTH_SHORT).show();
                Intent intent_web = new Intent(v.getContext(),Web.class);
                intent_web.putExtra("adress",M);
                context.startActivity(intent_web);

            }
        });
        //长按事件
        holder.click.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position=holder.getAdapterPosition();
                String M=mlist.get(position);
                Toast.makeText(v.getContext(),"长按"+M,Toast.LENGTH_SHORT).show();
                return true;
            }
        });
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
