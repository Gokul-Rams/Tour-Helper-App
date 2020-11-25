package com.example.project4148;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project4148.entities.Destination;
import com.example.project4148.entities.DestinationAbs;
import com.example.project4148.entities.Destination_Comments;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DestinationDetailActivity extends AppCompatActivity {

    Toolbar toolbar;
    String desname,zone;
    TextView tvname,tvzone,tvrating,tvdes;
    ImageView ivdesimage;
    RecyclerView recycler_commends;
    CardView loadinganim;
    FirebaseDatabase db;
    Destination current_destination;
    DestinationCommentsAdapter adapter;
    ArrayList<Destination_Comments> commentslist;
    FloatingActionButton addtoqueuebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_detail);

        toolbar = findViewById(R.id.toolbar_destination_detail);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        desname = getIntent().getStringExtra("destination_name");
        zone = getIntent().getStringExtra("zone");
        getSupportActionBar().setTitle(desname);

        tvname = findViewById(R.id.tvdesname_destination_detail);
        tvzone = findViewById(R.id.tvzonename_destination_detail);
        tvrating = findViewById(R.id.tvrating_destination_detail);
        tvdes = findViewById(R.id.tvdesdescription_destination_detail);
        addtoqueuebtn = findViewById(R.id.add_to_queuebtn_destination_detail);

        ivdesimage = findViewById(R.id.ivdesimage_destinatuon_detail);
        recycler_commends = findViewById(R.id.recycler_comments_destination_detail);
        loadinganim = findViewById(R.id.loading_card_layout_destinations_detail);

        adapter = new DestinationCommentsAdapter(this);

        commentslist = new ArrayList<>();
        recycler_commends.setAdapter(adapter);
        recycler_commends.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseDatabase.getInstance();

        updatedata();

        addtoqueuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adddestinationtoqueue();
            }
        });
    }

    private void adddestinationtoqueue() {
        loadinganim.setVisibility(View.VISIBLE);
        final DatabaseReference ref = db.getReference().child("destinationqueue").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(desname);
        DatabaseReference absref = db.getReference().child("zonelist").child(desname);
        absref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DestinationAbs tempobj;
                tempobj = dataSnapshot.getValue(DestinationAbs.class);
                ref.setValue(tempobj).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(DestinationDetailActivity.this, "Sucessful", Toast.LENGTH_SHORT).show();
                            loadinganim.setVisibility(View.INVISIBLE);
                        }
                        else {
                            Toast.makeText(DestinationDetailActivity.this, "Failed Internet Trouble", Toast.LENGTH_SHORT).show();
                            loadinganim.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DestinationDetailActivity.this, "Failed Internet trouble", Toast.LENGTH_SHORT).show();
                loadinganim.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void updatedata() {
        loadinganim.setVisibility(View.VISIBLE);
        DatabaseReference ref = db.getReference().child("Zone").child(zone);
        ref.child(desname).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                current_destination = dataSnapshot.getValue(Destination.class);
                tvname.setText(current_destination.getTitle());
                tvzone.setText(zone);
                tvdes.setText(current_destination.getDescription());
                tvrating.setText(current_destination.getTotal_rating());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DestinationDetailActivity.this, "Failed Internet Trouble..", Toast.LENGTH_SHORT).show();
            }
        });
        ref.child(desname).child("comments").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentslist.clear();
                for(DataSnapshot temp:dataSnapshot.getChildren())
                {
                    Destination_Comments tempobj = temp.getValue(Destination_Comments.class);
                    commentslist.add(tempobj);
                }
                adapter.commentslist.clear();
                adapter.commentslist.addAll(commentslist);
                adapter.notifyDataSetChanged();
                loadinganim.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DestinationDetailActivity.this, "Failed Internet trouble", Toast.LENGTH_SHORT).show();
                loadinganim.setVisibility(View.INVISIBLE);
            }
        });
    }
}