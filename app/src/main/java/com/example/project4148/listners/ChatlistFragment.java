package com.example.project4148.listners;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.project4148.Applicationclass;
import com.example.project4148.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatlistFragment extends Fragment {

    ListView lvchatlist;
    CardView loadinganimation;
    ArrayList<String> chatlist;
    ArrayAdapter adap;
    FirebaseDatabase db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatlist, container,false);
        lvchatlist = view.findViewById(R.id.lv_chatlist_chatlist);
        loadinganimation = view.findViewById(R.id.loading_card_layout_chatlist);
        db = FirebaseDatabase.getInstance();
        chatlist = new ArrayList<>();
        adap = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,chatlist);
        lvchatlist.setAdapter(adap);
        updateenvironment();
        return view;
    }

    private void updateenvironment() {
        DatabaseReference ref = db.getReference().child("chatlist");
        loadinganimation.setVisibility(View.INVISIBLE);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot temp:snapshot.getChildren()){
                    if(temp.getKey().contains(Applicationclass.currentappuser.uid)){
                        String key = temp.getKey();
                        String value = (String) temp.getValue();
                        int _pos = key.indexOf("_");
                        String user1 = key.substring(0,_pos);
                        String user2 = key.substring(_pos+1);
                        String oppuser,oppusername;
                        if(user1.equals(Applicationclass.currentappuser.uid)){
                            oppuser=user2;
                            oppusername = value.substring(value.indexOf("|")+1);
                        }
                        else{
                            oppuser=user1;
                            oppusername = value.substring(0,value.indexOf("|"));
                        }
                        chatlist.add(oppusername);
                        adap.notifyDataSetChanged();
                    }
                }
                loadinganimation.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();
                loadinganimation.setVisibility(View.INVISIBLE);
            }
        });
    }
}
