package com.example.project4148;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project4148.entities.Destination;
import com.example.project4148.entities.DestinationAbs;
import com.example.project4148.listners.destinationlistlistners;

import java.util.ArrayList;

public class DestinationListAdapter extends RecyclerView.Adapter<DestinationListAdapter.myviewholder> {

    Context parentcontext;
    public ArrayList<DestinationAbs> destinationlist,selecteddestinationlist;
    destinationlistlistners listner;
    Boolean onselectlistflag;


    public DestinationListAdapter(Context parentcontext, Fragment fragmentitsadded) {
        this.parentcontext = parentcontext;
        destinationlist = new ArrayList<>();
        listner = (destinationlistlistners) fragmentitsadded;
        onselectlistflag = false;
        selecteddestinationlist = new ArrayList<>();
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
        if(destinationlist.get(position).isselected == true)
        {
            holder.ivselectimage.setVisibility(View.VISIBLE);
        }
        else{
            holder.ivselectimage.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return destinationlist.size();
    }


    public class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView ivdesimage,ivselectimage;
        TextView tvdesname,tvzonename,tvrating;
        public myviewholder(@NonNull final View itemView) {
            super(itemView);
            ivdesimage = itemView.findViewById(R.id.ivlistdesimage);
            tvdesname = itemView.findViewById(R.id.tvlistdesname);
            tvzonename = itemView.findViewById(R.id.tvlistzonename);
            tvrating = itemView.findViewById(R.id.tvlistrating);
            ivselectimage = itemView.findViewById(R.id.iv_select_image_destination);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //defigning for select click
                    if(onselectlistflag == true){
                        if(destinationlist.get(getPosition()).isselected == true) {
                            //the element already selected
                            destinationlist.get(getPosition()).isselected = false;
                            selecteddestinationlist.remove(destinationlist.get(getPosition()));
                            notifyItemChanged(getPosition());
                        }
                        else{
                            //element newly selected
                            destinationlist.get(getPosition()).isselected = true;
                            selecteddestinationlist.add(destinationlist.get(getPosition()));
                            notifyItemChanged(getPosition());
                        }
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    destinationlist.get(getPosition()).isselected=true;
                    selecteddestinationlist.add(destinationlist.get(getPosition()));
                    notifyItemChanged(getPosition());
                    listner.iteminlistselected();
                    onselectlistflag=true;
                    return false;
                }
            });
        }
    }

    public void addtoqueuemenuclicked() {
        if(selecteddestinationlist.isEmpty())
        {
            Toast.makeText(parentcontext, "select destnations to add to queue", Toast.LENGTH_SHORT).show();
        }
    }
}
