package com.example.project4148;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project4148.entities.DestinationAbs;
import com.example.project4148.listners.destinationlistlistners;
import com.example.project4148.listners.functionfromfragmentlistner;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

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
    functionfromfragmentlistner listner;
    FloatingActionButton addtoqueuebtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_destinations,container,false);

        listner = (functionfromfragmentlistner) getActivity();

        deslistsearchView = view.findViewById(R.id.searchhomedeslist);
        destinationrecycler = view.findViewById(R.id.recyclerdestinationhome);
        filterbtn = view.findViewById(R.id.ibfilterdeshome);
        loadinganim = view.findViewById(R.id.loading_card_layout_destinations);
        selectbackbtn = view.findViewById(R.id.Select_back_btn_destination_queue);
        cbselectall = view.findViewById(R.id.cb_selectall_destination_queue);
        addtoqueuebtn = view.findViewById(R.id.addtoqueue_destination_main);

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
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                ConstraintLayout view = new ConstraintLayout(getContext());
                view = (ConstraintLayout) LayoutInflater.from(getContext()).inflate(R.layout.custom_destination_filter_dialog,view,false);
                builder.setView(view);
                final Spinner filterspinner = view.findViewById(R.id.spinner_zone_filter);
                ArrayList<String> filterlist = new ArrayList<>();
                filterlist.addAll(Arrays.asList(new String[]{"No filter","Coimbatore", "Kanyakumari", "Madurai", "Ooty", "Tirunelveli and Tuty"}));
                filterspinner.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,filterlist));

                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if(filterspinner.getSelectedItem().toString().toLowerCase().equals("no filter"))
                        {
                            updatedeslist();
                        }else {
                            updatedeslistbyzone((String) filterspinner.getSelectedItem());
                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        selectbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecttoolclosed();
            }
        });

        addtoqueuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.additemstoqueue();
            }
        });
        //cbselectall.setOn
        return view;
    }



    @Override
    public void openselecttoolbar() {
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

    private void updatedeslistbyzone(String zonequery) {
        loadinganim.setVisibility(View.VISIBLE);
        destinationlist.clear();
        DatabaseReference ref = db.getReference().child("deslistbyzone").child(zonequery);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot temp:dataSnapshot.getChildren())
                {
                    DestinationAbs tempdes = temp.getValue(DestinationAbs.class);
                    destinationlist.add(tempdes);
                }
                adapter.destinationlist.clear();
                adapter.destinationlist.addAll(destinationlist);
                adapter.notifyDataSetChanged();
                loadinganim.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                adapter.destinationlist.clear();
                destinationlist.clear();
                Toast.makeText(getContext(), "Network Error!..", Toast.LENGTH_SHORT).show();
                loadinganim.setVisibility(View.INVISIBLE);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void selecttoolclosed(){
        selectbackbtn.setVisibility(View.GONE);
        cbselectall.setVisibility(View.GONE);
        adapter.onselectlistflag=false;
        adapter.selecteddestinationlist.clear();
        updatedeslist();
    }

    @Override
    public void closeselecttoolbar() {
        selecttoolclosed();
    }

    @Override
    public void opendestinationqueue() {
        listner.replacefragment(new DestinationQueueFragment());
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.showqueuemenu:
                listner.replacefragment(new DestinationQueueFragment());
        }
        return false;
    }
}
