package com.example.project4148;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project4148.entities.GuideChat;

import java.util.ArrayList;

public class GuideChatAdapter extends RecyclerView.Adapter<GuideChatAdapter.myholder> {

    //view type
    //1-send chat
    //2-receive chat
    ArrayList<GuideChat> chatlist;
    Context parentcontext;

    public GuideChatAdapter(Context parentcontext) {
        this.parentcontext = parentcontext;
        chatlist = new ArrayList<>();
    }

    @NonNull
    @Override
    public GuideChatAdapter.myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == 1) {
            view = LayoutInflater.from(parentcontext).inflate(R.layout.custom_chat_send, parent, false);
        }
        else if(viewType==2){
            view = LayoutInflater.from(parentcontext).inflate(R.layout.custom_chat_received, parent, false);
        }
        return new myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuideChatAdapter.myholder holder, int position) {
        holder.tvmes.setText(chatlist.get(position).getMes());
    }

    @Override
    public int getItemCount() {
        return chatlist.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(chatlist.get(position).getSenderid().equals(Applicationclass.currentappuser.uid))
         return 1;
        else
            return 2;
    }

    class myholder extends RecyclerView.ViewHolder{

        TextView tvmes;
        public myholder(@NonNull View itemView) {
            super(itemView);

            tvmes = itemView.findViewById(R.id.tvmessage_chat_recycler);
        }
    }
}
