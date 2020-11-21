package com.example.project4148;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project4148.entities.Guide;
import com.example.project4148.entities.GuideAbs;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class GuideFragment extends Fragment {

    RecyclerView guiderecycler;
    CardView loadinganim;
    GuideListAdapter adapter;
    ArrayList<GuideAbs> guidelist;
    FirebaseDatabase db;
    SearchView searchView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide,container,false);

        guiderecycler = view.findViewById(R.id.recycler_guidemain);
        loadinganim = view.findViewById(R.id.loading_card_layout_guide);
        searchView = view.findViewById(R.id.search_guide_main);
        db = FirebaseDatabase.getInstance();

        loadinganim.setVisibility(View.INVISIBLE);

        guidelist  =new ArrayList();
        adapter = new GuideListAdapter(getContext());

        guiderecycler.setAdapter(adapter);
        guiderecycler.setLayoutManager(new GridLayoutManager(getContext(),2));

        loadguidelist();

        searchView.setQueryHint("Search Places..!");
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.guidelist.clear();
                adapter.notifyDataSetChanged();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadguidelistbyarea(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                loadguidelist();
                return false;
            }
        });

        return view;
    }

    public void loadguidelist()
    {
        loadinganim.setVisibility(View.VISIBLE);
        DatabaseReference ref = db.getReference().child("guidelist");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                guidelist.clear();
                for(DataSnapshot temp:dataSnapshot.getChildren())
                {
                    GuideAbs temptolist = (GuideAbs) temp.getValue(GuideAbs.class);
                    guidelist.add(temptolist);
                }
                adapter.guidelist.clear();
                adapter.guidelist.addAll(guidelist);
                adapter.notifyDataSetChanged();
                loadinganim.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loadinganim.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void loadguidelistbyarea(final String area)
    {
        loadinganim.setVisibility(View.VISIBLE);
        DatabaseReference ref = db.getReference().child("guide_list_by_area");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                guidelist.clear();
                for(DataSnapshot tempsnopshot:dataSnapshot.getChildren())
                {
                    if(tempsnopshot.getKey().toLowerCase().equals(area.toLowerCase()))
                    {
                        for(DataSnapshot tempdatasnopshot2:tempsnopshot.getChildren())
                        {
                            guidelist.add((GuideAbs) tempdatasnopshot2.getValue(GuideAbs.class));
                        }
                        break;
                    }
                }
                adapter.guidelist.clear();
                adapter.guidelist.addAll(guidelist);
                adapter.notifyDataSetChanged();
                loadinganim.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loadinganim.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Failed Check Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
