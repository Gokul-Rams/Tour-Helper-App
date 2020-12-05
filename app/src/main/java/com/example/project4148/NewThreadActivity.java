package com.example.project4148;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project4148.entities.foramThread;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

public class NewThreadActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText ettitle,etddestination;
    TextInputLayout tidetail;
    FloatingActionButton donebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_thread);

        toolbar = findViewById(R.id.toolbar_new_thread);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ettitle = findViewById(R.id.ettitle_new_thread);
        etddestination = findViewById(R.id.etdestination_new_thread);
        tidetail = findViewById(R.id.tidetail_new_thread);

        donebtn = findViewById(R.id.donebtn_new_thread);

        donebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Applicationclass.threadlist.add(new foramThread(ettitle.getText().toString(),etddestination.getText().toString(),Applicationclass.currentappuser.getName(),tidetail.getEditText().getText().toString()));
                finish();
            }
        });
    }
}