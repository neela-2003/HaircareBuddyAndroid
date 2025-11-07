package com.saveetha.haircarebuddy; // 🔁 Replace with your actual package name

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class HomePage extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ImageButton menuIcon;
    private Button analyseButton, btnPage1, btnPage2, btnPage3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        drawerLayout = findViewById(R.id.drawerLayout);
        menuIcon = findViewById(R.id.menuIcon);
        analyseButton = findViewById(R.id.analyseButton);
        btnPage1 = findViewById(R.id.btnPage1); // Doctor Status
        btnPage2 = findViewById(R.id.btnPage2); // Rating & Review
        btnPage3 = findViewById(R.id.btnPage3); // Logout

        // 🔹 Open drawer on menu icon click
        menuIcon.setOnClickListener(v -> drawerLayout.openDrawer(findViewById(R.id.leftDrawer)));

        // 🔹 Analyse Button → Navigate to Hair Density page
        analyseButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, HomePage.class);
            startActivity(intent);
        });

        // 🔹 Menu Button 1 → Doctor Status Page
        btnPage1.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, AllDoctors.class);
            startActivity(intent);
        });

        // 🔹 Menu Button 2 → Rating and Review Page
        btnPage2.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, ReviewActivity.class);
            startActivity(intent);
        });

        // 🔹 Menu Button 3 → Logout (optional: back to login)
        btnPage3.setOnClickListener(v -> {
            Intent intent = new Intent(HomePage.this, UserLogin.class);
            startActivity(intent);
            finish(); // Close home page
        });
    }
}
