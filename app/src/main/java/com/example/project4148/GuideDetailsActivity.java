package com.example.project4148;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
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
    TextView tvdes,tvname,tvlan,tvareas,tvcontent1,tvcontent2;
    ImageView ivphoto;
    FirebaseDatabase db;
    Guide currentguide;
    FrameLayout fragmentcontainer;
    Guide_ChatFragment chatFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_details);

        id = getIntent().getStringExtra("id");
        toolbar  =findViewById(R.id.toolbar_guide_details);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = FirebaseDatabase.getInstance();

        chatFragment = new Guide_ChatFragment();
        tvdes = findViewById(R.id.tvdes_guide_details);
        tvname = findViewById(R.id.tvname_guide_details);
        tvlan = findViewById(R.id.tvlan_guide_details);
        tvareas = findViewById(R.id.tvareas_guide_details);
        ivphoto = findViewById(R.id.ivimage_guide_details);
        fragmentcontainer = findViewById(R.id.guide_chat_fragment_container);
        tvcontent1 = findViewById(R.id.tvcontent1_guide_details);
        tvcontent2 = findViewById(R.id.tvcontent2_guide_details);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_guide_details,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.addtofavmenu_guide_details:
                break;
            case R.id.chatwithguidemenu:
                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                trans.replace(R.id.guide_chat_fragment_container,chatFragment);
                trans.addToBackStack("chatfragmentadded");
                trans.commit();
                chatfragmentadded();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void chatfragmentadded() {
        tvdes.setVisibility(View.GONE);
        tvareas.setVisibility(View.GONE);
        tvlan.setVisibility(View.GONE);
        tvcontent2.setVisibility(View.GONE);
        tvcontent1.setVisibility(View.GONE);
        fragmentcontainer.setVisibility(View.VISIBLE);
    }
    public void chatfragmentremoved(){
        tvdes.setVisibility(View.VISIBLE);
        tvareas.setVisibility(View.VISIBLE);
        tvlan.setVisibility(View.VISIBLE);
        tvcontent2.setVisibility(View.VISIBLE);
        tvcontent1.setVisibility(View.VISIBLE);
        fragmentcontainer.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        final FragmentManager man = getSupportFragmentManager();
        if(man.getBackStackEntryAt(man.getBackStackEntryCount()-1).getName().equals("chatfragmentadded")) {
            chatfragmentremoved();
        }
        super.onBackPressed();
    }
}