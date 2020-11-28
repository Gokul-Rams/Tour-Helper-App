package com.example.project4148;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FinalDestinationListAdapter extends RecyclerView.Adapter<FinalDestinationListAdapter.DestinationViewHolder> {
    private final ArrayList<Final_Destination_view> Destinationlist;

    @NonNull
    @Override
    public DestinationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.individual_destination, parent, false);

        return new DestinationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DestinationViewHolder holder, int position) {
        Final_Destination_view listitem = Destinationlist.get(position);
        holder.getNo().setText(listitem.getNo());
        holder.getDestinationName().setText(listitem.getDestination());
    }

    @Override
    public int getItemCount() {
        return Destinationlist.size();
    }

    public static class DestinationViewHolder extends RecyclerView.ViewHolder{
        public TextView no;
        public TextView DestinationName;
        public DestinationViewHolder(@NonNull View itemView) {
            super(itemView);
            no = itemView.findViewById(R.id.indexOfDestination);
            DestinationName = itemView.findViewById(R.id.DestinationName);
        }
        public TextView getNo(){
            return no;
        }
        public TextView getDestinationName(){
            return DestinationName;
        }
    }
    public FinalDestinationListAdapter(ArrayList<Final_Destination_view> list){
        Destinationlist=list;
    }

}
