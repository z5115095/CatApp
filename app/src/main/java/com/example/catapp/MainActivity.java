package com.example.catapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;



import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    //Declare item for the bottom navigation bar
    BottomNavigationView botNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //New fragment for search
        Fragment defaultFragment = new SearchFragment();
        swapFragment(defaultFragment);


        //Creating logic for bottom navigation bar, switching between the search and favourites
        botNavView = findViewById(R.id.bottomnav);
        botNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.bottom_navigation_search) {
                    Fragment fragment = new SearchFragment();
                    swapFragment(fragment);
                    return true;
                } else if (menuItem.getItemId() == R.id.bottom_navigation_favourite) {
                    Fragment fragment = new FavouriteFragment();
                    swapFragment(fragment);
                    return true;
                }

                return false;
            }
        });

    }

//swapping between fragments
    private void swapFragment(Fragment selectedFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment, selectedFragment);
        fragmentTransaction.commit();
    }
}

