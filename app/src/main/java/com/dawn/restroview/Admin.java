package com.dawn.restroview;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.dawn.restroview.adminfrags.AboutFrag;
import com.dawn.restroview.adminfrags.GeneralFrag;
import com.dawn.restroview.adminfrags.MonthlyFrag;
import com.dawn.restroview.adminfrags.TodayFrag;

public class Admin extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //initializing drawer layout
        drawerLayout = findViewById(R.id.drawer_layout);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);


        //title of app
        actionBar.setTitle("Today");
        //setting frags
        TodayFrag todayFrag = new TodayFrag();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content_frame, todayFrag);
        fragmentTransaction.commit();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()){
                    case R.id.nav_monthly:
                        fragment = new MonthlyFrag();
                        actionBar.setTitle("Monthly");
                        break;
                    case R.id.nav_general:
                        fragment = new GeneralFrag();
                        actionBar.setTitle("General");
                        break;
                    case R.id.nav_about:
                        fragment = new AboutFrag();
                        actionBar.setTitle("About");
                        break;
                    case R.id.nav_settings:
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                    default:
                        fragment = new TodayFrag();
                        actionBar.setTitle("Today");
                        break;
                }
                FragmentTransaction newTransaction = getSupportFragmentManager().beginTransaction();
                newTransaction.replace(R.id.content_frame, fragment);
                newTransaction.commit();

                drawerLayout.closeDrawers();

                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawers();
                }else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawers();
        }else {
            super.onBackPressed();
        }
    }
}
