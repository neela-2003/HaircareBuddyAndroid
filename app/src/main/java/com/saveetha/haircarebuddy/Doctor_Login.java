package com.saveetha.haircarebuddy;



import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.saveetha.haircarebuddy.api.ApiClient;
import com.saveetha.haircarebuddy.api.ApiService;
import com.saveetha.haircarebuddy.api.DoctorLoginResponse;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Doctor_Login extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button loginButton;
    TextView signUpTextView, forgotPasswordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Initialize views
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.etPassword);
        loginButton = findViewById(R.id.loginButton);
        signUpTextView = findViewById(R.id.signupTXT);
        forgotPasswordTextView = findViewById(R.id.tvForgotPassword);

        // Handle Login Button Click
        loginButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            } else {
                loginDoctor(email, password);
            }
        });

        // Handle Sign Up Click
        signUpTextView.setOnClickListener(view -> {
            startActivity(new Intent(Doctor_Login.this, DoctorSignup.class));
        });

        // Handle Forgot Password Click
        forgotPasswordTextView.setOnClickListener(view -> {
            startActivity(new Intent(Doctor_Login.this, DoctorForget.class));
        });
    }

    private void loginDoctor(String email, String password) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<DoctorLoginResponse> call = apiService.doctorLogin(email, password);

        call.enqueue(new Callback<DoctorLoginResponse>() {
            @Override
            public void onResponse(Call<DoctorLoginResponse> call, Response<DoctorLoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DoctorLoginResponse res = response.body();
                    if (res.getStatus()) {
                        Toast.makeText(Doctor_Login.this, "Login successful!", Toast.LENGTH_SHORT).show();

                        // Save doctor ID in SharedPreferences for profile reference
                        getSharedPreferences("doctor_prefs", MODE_PRIVATE)
                            .edit()
                            .putString("doctor_id", res.getUser().getDoctor_id())
                            .apply();

                        // Redirect to main screen
                        Intent intent = new Intent(Doctor_Login.this, MainActivity.class);
                        intent.putExtra("doctor_id", res.getUser().getDoctor_id());
                        intent.putExtra("name", res.getUser().getName());
                        intent.putExtra("email", res.getUser().getEmail());
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Doctor_Login.this, res.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Doctor_Login.this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DoctorLoginResponse> call, Throwable t) {
                Toast.makeText(Doctor_Login.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
