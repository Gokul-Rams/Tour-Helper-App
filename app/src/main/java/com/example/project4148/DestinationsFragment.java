package com.example.project4148;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DestinationsFragment extends Fragment {

    ArrayList<Destination> destinationlist;
    RecyclerView destinationrecycler;
    DestinationListAdapter adapter;
    androidx.appcompat.widget.SearchView deslistsearchView;
    ImageButton filterbtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_destinations,container,false);

        deslistsearchView = view.findViewById(R.id.searchhomedeslist);
        destinationrecycler = view.findViewById(R.id.recyclerdestinationhome);
        filterbtn = view.findViewById(R.id.ibfilterdeshome);

        destinationlist = new ArrayList<>();
        destinationlist.add(new Destination("TajMahal","New Delhi"," 4.5* "));
        destinationlist.add(new Destination("TajMahal","New Delhi"," 4.5* "));
        destinationlist.add(new Destination("TajMahal","New Delhi"," 4.5* "));

        adapter = new DestinationListAdapter(getContext(),destinationlist);
        destinationrecycler.setAdapter(adapter);
        destinationrecycler.setLayoutManager(new LinearLayoutManager(getContext()));

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
