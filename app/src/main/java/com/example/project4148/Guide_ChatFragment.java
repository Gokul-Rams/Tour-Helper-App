package com.example.project4148;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project4148.entities.GuideChat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Guide_ChatFragment extends Fragment {

    GuideChatAdapter adapter;
    RecyclerView chatrecycler;
    EditText etmes;
    FloatingActionButton sendbtn;
    String guideid;
    FirebaseDatabase db;
    String chatroot;

    public Guide_ChatFragment(String guideid) {
        this.guideid = guideid;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guidechat,container,false);

        etmes = view.findViewById(R.id.etmes_guide_chat);
        sendbtn = view.findViewById(R.id.sendbtn_guide_chat);

        db = FirebaseDatabase.getInstance();
        updateenvironment();
        
        chatrecycler = view.findViewById(R.id.guide_chat_recycler);
        adapter = new GuideChatAdapter(getContext());
        adapter.chatlist.add(new GuideChat("hii",true));
        adapter.chatlist.add(new GuideChat("hii",false));

        chatrecycler.setAdapter(adapter);
        LinearLayoutManager man = new LinearLayoutManager(getContext());
        chatrecycler.setLayoutManager(man);

        Toast.makeText(getContext(), Applicationclass.currentappuser.uid + " "+guideid, Toast.LENGTH_SHORT).show();
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etmes.getText().toString().isEmpty()){
                    sendmessage();
                }
            }
        });

        return view;
    }

    private void updateenvironment() {
    }

    private void sendmessage() {
        DatabaseReference ref = db.getReference();
    }
}
