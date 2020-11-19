package com.example.project4148;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class GuidesigninActivity extends AppCompatActivity {

    Spinner lanspinner;
    Toolbar toolbar;
    TextInputLayout tides;
    RadioButton rbindudvial,rborg;
    ListView lvareas,lvlanguages;
    EditText etarea;
    ArrayList<String> lanlist,arealist,spinnerlanlist;
    ArrayAdapter<String> lanadap,areaadap,spinneradap;
    FirebaseDatabase db;
    FirebaseAuth auth;
    FirebaseUser user;
    Loading_animation anim;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidesignin);

        toolbar = findViewById(R.id.toolbar_guidesignin);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Guide Signin");

        lanspinner = findViewById(R.id.spinnerguidesignuplan);
        tides = findViewById(R.id.tiguidesigndes);
        rborg = findViewById(R.id.rbguidesiguporg);
        rbindudvial = findViewById(R.id.rbguidesignupindu);
        lvareas = findViewById(R.id.listguidesignupareas);
        lvlanguages = findViewById(R.id.listguidesignuplan);
        etarea = findViewById(R.id.etguidesignupareasserved);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseDatabase.getInstance();
        anim = new Loading_animation(this);

        lanlist = new ArrayList<>();
        arealist = new ArrayList<>();
        spinnerlanlist = new ArrayList<>();
        spinnerlanlist.add("English");
        spinnerlanlist.add("Tamil");
        spinnerlanlist.add("French");
        spinnerlanlist.add("Hindi");

        areaadap = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arealist);
        lvareas.setAdapter(areaadap);

        lanadap = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,lanlist);
        lvlanguages.setAdapter(lanadap);

        spinneradap = new ArrayAdapter<>(this,android.R.layout.select_dialog_item,spinnerlanlist);
        lanspinner.setAdapter(spinneradap);

        lanlist.clear();
        lanadap.notifyDataSetChanged();

        lanspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lanlist.add(spinnerlanlist.get(position));
                lanadap.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void addbtnclicked(View view){
        if(!etarea.getText().toString().trim().isEmpty())
        {
            lvareas.setMinimumHeight(400);
            arealist.add(etarea.getText().toString().trim());
            areaadap.notifyDataSetChanged();
        }
    }

    public void donebtnclicked(View view)
    {
        anim.startanimation();
        DatabaseReference ref = db.getReference().child("userdetails");
        ref.child(user.getUid()).child("isguide").setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                anim.stopanimation();
                if(task.isSuccessful())
                {
                    Toast.makeText(GuidesigninActivity.this, "Sucessful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(GuidesigninActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(GuidesigninActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}