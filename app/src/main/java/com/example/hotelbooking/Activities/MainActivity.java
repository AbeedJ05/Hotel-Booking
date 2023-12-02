package com.example.hotelbooking.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.hotelbooking.Fragments.HomeFragment;
import com.example.hotelbooking.Fragments.ProfileFragment;
import com.example.hotelbooking.Fragments.SettingsFragment;
import com.example.hotelbooking.Fragments.UserReservation;
import com.example.hotelbooking.R;
import com.example.hotelbooking.adapter.UserReservationAdapter;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private BottomAppBar bottomAppBar;
    UserReservationAdapter reservationAdapter;
    RecyclerView recyclerView;
    private FloatingActionButton fab;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the initial fragment to display
        // Pass MainActivity reference to HomeFragment
        replaceFragment(new HomeFragment());
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomAppBar = findViewById(R.id.bottom_app_bar);

        fab = findViewById(R.id.fab); // Initialize the FloatingActionButton

        fab.setOnClickListener(v -> showCalendarDialog());

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_bottom:
                        replaceFragment(new HomeFragment()); // Pass MainActivity reference to HomeFragment
                        break;
                    case R.id.profile_bottom:
                        replaceFragment(new ProfileFragment());
                        break;
                    case R.id.settings_bottom:
                        replaceFragment(new SettingsFragment());
                        break;
                }

                return true;
            }
        });


    }//end onCreate method


    //fab method
    private void showCalendarDialog() {
  replaceFragment(new UserReservation());

    }



    public void replaceFragment(Fragment fragment) {
        // Get the fragment manager.
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Create a new fragment transaction.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // Replace the fragment in the fragment container.
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        // Commit the fragment transaction.
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void setupCustomToolbar(String title) {
       toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView titleTextView = findViewById(R.id.toolbar_title);
        titleTextView.setText(title);

    }


}
