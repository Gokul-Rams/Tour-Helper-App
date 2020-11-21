package com.example.project4148;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project4148.entities.Destination;
import com.example.project4148.entities.DestinationAbs;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DestinationsFragment extends Fragment {

    ArrayList<DestinationAbs> destinationlist;
    RecyclerView destinationrecycler;
    DestinationListAdapter adapter;
    CardView loadinganim;
    androidx.appcompat.widget.SearchView deslistsearchView;
    ImageButton filterbtn;
    FirebaseDatabase db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_destinations,container,false);

        deslistsearchView = view.findViewById(R.id.searchhomedeslist);
        destinationrecycler = view.findViewById(R.id.recyclerdestinationhome);
        filterbtn = view.findViewById(R.id.ibfilterdeshome);
        loadinganim = view.findViewById(R.id.loading_card_layout_destinations);

        db = FirebaseDatabase.getInstance();

        destinationlist = new ArrayList<>();

        adapter = new DestinationListAdapter(getContext());
        destinationrecycler.setAdapter(adapter);
        destinationrecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        loadinganim.setVisibility(View.VISIBLE);
        DatabaseReference ref = db.getReference().child("zonelist");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot temp:dataSnapshot.getChildren())
                {
                    destinationlist.add((DestinationAbs) temp.getValue(DestinationAbs.class));
                }
                adapter.destinationlist.clear();
                adapter.destinationlist.addAll(destinationlist);
                adapter.notifyDataSetChanged();
                loadinganim.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        deslistsearchView.setQueryHint("enter places to search");
        deslistsearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        deslistsearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.destinationlist.clear();
            }
        });

        deslistsearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                adapter.destinationlist.clear();
                adapter.destinationlist.addAll(destinationlist);
                return true;
            }
        });

        filterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}
