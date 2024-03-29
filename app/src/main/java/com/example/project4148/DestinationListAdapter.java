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

import com.example.project4148.entities.DestinationAbs;
import com.example.project4148.listners.destinationlistlistners;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class DestinationListAdapter extends RecyclerView.Adapter<DestinationListAdapter.myviewholder> {

    Context parentcontext;
    public ArrayList<DestinationAbs> destinationlist,selecteddestinationlist;
    destinationlistlistners listner;
    Boolean onselectlistflag;
    FirebaseDatabase db;
    boolean sucessflag;
    Loading_animation anim;


    public DestinationListAdapter(Context parentcontext, Fragment fragmentitsadded) {
        this.parentcontext = parentcontext;
        destinationlist = new ArrayList<>();
        listner = (destinationlistlistners) fragmentitsadded;
        onselectlistflag = false;
        selecteddestinationlist = new ArrayList<>();
        db = FirebaseDatabase.getInstance();
        anim = new Loading_animation(parentcontext);
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
        System.out.println(destinationlist.get(position).getLatLong());
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
                    else {
                        Intent intent = new Intent(parentcontext,DestinationDetailActivity.class);
                        intent.putExtra("destination_name",destinationlist.get(getPosition()).getTitle());
                        intent.putExtra("zone",destinationlist.get(getPosition()).getZone());
                        parentcontext.startActivity(intent);
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    destinationlist.get(getPosition()).isselected=true;
                    selecteddestinationlist.add(destinationlist.get(getPosition()));
                    notifyItemChanged(getPosition());
                    listner.openselecttoolbar();
                    onselectlistflag=true;
                    return false;
                }
            });
        }
    }

    //used only with destination home frag
    public void additemstoqueue() {
        if(selecteddestinationlist.isEmpty())
        {
            Toast.makeText(parentcontext, "select destnations to add to queue", Toast.LENGTH_SHORT).show();
        }
        else{
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseUser user = auth.getCurrentUser();
            anim.startanimation();
            DatabaseReference ref = db.getReference().child("destinationqueue").child(user.getUid());
            /*HashMap<String,DestinationAbs> map = new HashMap();
            for(int i=0;i<selecteddestinationlist.size();i++)
            {
                map.put(selecteddestinationlist.get(i).getTitle(),selecteddestinationlist.get(i));
            }
            System.out.println(map.get(selecteddestinationlist.get(0).getTitle()));
            ref.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        listner.closeselecttoolbar();
                        Toast.makeText(parentcontext, "Sucessful", Toast.LENGTH_SHORT).show();
                        anim.stopanimation();
                        listner.opendestinationqueue();
                    }
                    else {
                        listner.closeselecttoolbar();
                        Toast.makeText(parentcontext, "Failure", Toast.LENGTH_SHORT).show();
                        anim.stopanimation();
                    }
                }
            });*/

            final Integer[] insertcount = {0};
            final boolean[] flag = {false};
            for(int i=0;i<selecteddestinationlist.size();i++)
            {
                ref.child(selecteddestinationlist.get(i).getTitle()).setValue(selecteddestinationlist.get(i)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            insertcount[0] = insertcount[0] +1;
                        }
                        else {
                            flag[0] = true;
                        }
                        if(insertcount[0]>=selecteddestinationlist.size() || flag[0] == true){
                            if(flag[0] == true){
                                Toast.makeText(parentcontext, "Failed Internet trouble", Toast.LENGTH_SHORT).show();
                                anim.stopanimation();
                            }
                            else {
                                Toast.makeText(parentcontext, "items added to queue", Toast.LENGTH_SHORT).show();
                                anim.stopanimation();
                                listner.closeselecttoolbar();
                                listner.opendestinationqueue();
                            }
                        }
                    }
                });
            }
        }
    }

    //use only on destination queue fragment
    public void deletebtnclicked() {
        anim.startanimation();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        DatabaseReference ref = db.getReference("destinationqueue").child(user.getUid());
        sucessflag=true;
        for(int i=0;i<selecteddestinationlist.size();i++)
        {
            ref.child(selecteddestinationlist.get(i).getTitle()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        sucessflag =true;
                    }
                    else {
                        sucessflag=false;
                    }
                }
            });
            if(sucessflag == false){
                anim.stopanimation();
                Toast.makeText(parentcontext, "Internet trouble..", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        if(sucessflag == true){
            anim.stopanimation();
            Toast.makeText(parentcontext, "Items deleted from queue", Toast.LENGTH_SHORT).show();
            listner.closeselecttoolbar();
        }
    }

}
