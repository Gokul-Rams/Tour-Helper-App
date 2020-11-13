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

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    NavigationView navview;
    DrawerLayout drawer;
    ConstraintLayout navheader;
    Toolbar toolbar;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.home_toolbar);
        drawer = findViewById(R.id.homenav_drawer);
        navview = findViewById(R.id.home_navigetionniew);
        navheader = (ConstraintLayout) navview.getHeaderView(0);

        setSupportActionBar(toolbar);

        navview.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(HomeActivity.this,drawer,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction trans = fragmentManager.beginTransaction();
        HomeFragment fragtoadd = new HomeFragment();
        trans.replace(R.id.fragmentcontainer,fragtoadd);
        trans.commit();
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