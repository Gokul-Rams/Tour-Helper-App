package com.example.project4148;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Trace;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    NavigationView navview;
    DrawerLayout drawer;
    FrameLayout navheader;
    Toolbar toolbar;
    FragmentManager fragmentManager;
    FirebaseAuth auth;
    TextView tvheaderusername,tvheadercurrentlocation,addeditprofile;
    Button btnsignout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if(user==null){
            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            toolbar = findViewById(R.id.home_toolbar);
            drawer = findViewById(R.id.homenav_drawer);
            navview = findViewById(R.id.home_navigetionniew);
            navheader = (FrameLayout) navview.getHeaderView(0);
            tvheaderusername = navheader.findViewById(R.id.tvheaderusername);
            tvheadercurrentlocation = navheader.findViewById(R.id.tvheadercurrentlocation);
            btnsignout = navheader.findViewById(R.id.btnsignout);
            addeditprofile = navheader.findViewById(R.id.addeditprofile);

            setSupportActionBar(toolbar);

            navview.setNavigationItemSelectedListener(this);

            ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(HomeActivity.this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
            drawer.addDrawerListener(toogle);
            toogle.syncState();

            fragmentManager = getSupportFragmentManager();
            FragmentTransaction trans = fragmentManager.beginTransaction();
            HomeFragment fragtoadd = new HomeFragment();
            trans.replace(R.id.fragmentcontainer, fragtoadd);
            trans.commit();

            tvheaderusername.setText("Hello " + user.getEmail());

            btnsignout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    auth.signOut();
                    Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            addeditprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this,AdddetailsActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction transaction;
        Intent intent;
        switch (item.getItemId())
        {
            case R.id.homenavoption:
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragmentcontainer,new HomeFragment());
                transaction.commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.destinationsnavoption:
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragmentcontainer,new DestinationsFragment());
                transaction.commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.foodsnavoption:
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragmentcontainer,new FoodsFragment());
                transaction.commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.guidenavoption:
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragmentcontainer,new GuideFragment());
                transaction.commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.helpnav_option:
                intent = new Intent(HomeActivity.this,HelpActivity.class);
                startActivity(intent);
                break;
            case R.id.settingnav_option:
                intent = new Intent(HomeActivity.this,SettingActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}