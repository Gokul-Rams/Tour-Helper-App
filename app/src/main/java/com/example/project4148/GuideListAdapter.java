package com.example.project4148;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project4148.entities.Guide;
import com.example.project4148.entities.GuideAbs;

import java.util.ArrayList;

public class GuideListAdapter extends RecyclerView.Adapter<GuideListAdapter.myviewholder> {

    ArrayList<GuideAbs> guidelist;
    Context parentcontext;

    public GuideListAdapter(Context parentcontext) {
        guidelist = new ArrayList<>();
        this.parentcontext = parentcontext;
    }

    @NonNull
    @Override
    public GuideListAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parentcontext).inflate(R.layout.custom_guideview,parent,false);
        myviewholder holder = new myviewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GuideListAdapter.myviewholder holder, int position) {
        holder.tvname.setText(guidelist.get(position).name);
        holder.tvrating.setText(guidelist.get(position).getRating());
        StringBuilder builder = new StringBuilder();
        ArrayList<String> temp = new ArrayList<>();
        temp = guidelist.get(position).getAreas();
        for(int i=0;i<temp.size();i++)
        {
            builder.append(temp.get(i) + "\n");
        }
        holder.tvareas.setText(builder);
    }

    @Override
    public int getItemCount() {
        return guidelist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView ivphoto;
        TextView tvname,tvrating,tvareas;
        CardView card;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            ivphoto = itemView.findViewById(R.id.ivguideimage_guidelist);
            tvname = itemView.findViewById(R.id.tvguidename_guidelist);
            tvareas = itemView.findViewById(R.id.tvareas_guidelist);
            tvrating = itemView.findViewById(R.id.tvrating_guidelist);
            card = itemView.findViewById(R.id.custom_guideview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(parentcontext,GuideDetailsActivity.class);
                    intent.putExtra("id",guidelist.get(getPosition()).key);
                    parentcontext.startActivity(intent);
                }
            });
        }
    }
}
