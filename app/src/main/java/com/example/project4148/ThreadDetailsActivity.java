package com.example.project4148;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class ThreadDetailsActivity extends AppCompatActivity {

    ListView lvcommend;
    ArrayAdapter adap;
    Toolbar toolbar;
    FrameLayout qesholder;
    TextInputLayout tinewcommend;
    TextView tvtitle,tvposter,tvdet,tvdes;
    FloatingActionButton inbtn,addcommend;
    CardView cd;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_details);

        toolbar = findViewById(R.id.toolbar_thread_details);
        position = getIntent().getIntExtra("position",0);

        toolbar = findViewById(R.id.toolbar_thread_details);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adap = new ArrayAdapter(this,android.R.layout.simple_list_item_1,Applicationclass.threadlist.get(position).commends);
        lvcommend = findViewById(R.id.recycler_comments_destination_detail);
        qesholder = findViewById(R.id.qescontainer_thread_details);
        cd = findViewById(R.id.custom_threaddetail);

        tvtitle = findViewById(R.id.tvhead_thread_list);
        tvdes = findViewById(R.id.tvdes_thread_list);
        tvposter = findViewById(R.id.tvposter_thread_list);
        tvdet = findViewById(R.id.tvdetail_thread_list);
        inbtn = findViewById(R.id.openthreadbtn_thread_list);
        inbtn.setVisibility(View.GONE);
        addcommend = findViewById(R.id.addcommendbtn_thread_details);
        tinewcommend = findViewById(R.id.ticommend_thread_details);
        lvcommend = findViewById(R.id.list_commends_thread_details);

        tvtitle.setText(Applicationclass.threadlist.get(position).getTitle());
        tvdes.setText(Applicationclass.threadlist.get(position).getDestination());
        tvposter.setText(Applicationclass.threadlist.get(position).getPoster());
        tvdet.setText(Applicationclass.threadlist.get(position).getDescription());

        lvcommend.setAdapter(adap);
        addcommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Applicationclass.threadlist.get(position).commends.add(tinewcommend.getEditText().getText().toString());
                adap.notifyDataSetChanged();
            }
        });
    }
}