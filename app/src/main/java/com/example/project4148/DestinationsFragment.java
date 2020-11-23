package com.example.project4148;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project4148.entities.Destination;
import com.example.project4148.entities.DestinationAbs;
import com.example.project4148.listners.destinationlistlistners;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DestinationsFragment extends Fragment implements destinationlistlistners, Toolbar.OnMenuItemClickListener {

    ArrayList<DestinationAbs> destinationlist;
    RecyclerView destinationrecycler;
    DestinationListAdapter adapter;
    CardView loadinganim;
    androidx.appcompat.widget.SearchView deslistsearchView;
    ImageButton filterbtn;
    FirebaseDatabase db;
    ImageButton selectbackbtn;
    CheckBox cbselectall;
    androidx.appcompat.widget.Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_destinations,container,false);

        deslistsearchView = view.findViewById(R.id.searchhomedeslist);
        destinationrecycler = view.findViewById(R.id.recyclerdestinationhome);
        filterbtn = view.findViewById(R.id.ibfilterdeshome);
        loadinganim = view.findViewById(R.id.loading_card_layout_destinations);
        selectbackbtn = view.findViewById(R.id.Select_back_btn_destination_home);
        cbselectall = view.findViewById(R.id.cb_selectall_destination_main);

        toolbar = getActivity().findViewById(R.id.home_toolbar);
        toolbar.setOnMenuItemClickListener(this);


        db = FirebaseDatabase.getInstance();

        destinationlist = new ArrayList<>();

        adapter = new DestinationListAdapter(getContext(),this);
        destinationrecycler.setAdapter(adapter);
        destinationrecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        updatedeslist();

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

        selectbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectbackbtnclicked();
            }
        });

        //cbselectall.setOn
        return view;
    }

    @Override
    public void iteminlistselected() {
        selectbackbtn.setVisibility(View.VISIBLE);
        cbselectall.setVisibility(View.VISIBLE);
    }

    public void updatedeslist()
    {
        loadinganim.setVisibility(View.VISIBLE);
        DatabaseReference ref = db.getReference().child("zonelist");
        destinationlist.clear();
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
                loadinganim.setVisibility(View.INVISIBLE);
                adapter.destinationlist.clear();
                destinationlist.clear();
                Toast.makeText(getContext(), "Intrnet trouble", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void selectbackbtnclicked(){
        selectbackbtn.setVisibility(View.GONE);
        cbselectall.setVisibility(View.GONE);
        adapter.onselectlistflag=false;
        System.out.println(adapter.selecteddestinationlist.size());
        adapter.selecteddestinationlist.clear();
        updatedeslist();
    }


    public void menuitemclicked(MenuItem item)
    {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.addtoqueuemenu:
                adapter.addtoqueuemenuclicked();
        }
        return false;
    }
}
