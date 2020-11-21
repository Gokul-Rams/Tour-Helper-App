package com.example.project4148;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project4148.entities.Destination;
import com.example.project4148.entities.DestinationAbs;

import java.util.ArrayList;

public class DestinationListAdapter extends RecyclerView.Adapter<DestinationListAdapter.myviewholder> {

    Context parentcontext;
    public ArrayList<DestinationAbs> destinationlist;

    public DestinationListAdapter(Context parentcontext) {
        this.parentcontext = parentcontext;
        destinationlist = new ArrayList<>();
    }

    @NonNull
    @Override
    public DestinationListAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parentcontext).inflate(R.layout.custom_destinationview,parent,false);
        myviewholder holder = new myviewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DestinationListAdapter.myviewholder holder, int position) {
        holder.tvdesname.setText(destinationlist.get(position).getTitle());
        holder.tvrating.setText(destinationlist.get(position).getTotal_rating().toString()+"*");
        holder.tvzonename.setText(destinationlist.get(position).getZone());
    }

    @Override
    public int getItemCount() {
        return destinationlist.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView ivdesimage;
        TextView tvdesname,tvzonename,tvrating;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            ivdesimage = itemView.findViewById(R.id.ivlistdesimage);
            tvdesname = itemView.findViewById(R.id.tvlistdesname);
            tvzonename = itemView.findViewById(R.id.tvlistzonename);
            tvrating = itemView.findViewById(R.id.tvlistrating);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
