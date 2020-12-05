package com.example.project4148;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ThreadDetailsActivity extends AppCompatActivity {

    ListView lvcommend;
    ArrayList<String> commendslist;
    Toolbar toolbar;
    FrameLayout qesholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_details);

        toolbar = findViewById(R.id.toolbar_thread_details);

        toolbar = findViewById(R.id.toolbar_new_thread);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvcommend = findViewById(R.id.recycler_comments_destination_detail);
        qesholder = findViewById(R.id.qescontainer_thread_details);

    }
}