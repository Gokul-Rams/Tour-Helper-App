package com.example.project4148;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project4148.entities.DestinationAbs;
import com.example.project4148.listners.destinationlistlistners;
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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_destination_queue,container,false);

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

        return view;
    }

    private void updatedatalist() {
        loading_card_view.setVisibility(View.VISIBLE);
        DatabaseReference ref = db.getReference().child("destinationqueue").child(user.getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                loading_card_view.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void iteminlistselected() {

    }

    @Override
    public void itemsinlistselectionexits() {

    }
}
