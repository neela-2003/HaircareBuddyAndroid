package com.saveetha.haircarebuddy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.saveetha.haircarebuddy.api.ApiClient;
import com.saveetha.haircarebuddy.api.ApiService;
import com.saveetha.haircarebuddy.api.LoginRequest;
import com.saveetha.haircarebuddy.api.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLogin extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button loginButton;
    TextView signupTextView, forgotPasswordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.etPassword);
        loginButton = findViewById(R.id.loginButton);
        signupTextView = findViewById(R.id.signupTXT);
        forgotPasswordTextView = findViewById(R.id.tvForgotPassword);

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            performLogin(email, password);
        });

        signupTextView.setOnClickListener(v ->
                startActivity(new Intent(UserLogin.this, UserSignup.class)));

        forgotPasswordTextView.setOnClickListener(v ->
                startActivity(new Intent(UserLogin.this, ForgotPage.class)));
    }

    private void performLogin(String email, String password) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        LoginRequest loginRequest = new LoginRequest(email, password);

        Call<LoginResponse> call = apiService.loginUser(email, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse.isStatus()) {
                        Toast.makeText(UserLogin.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        // ✅ Save email in SharedPreferences
                        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("email", email);
                        editor.apply();

                        // Navigate to home
                        Intent intent = new Intent(UserLogin.this, MainActivity2.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(UserLogin.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UserLogin.this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(UserLogin.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
