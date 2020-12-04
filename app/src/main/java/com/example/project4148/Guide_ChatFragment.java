package com.example.project4148;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project4148.entities.GuideChat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Guide_ChatFragment extends Fragment {

    GuideChatAdapter adapter;
    RecyclerView chatrecycler;
    EditText etmes;
    FloatingActionButton sendbtn;
    String guideid,guidename,chatvalue;
    FirebaseDatabase db;
    String chatroot;
    DatabaseReference ref;

    public Guide_ChatFragment(String guideid,String guidename) {
        this.guideid = guideid;
        this.guidename = guidename;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guidechat,container,false);

        etmes = view.findViewById(R.id.etmes_guide_chat);
        sendbtn = view.findViewById(R.id.sendbtn_guide_chat);

        chatroot = getchatroot();
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("chats").child(chatroot);
        updateenvironment();
        
        chatrecycler = view.findViewById(R.id.guide_chat_recycler);
        adapter = new GuideChatAdapter(getContext());

        chatrecycler.setAdapter(adapter);
        LinearLayoutManager man = new LinearLayoutManager(getContext());
        chatrecycler.setLayoutManager(man);

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

    private String getchatroot() {
        if(guideid.compareTo(Applicationclass.currentappuser.uid)>0)
        {
            chatvalue = guidename + "|" + Applicationclass.currentappuser.name;
            return guideid + "_" + Applicationclass.currentappuser.uid;
        }
        else {
            chatvalue =Applicationclass.currentappuser.name  + "|"  + guidename;
            return Applicationclass.currentappuser.uid + "_" + guideid;
        }
    }

    //used to update environment first and keep on updating chats
    private void updateenvironment() {
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                GuideChat tempchat = dataSnapshot.getValue(GuideChat.class);
                adapter.chatlist.add(tempchat);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendmessage() {
        final GuideChat tempchat = new GuideChat(etmes.getText().toString(),Applicationclass.currentappuser.uid,guideid,"hii");
        DatabaseReference ref1 = db.getReference().child("chatlist").child(chatroot);
        ref1.setValue(chatvalue).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    ref.push().setValue(tempchat).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                            }
                            else {
                                Toast.makeText(getContext(), "Internet trouble", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(getContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
