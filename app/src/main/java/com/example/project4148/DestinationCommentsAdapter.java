package com.example.project4148;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project4148.entities.Destination_Comments;

import java.util.ArrayList;

public class DestinationCommentsAdapter extends RecyclerView.Adapter<DestinationCommentsAdapter.myholder> {
    Context parentcontext;
    ArrayList<Destination_Comments> commentslist;

    public DestinationCommentsAdapter(Context parentcontext) {
        this.parentcontext = parentcontext;
        commentslist = new ArrayList<>();
    }

    @NonNull
    @Override
    public DestinationCommentsAdapter.myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parentcontext).inflate(R.layout.custom_destinatiion_comments,parent,false);
        myholder holder = new myholder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DestinationCommentsAdapter.myholder holder, int position) {
        holder.tvrating.setText(commentslist.get(position).getRating().toString()+".0*");
        holder.tvcomments.setText(commentslist.get(position).getComment());
        holder.tvauthor.setText(commentslist.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return commentslist.size();
    }

    class myholder extends RecyclerView.ViewHolder{

        TextView tvauthor,tvrating,tvcomments;
        public myholder(@NonNull View itemView) {
            super(itemView);
            tvauthor = itemView.findViewById(R.id.tvauthor_custom_comments_destination);
            tvcomments = itemView.findViewById(R.id.tvcomment_comment_destination);
            tvrating = itemView.findViewById(R.id.tvrating_custom_comments_destination);
        }
    }
}
