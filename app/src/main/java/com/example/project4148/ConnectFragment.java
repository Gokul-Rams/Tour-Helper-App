package com.example.project4148;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project4148.entities.foramThread;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class ConnectFragment extends Fragment {

    RecyclerView rectclerconnect;
    ThreadListAdapter adap;
    ExtendedFloatingActionButton newthreadbtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_connect,container,false);

        rectclerconnect = view.findViewById(R.id.connect_thread_recycler);
        newthreadbtn = view.findViewById(R.id.newthreadbtn_thread_list);

        adap = new ThreadListAdapter(getContext());
        rectclerconnect.setAdapter(adap);
        rectclerconnect.setLayoutManager(new LinearLayoutManager(getContext()));
        Applicationclass.threadlist.add(new foramThread("How good is Tajmahal","Agra","Gokul","Looking ahead to visit agra how good is the destination in agra"));
        adap.threadlist.addAll(Applicationclass.threadlist);
        adap.notifyDataSetChanged();

        newthreadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),NewThreadActivity.class);
                startActivity(intent);
            }
        });

        return  view;
    }

    public void menuitemclicked(MenuItem item)
    {

    }

    @Override
    public void onStart() {
        adap.threadlist.clear();
        adap.threadlist.addAll(Applicationclass.threadlist);
        adap.notifyDataSetChanged();
        super.onStart();
    }
}
