package com.example.project4148;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project4148.entities.DestinationAbs;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FinalDestinationActivity extends AppCompatActivity {
    FirebaseDatabase db;
    FirebaseUser user;
    Button back;
    String places_names,places;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = FirebaseDatabase.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        places_names = "";
        places = "";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finaldestinationmain);
        back = findViewById(R.id.backbuttonforfinaldestination);
        back.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                goBack();
            }
        });
        Thread t1 = new Thread(new initializePlaces());
        t1.start();
        initializeView();
        try{
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private class initializePlaces implements Runnable {
        public void run(){
            DatabaseReference ref = db.getReference().child("destinationqueue").child(user.getUid());
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    places_names="";
                    places="";
                    for(DataSnapshot temp:dataSnapshot.getChildren()){
                        DestinationAbs tempobj = temp.getValue(DestinationAbs.class);
                        assert tempobj != null;
                        tempobj.isselected=false;
                        places_names+=","+tempobj.getTitle();
                        places+=";"+tempobj.getLatLong();
                    }
                    places_names=places_names.replaceFirst(",","");
                    places=places.replaceFirst(";","");
                    initializeView();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast error = Toast.makeText(getApplicationContext(),"Error Reload",Toast.LENGTH_SHORT);
                    error.show();
                }
            });
        }
    }
    private void initializeView(){
            distance_find_matrix exq = new distance_find_matrix();
            StringBuilder index = new StringBuilder("0");
            JSONObject result = null;
            try {
                result = exq.execute(places.split(";")).get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            if(result!=null){
                try {
                    JSONArray dis=null;
                    try {
                        dis = result.getJSONArray("distance");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(dis!=null) {
                        int len = dis.length();
                        int cur_node = 0, no_jumps = 0, low_ind;
                        double lowest, data;
                        do {
                            JSONArray arr_cur = (JSONArray) dis.get(cur_node);
                            low_ind = 0;
                            lowest = Double.POSITIVE_INFINITY;
                            for (int i = 0; i < len; i++) {
                                if (String.valueOf(arr_cur.get(i)).equals("0")) {
                                    data = 0.0;
                                } else {
                                    data = (Double) arr_cur.get(i);
                                }
                                if (data != 0.0 && data < lowest) {
                                    if (!Arrays.asList(index.toString().split(",")).contains(String.valueOf(i))) {
                                        lowest = data;
                                        low_ind = i;
                                    }
                                }
                            }
                            index.append(",").append(Integer.valueOf(low_ind).toString());
                            cur_node = low_ind;
                            no_jumps++;
                        } while (no_jumps != len - 1);
                        List<String> ind = Arrays.asList(index.toString().split(","));
                        StringBuilder Places = new StringBuilder();
                        List<String> placeList = Arrays.asList(places_names.split(","));
                        for (int i = 0; i < len; i++) {
                            int j = Integer.parseInt(ind.get(i));
                            Places.append(",").append(placeList.get(j));
                        }
                        Places = new StringBuilder(Places.toString().replaceFirst(",", ""));
                        String[] PlaceLastList = Places.toString().split(",");
                        ArrayList<Final_Destination_view> destinationList = new ArrayList<>();
                        RecyclerView recycleViewFordestination = findViewById(R.id.PlacesFinalDisplayRecycler);
                        RecyclerView.LayoutManager layout0 = new LinearLayoutManager(getBaseContext());
                        for (int i = 0; i < PlaceLastList.length; i++) {
                            destinationList.add(new Final_Destination_view(String.valueOf(i + 1), PlaceLastList[i]));
                            RecyclerView.Adapter destinationadapter = new FinalDestinationListAdapter(destinationList);
                            recycleViewFordestination.setLayoutManager(layout0);
                            recycleViewFordestination.setAdapter(destinationadapter);
                        }
                    }
                    else{
                        ArrayList<Final_Destination_view> destinationList = new ArrayList<>();
                        RecyclerView recycleViewFordestination = findViewById(R.id.PlacesFinalDisplayRecycler);
                        RecyclerView.LayoutManager layout0 = new LinearLayoutManager(getBaseContext());
                        destinationList.add(new Final_Destination_view("0","To be load"));
                        RecyclerView.Adapter destinationadapter = new FinalDestinationListAdapter(destinationList);
                        recycleViewFordestination.setLayoutManager(layout0);
                        recycleViewFordestination.setAdapter(destinationadapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                ArrayList<Final_Destination_view> destinationList = new ArrayList<>();
                RecyclerView recycleViewFordestination = findViewById(R.id.PlacesFinalDisplayRecycler);
                RecyclerView.LayoutManager layout0 = new LinearLayoutManager(getBaseContext());
                destinationList.add(new Final_Destination_view("0", "Error Retry"));
                RecyclerView.Adapter destinationadapter = new FinalDestinationListAdapter(destinationList);
                recycleViewFordestination.setLayoutManager(layout0);
                recycleViewFordestination.setAdapter(destinationadapter);
            }
    }
    private void goBack(){
        Intent i = new Intent(FinalDestinationActivity.this,HomeActivity.class);
        startActivity(i);
    }
    private static class distance_find_matrix extends AsyncTask<String,Void,JSONObject> {

        @Override
        protected JSONObject doInBackground(String... strings) {
            String url  = "https://www.mapquestapi.com/directions/v2/routematrix?key=8xw81kGybKGzqcTkfE40gTx9igiGo649";
            int len = strings.length;
            StringBuilder data= new StringBuilder("{\"locations\": [");
            for(int i=0;i<len-1;i++){
                data.append("\"").append(strings[i]).append("\",");
            }
            data.append("\"").append(strings[len - 1]).append("\"").append("],\"options\": {\"allToAll\": true}}");
            try {
                URL url_base = new URL(url);
                HttpURLConnection con = (HttpURLConnection) url_base.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.setDoOutput(true);
                byte[] out = data.toString().getBytes(StandardCharsets.UTF_8);
                int length = out.length;
                con.setFixedLengthStreamingMode(length);
                try(OutputStream os = con.getOutputStream()) {
                    os.write(out);
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                try {
                    return new JSONObject(response.toString());
                } catch (Exception ignored) {
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null ;
        }
    }
}