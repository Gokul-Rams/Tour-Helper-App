package com.example.project4148;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project4148.entities.CurrentUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdddetailsActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText etname,etphno;
    ExtendedFloatingActionButton btndone;
    FirebaseDatabase db;
    FirebaseAuth auth;
    Loading_animation anim;
    FirebaseUser user;
    Boolean isguideflag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddetails);

        toolbar = findViewById(R.id.adddetails_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etname = findViewById(R.id.etadddetailsname);
        etphno = findViewById(R.id.etadddetailsphno);
        btndone = findViewById(R.id.btnaddetailsdone);

        if(getIntent().getStringExtra("name")!=null)
        {
            etname.setText(getIntent().getStringExtra("name"));
            etphno.setText(getIntent().getStringExtra("phno"));
        }

        anim = new Loading_animation(this);
        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        btndone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etname.getText().toString().trim().isEmpty() || etphno.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(AdddetailsActivity.this, "Empty Feilds", Toast.LENGTH_SHORT).show();
                }
                else {
                    anim.startanimation();
                    final DatabaseReference ref = db.getReference();
                    if(Applicationclass.currentappuser == null){
                        isguideflag = false;
                        updateuset(ref);
                    }
                    else{
                        ref.child(user.getUid()).child("isguide").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                isguideflag = (Boolean) dataSnapshot.getValue();
                                updateuset(ref);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                anim.stopanimation();
                                Toast.makeText(AdddetailsActivity.this, "Failed check internet", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }

    void updateuset(DatabaseReference ref){
        CurrentUser temp = new CurrentUser(etname.getText().toString().trim(),
                user.getEmail(),
                Long.parseLong(etphno.getText().toString().trim()),
                isguideflag,user.getUid());
        ref.child("userdetails").child(user.getUid()).setValue(temp).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                anim.stopanimation();
                if(task.isSuccessful())
                {
                    Intent intent = new Intent(AdddetailsActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(AdddetailsActivity.this, "Failed check internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}