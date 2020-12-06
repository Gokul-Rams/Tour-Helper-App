package com.example.project4148;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project4148.entities.foramThread;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ThreadListAdapter extends RecyclerView.Adapter<ThreadListAdapter.myviewholder> {

    Context parentcontext;
    public ArrayList<foramThread> threadlist;

    public ThreadListAdapter(Context parentcontext) {
        this.parentcontext = parentcontext;
        threadlist = new ArrayList<>();
    }

    @NonNull
    @Override
    public ThreadListAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parentcontext).inflate(R.layout.custom_thread,parent,false);
        myviewholder holder = new myviewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ThreadListAdapter.myviewholder holder, int position) {
        holder.tvhead.setText(threadlist.get(position).getTitle());
        holder.tvdes.setText(threadlist.get(position).getDestination());
        holder.tvthreadposter.setText("Posted By : " + threadlist.get(position).getPoster());
        holder.tvthreaddetail.setText(threadlist.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return threadlist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{

        TextView tvhead;
        TextView tvdes;
        TextView tvthreadposter;
        TextView tvthreaddetail;
        FloatingActionButton openbtn;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            tvhead = itemView.findViewById(R.id.tvhead_thread_list);
            tvdes = itemView.findViewById(R.id.tvdes_thread_list);
            tvthreadposter = itemView.findViewById(R.id.tvposter_thread_list);
            tvthreaddetail = itemView.findViewById(R.id.tvdetail_thread_list);

            openbtn = itemView.findViewById(R.id.openthreadbtn_thread_list);
            openbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(parentcontext, ThreadDetailsActivity.class);
                    intent.putExtra("position",getPosition());
                    parentcontext.startActivity(intent);
                }
            });
        }
    }
}
