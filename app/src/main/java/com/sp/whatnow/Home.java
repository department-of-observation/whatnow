package com.sp.whatnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up ActionBarDrawerToggle
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.nav_open,
                R.string.nav_close
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        homeFragment home = new homeFragment();
        DocumentsFragment documents = new DocumentsFragment();
        SettingsFragment settings = new SettingsFragment();
        ExerciseFragment exercise = new ExerciseFragment();


        // Handle item clicks in the NavigationView
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                // Handle item clicks here
                switch (item.getItemId()) {
                    case R.id.nav_dashboard:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.homeFragmentContainer, home)
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.nav_documents:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.homeFragmentContainer, documents)
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.nav_exercise:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.homeFragmentContainer, exercise)
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.nav_settings:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.homeFragmentContainer, settings)
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.nav_forum:
                        intent = new Intent(Home.this,ForumsPage.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                }
                return false;




            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}