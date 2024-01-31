package com.sp.whatnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private DocumentsFragment documentsFragment;
    private SettingsFragment settingsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        documentsFragment = new DocumentsFragment();
        settingsFragment = new SettingsFragment();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
/*
    NavigationView.OnNavigationItemSelectedListener navSelected = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.nav_documents){
                fragment = new DocumentsFragment();
            }
            fragmentManager.beginTransaction().replace(R.id.homeFragmentContainer, fragment).commit();
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    } */
}