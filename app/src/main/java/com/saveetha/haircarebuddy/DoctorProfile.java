package com.saveetha.haircarebuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

@SuppressWarnings("MissingSuperCall")
public class DoctorProfile extends AppCompatActivity {

    ImageView deleteIcon;
    TextView emailBox, phoneBox, nameBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        // Initialize views
        deleteIcon = findViewById(R.id.deleteIcon);
        emailBox = findViewById(R.id.emailBox);
        phoneBox = findViewById(R.id.phoneBox);
        nameBox = findViewById(R.id.nameBox);

        // Logout navigation
        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorProfile.this, UserLogin.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        // Example click actions (optional)
        emailBox.setOnClickListener(view -> {
            // Add intent to open email editing screen (if any)
            // startActivity(new Intent(this, EditEmailActivity.class));
        });

        phoneBox.setOnClickListener(view -> {
            // Add intent to open phone editing screen
        });

        nameBox.setOnClickListener(view -> {
            // Add intent to open name editing screen
        });
    }

    @Override
    public void onBackPressed() {
        // Navigate to HomePage instead of going back
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
        finish();
        // super.onBackPressed(); // (not called intentionally)
    }
}
