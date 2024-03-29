package com.example.project4148;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project4148.entities.DestinationAbs;
import com.example.project4148.listners.destinationlistlistners;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DestinationQueueFragment extends Fragment implements destinationlistlistners {

    RecyclerView recyclerdestinationqueue;
    DestinationListAdapter adapter;
    ArrayList<DestinationAbs> destinationlist;
    CardView loading_card_view;
    FirebaseDatabase db;
    FirebaseUser user;
    ImageButton deletebtn,selectbackbtn;
    CheckBox cb_selectall;
    ImageButton selectbtn;
    ExtendedFloatingActionButton find_route;
    TextView tvtitle;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_destination_queue,container,false);

        deletebtn = view.findViewById(R.id.ib_deletebtn_destination_queue);
        selectbackbtn = view.findViewById(R.id.Select_back_btn_destination_queue);
        cb_selectall = view.findViewById(R.id.cb_selectall_destination_queue);
        selectbtn = view.findViewById(R.id.select_btn_destination_queue);
        tvtitle = view.findViewById(R.id.tvtitle_destination_queue);
        find_route =view.findViewById(R.id.btnfindroute);

        recyclerdestinationqueue = view.findViewById(R.id.recycler_destination_queue);
        loading_card_view = view.findViewById(R.id.loading_card_layout_destinations_queue);
        loading_card_view.setVisibility(View.INVISIBLE);

        db = FirebaseDatabase.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        adapter = new DestinationListAdapter(getContext(),this);
        destinationlist = new ArrayList<>();

        recyclerdestinationqueue.setAdapter(adapter);
        recyclerdestinationqueue.setLayoutManager(new LinearLayoutManager(getContext()));
        updatedatalist();

        find_route.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openFinalRouteActivity();
            }
        } );

        selectbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecttoolbarclosed();
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.deletebtnclicked();
            }
        });

        selectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openselecttoolbar();
                adapter.onselectlistflag=true;
            }
        });

        return view;
    }

    private void openFinalRouteActivity(){
        if(destinationlist.isEmpty()||destinationlist.size()<=2){
            Toast tos = Toast.makeText(getContext(),"The list is less than 2!",Toast.LENGTH_LONG);
            tos.show();
        }
        else{
            System.out.println(destinationlist.size());
            Intent i = new Intent(getContext(),FinalDestinationActivity.class);
            startActivity(i);
        }
    }

    private void updatedatalist() {
        loading_card_view.setVisibility(View.VISIBLE);
        DatabaseReference ref = db.getReference().child("destinationqueue").child(user.getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                destinationlist.clear();
                for(DataSnapshot temp:dataSnapshot.getChildren()){
                    DestinationAbs tempobj = temp.getValue(DestinationAbs.class);
                    tempobj.isselected=false;
                    destinationlist.add(tempobj);
                }
                adapter.destinationlist.clear();
                adapter.destinationlist.addAll(destinationlist);
                adapter.notifyDataSetChanged();
                loading_card_view.setVisibility(View.INVISIBLE);
                if(adapter.destinationlist.isEmpty())
                {
                    tvtitle.setText("Destination queue Empty");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                loading_card_view.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void openselecttoolbar() {
        deletebtn.setVisibility(View.VISIBLE);
        cb_selectall.setVisibility(View.VISIBLE);
        selectbackbtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void closeselecttoolbar() {
        selecttoolbarclosed();
    }

    @Override
    public void opendestinationqueue() {

    }

    public void selecttoolbarclosed()
    {
        deletebtn.setVisibility(View.GONE);
        cb_selectall.setVisibility(View.GONE);
        selectbackbtn.setVisibility(View.GONE);
        adapter.onselectlistflag=false;
        adapter.selecteddestinationlist.clear();
        updatedatalist();
    }

}
