package com.saveetha.haircarebuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DoctorSignup extends AppCompatActivity {

    EditText etName, etAge, etEmail, etPassword;
    Button btnSignup;
    ImageView backIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_signup);

        // Initialize views
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignup = findViewById(R.id.btnSubmit);
        backIcon = findViewById(R.id.backIcon);

        // Back icon click listener
        backIcon.setOnClickListener(v -> {
            Intent intent = new Intent(DoctorSignup.this, UserLogin.class);
            startActivity(intent);
            finish(); // Optional
        });

        // Signup button click listener
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(DoctorSignup.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Perform signup logic here (API call or local DB)
                Toast.makeText(DoctorSignup.this, "Signup successful!", Toast.LENGTH_SHORT).show();

                // Redirect to login or home
                Intent intent = new Intent(DoctorSignup.this, UserLogin.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
