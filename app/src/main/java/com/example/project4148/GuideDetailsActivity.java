package com.example.project4148;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project4148.entities.Guide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GuideDetailsActivity extends AppCompatActivity {

    Toolbar toolbar;
    String id;
    TextView tvdes,tvname,tvlan,tvareas;
    ImageView ivphoto;
    FirebaseDatabase db;
    Guide currentguide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_details);

        id = getIntent().getStringExtra("id");
        toolbar  =findViewById(R.id.toolbar_guide_details);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = FirebaseDatabase.getInstance();

        tvdes = findViewById(R.id.tvdes_guide_details);
        tvname = findViewById(R.id.tvname_guide_details);
        tvlan = findViewById(R.id.tvlan_guide_details);
        tvareas = findViewById(R.id.tvareas_guide_details);
        ivphoto = findViewById(R.id.ivimage_guide_details);

        DatabaseReference ref = db.getReference().child("guide");

        ref.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentguide = dataSnapshot.getValue(Guide.class);
                tvname.setText(currentguide.getName());
                tvdes.setText(currentguide.getDescription());
                StringBuilder builder = new StringBuilder();
                for(int i=0;i<currentguide.getLang().size();i++)
                {
                    builder.append(currentguide.getLang().get(i) + "  ");
                }
                tvlan.setText(builder);
                builder = new StringBuilder();
                for(int i=0;i<currentguide.getAreas().size();i++)
                {
                    builder.append(currentguide.getAreas().get(i) + "  ");
                }
                tvareas.setText(builder);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(GuideDetailsActivity.this, "check your Internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}