package com.saveetha.haircarebuddy;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);  // ⬅️ Layout with BottomNavigationView

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Load default fragment
        loadFragment(new HomeFragment1());

        // Handle navigation
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                selectedFragment = new HomeFragment1();
            } else if (id == R.id.nav_food) {
                selectedFragment = new FoodRecomFragment();
            } else if (id == R.id.nav_profile) {
                selectedFragment = new UserProfileFragment1();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
                return true;
            }

            return false;
        });
    }

    // ✅ Accepts any Fragment type
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
