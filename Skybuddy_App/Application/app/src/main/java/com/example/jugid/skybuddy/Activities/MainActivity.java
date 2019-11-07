package com.example.jugid.skybuddy.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jugid.skybuddy.Fragments.ActionDiscussing;
import com.example.jugid.skybuddy.Fragments.AddFlightFragment;
import com.example.jugid.skybuddy.Fragments.DetailFlightFragment;
import com.example.jugid.skybuddy.Fragments.DiscussingFragment;
import com.example.jugid.skybuddy.Fragments.FlightFragment;
import com.example.jugid.skybuddy.Fragments.FollowFragment;
import com.example.jugid.skybuddy.Fragments.PostACommentFragment;
import com.example.jugid.skybuddy.Fragments.ProfileFragment;
import com.example.jugid.skybuddy.Fragments.RatesFragment;
import com.example.jugid.skybuddy.Fragments.UserProfileFragment;
import com.example.jugid.skybuddy.Fragments.WelcomeFragment;
import com.example.jugid.skybuddy.Interfaces.VolleyCallback;
import com.example.jugid.skybuddy.Modules.Chloe;
import com.example.jugid.skybuddy.Modules.Thibaut;
import com.example.jugid.skybuddy.R;
import com.example.jugid.skybuddy.Service.Notification;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, WelcomeFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener, FlightFragment.OnFragmentInteractionListener,
                    FollowFragment.OnFragmentInteractionListener, RatesFragment.OnFragmentInteractionListener, DiscussingFragment.OnFragmentInteractionListener, AddFlightFragment.OnFragmentInteractionListener ,
                    DetailFlightFragment.OnFragmentInteractionListener, UserProfileFragment.OnFragmentInteractionListener, PostACommentFragment.OnFragmentInteractionListener, ActionDiscussing.OnFragmentInteractionListener {

    Fragment actualFragment;

    Intent intentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        TextView user = header.findViewById(R.id.TV_navbar_user);
        TextView rang = header.findViewById(R.id.TV_navbar_ranguser);

        String userName = String.format("%s %s",Thibaut.user.getPrenom(),Thibaut.user.getNomUser());
        user.setText(userName);
        rang.setText(Thibaut.user.getRang());
        intentService = new Intent(this,Notification.class);
        //Lancement du service de Notification des messages
        startService(intentService);

        //Tout sur les fragments à l'oncreate
        actualFragment = WelcomeFragment.newInstance("Fragment","Welcome fragment");

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameLayoutGeneral,actualFragment);
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        stopService(intentService);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            if(Thibaut.precedent != null){
                if(!Thibaut.precedent.isEmpty()){
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    switch (Thibaut.precedent){
                        case "AddFlight":
                            actualFragment = AddFlightFragment.newInstance("Fragment","AddFlight fragment");
                            break;
                        case "Flight":
                            actualFragment =  FlightFragment.newInstance("Fragment","Flight fragment");
                            break;
                        case "UserProfile":
                            actualFragment =  UserProfileFragment.newInstance("Fragment","User profile fragment",Thibaut.lastIdUserViewed);
                            break;
                        case "Follow":
                            actualFragment = FollowFragment.newInstance("Fragment", "Follow fragment");
                            break;
                        case "DetailFlight":
                            actualFragment = DetailFlightFragment.newInstance("Fragment", "Detail flight fragment",Thibaut.lastIdVolViewed);
                            break;
                        case "Discussing":
                            actualFragment = DiscussingFragment.newInstance("Fragment", "Discussing fragment");
                    }
                    transaction.replace(R.id.frameLayoutGeneral, actualFragment);
                    transaction.commit();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.action_about:
                startActivity(new Intent(this,InformationActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Thibaut.precedent = "";
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (id == R.id.nav_profile) {
            actualFragment = ProfileFragment.newInstance("Fragment","Profile fragment");
        } else if (id == R.id.nav_flights) {
            actualFragment =  FlightFragment.newInstance("Fragment","Flight fragment");
        } else if(id==R.id.nav_follow){
            actualFragment = FollowFragment.newInstance("Fragment","Follow fragment");
        } else if (id== R.id.nav_rates){
            actualFragment = RatesFragment.newInstance("Fragment","Rates fragment");
        } else if(id== R.id.nav_articles){
            actualFragment = WelcomeFragment.newInstance("Fragment","Welcome fragment");
        } else if(id==R.id.nav_messaging){
            actualFragment = DiscussingFragment.newInstance("Fragment","Discussing fragment");
        } else if(id == R.id.nav_disconnect){
            VolleyCallback callback = new VolleyCallback() {
                @Override
                public void onSuccess(String response) {
                    if(response.equals("1")){
                        startActivity(new Intent(getApplication(),StartActivity.class));
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Impossible de vous deconnecter.", Toast.LENGTH_SHORT).show();
                    }
                }
            };
            Chloe.disconnect(getApplicationContext(), callback);
            //Log.e("DATE THIBAUT",Thibaut.lastActualisationDateMessage);
        }
        transaction.replace(R.id.frameLayoutGeneral, actualFragment);
        transaction.commit();
        Thibaut.lastIdUserViewed="";
        Thibaut.lastIdVolViewed="";
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri){

    }

}
