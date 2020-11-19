package com.example.project4148;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DestinationListAdapter extends RecyclerView.Adapter<DestinationListAdapter.myviewholder> {

    Context parentcontext;
    ArrayList<Destination> destinationlist;

    public DestinationListAdapter(Context parentcontext, ArrayList<Destination> destinationlistfromact) {
        this.parentcontext = parentcontext;
        destinationlist = new ArrayList<>();
        destinationlist.addAll(destinationlistfromact);
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
        holder.tvdesname.setText(destinationlist.get(position).name);
        holder.tvrating.setText(destinationlist.get(position).rating);
        holder.tvzonename.setText(destinationlist.get(position).zone);
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
        }
    }
}
