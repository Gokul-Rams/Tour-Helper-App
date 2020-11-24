package com.example.project4148;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project4148.entities.CurrentUser;
import com.example.project4148.listners.functionfromfragmentlistner;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity implements functionfromfragmentlistner,NavigationView.OnNavigationItemSelectedListener{

    NavigationView navview;
    DrawerLayout drawer;
    FrameLayout navheader;
    Toolbar toolbar;
    FragmentManager fragmentManager;
    FirebaseAuth auth;
    TextView tvheaderusername,tvheadercurrentlocation,addeditprofile,tvheaderisguide;
    Button btnsignout;
    FirebaseDatabase db;
    Fragment fragonscreen;
    int flagfragonscreen;
    /*flag frag on screen
    1-homefrag
    2-destination frag
    3-destination queue frag*/
    Loading_animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();
        if(user==null){
            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            //intilizing variables
            toolbar = findViewById(R.id.home_toolbar);
            drawer = findViewById(R.id.homenav_drawer);
            navview = findViewById(R.id.home_navigetionniew);
            navheader = (FrameLayout) navview.getHeaderView(0);
            tvheaderusername = navheader.findViewById(R.id.tvheaderusername);
            tvheaderisguide = navheader.findViewById(R.id.tvheaderisguide);
            tvheadercurrentlocation = navheader.findViewById(R.id.tvheadercurrentlocation);
            btnsignout = navheader.findViewById(R.id.btnsignout);
            addeditprofile = navheader.findViewById(R.id.addeditprofile);

            setSupportActionBar(toolbar);

            //setting up navigation listner
            navview.setNavigationItemSelectedListener(this);

            //setting up navigation toogle
            ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(HomeActivity.this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
            DrawerArrowDrawable toogletoset = new DrawerArrowDrawable(this);
            toogletoset.setColor(Color.WHITE);
            toogle.setDrawerArrowDrawable(toogletoset);
            drawer.addDrawerListener(toogle);
            toogle.syncState();

            //setting up home frag first
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction trans = fragmentManager.beginTransaction();
            fragonscreen = new HomeFragment();
            trans.replace(R.id.fragmentcontainer, fragonscreen);
            flagfragonscreen = 1;
            invalidateOptionsMenu();
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
                    intent.putExtra("name", Applicationclass.currentappuser.name);
                    intent.putExtra("phno", Applicationclass.currentappuser.phno.toString());
                    startActivity(intent);
                }
            });

            
            anim = new Loading_animation(this);
            //setting the user details
            db = FirebaseDatabase.getInstance();
            anim.startanimation();
            DatabaseReference ref = db.getReference().child("userdetails");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(user.getUid()))
                    {
                        assigncurrentuser(dataSnapshot.child(user.getUid()),user.getEmail());
                    }
                    else {
                        anim.stopanimation();
                        getuser();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(HomeActivity.this, "unable to get current user check Internet", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //this method is used to get the user details if not got (executed at first entry)
    private void getuser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("update user");
        builder.setMessage("uh huh update your user details");
        builder.setPositiveButton("Add detail", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(HomeActivity.this,AdddetailsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //method is uesd to asssign the current user to the application class's variable
    private void assigncurrentuser(DataSnapshot dataSnapshot,String email) {
        Applicationclass.currentappuser = new CurrentUser(dataSnapshot.child("name").getValue().toString(),
                email,
                Long.parseLong(dataSnapshot.child("phno").getValue().toString()),
                Boolean.parseBoolean(dataSnapshot.child("isguide").getValue().toString()));

        tvheaderusername.setText("Hii " + Applicationclass.currentappuser.name);
        if(Applicationclass.currentappuser.isguide==true)
        {
            tvheaderisguide.setText("( Guide )");
        }
        else {
            tvheaderisguide.setText("");
        }
        anim.stopanimation();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction transaction;
        Intent intent;
        switch (item.getItemId())
        {
            case R.id.homenavoption:
                transaction = fragmentManager.beginTransaction();
                fragonscreen = new HomeFragment();
                transaction.replace(R.id.fragmentcontainer,fragonscreen);
                flagfragonscreen = 1;
                invalidateOptionsMenu();
                transaction.commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.destinationsnavoption:
                transaction = fragmentManager.beginTransaction();
                fragonscreen = new DestinationsFragment();
                transaction.replace(R.id.fragmentcontainer,fragonscreen);
                flagfragonscreen = 2;
                invalidateOptionsMenu();
                transaction.commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.connectnavoption:
                transaction = fragmentManager.beginTransaction();
                fragonscreen = new ConnectFragment();
                transaction.replace(R.id.fragmentcontainer,fragonscreen);
                transaction.commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.guidenavoption:
                transaction = fragmentManager.beginTransaction();
                fragonscreen = new GuideFragment();
                transaction.replace(R.id.fragmentcontainer,fragonscreen);
                transaction.commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.helpnav_option:
                intent = new Intent(HomeActivity.this,HelpActivity.class);
                drawer.closeDrawer(GravityCompat.START);
                startActivity(intent);
                break;
            case R.id.settingnav_option:
                intent = new Intent(HomeActivity.this,SettingActivity.class);
                drawer.closeDrawer(GravityCompat.START);
                startActivity(intent);
                break;
            case R.id.guidesignnavoption:
                intent = new Intent(HomeActivity.this,GuidesigninActivity.class);
                drawer.closeDrawer(GravityCompat.START);
                startActivity(intent);
                break;
        }
        return true;
    }

    //create option ment
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_homemenu,menu);
        return true;
    }

    //this is to change the menu according to fragment on screen
    //called by calling invalidteoptionmenu() method
    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        switch (flagfragonscreen)
        {
            //destination fragment added
            case 2:menu.findItem(R.id.addtoqueuemenu).setVisible(true);
                menu.findItem(R.id.addtofavmenu).setVisible(true);
                break;
                //home fragment added
            case 1:menu.findItem(R.id.addtoqueuemenu).setVisible(false);
                menu.findItem(R.id.addtofavmenu).setVisible(true);
                break;
            //destination queue added
            case 3:menu.findItem(R.id.addtofavmenu).setVisible(false);
                   menu.findItem(R.id.addtoqueuemenu).setVisible(false);
                   break;

        }
        return true;
    }

    //called to replace fraagment from an fragment overridden from functionfromfragmentlistner
    @Override
    public void replacefragment(Fragment fragtoadd) {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.fragmentcontainer,fragtoadd);
        flagfragonscreen=3;
        invalidateOptionsMenu();
        trans.addToBackStack("queue fragment added");
        trans.commit();
    }

    //tohandlefrabment backstack
    @Override
    public void onBackPressed() {
        final FragmentManager man = getSupportFragmentManager();
        man.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                //fragment on home page visible on screen
                if(man.getBackStackEntryCount() <= 0)
                {
                    //if destination is selected in nav view
                    if(navview.getMenu().findItem(R.id.destinationsnavoption).isChecked())
                    {
                        flagfragonscreen =2;
                    }
                    invalidateOptionsMenu();
                }
            }
        });
        super.onBackPressed();
    }
}