package com.saveetha.haircarebuddy;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.button.MaterialButton;

public class HomeFragment1 extends Fragment {

    private DrawerLayout drawerLayout;
    private ImageButton menuIcon;
    private MaterialButton analyseButton;
    private Button btnPage1, btnPage2, btnPage3;
    private LinearLayout leftDrawer;

    public HomeFragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home1, container, false);

        drawerLayout = view.findViewById(R.id.drawerLayout);
        leftDrawer = view.findViewById(R.id.leftDrawer);
        menuIcon = view.findViewById(R.id.menuIcon);
        analyseButton = view.findViewById(R.id.analyseButton);
        btnPage1 = view.findViewById(R.id.btnPage1);
        btnPage2 = view.findViewById(R.id.btnPage2);
        btnPage3 = view.findViewById(R.id.btnPage3);

        // Menu icon toggles the drawer
        menuIcon.setOnClickListener(v -> {
            if (drawerLayout != null && leftDrawer != null) {
                if (!drawerLayout.isDrawerOpen(leftDrawer)) {
                    drawerLayout.openDrawer(leftDrawer);
                } else {
                    drawerLayout.closeDrawer(leftDrawer);
                }
            }
        });

        // Navigate to AllDoctorsPage
        btnPage1.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), AllDoctors.class);
            startActivity(intent);
        });

        // Navigate to Review2 page
        btnPage2.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ReviewActivity.class);
            startActivity(intent);
        });

        // Logout
        btnPage3.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Confirm Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Intent intent = new Intent(requireContext(), UserLogin.class);
                        startActivity(intent);
                        requireActivity().finishAffinity();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .setCancelable(true)
                    .show();
        });

        // Analyse Button launches HairDensity activity
        analyseButton.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), HairDensity.class);
            startActivity(intent);
            Toast.makeText(getContext(), "Launching hair analysis...", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
