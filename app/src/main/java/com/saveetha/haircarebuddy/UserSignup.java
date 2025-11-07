package com.saveetha.haircarebuddy;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.saveetha.haircarebuddy.api.ApiClient;
import com.saveetha.haircarebuddy.api.ApiService;
import com.saveetha.haircarebuddy.api.SignupResponse;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSignup extends AppCompatActivity {

    EditText etName, etAge, etGender, etNumber, etEmail, etPassword;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etGender = findViewById(R.id.etGender);
        etNumber = findViewById(R.id.etMobile);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignup = findViewById(R.id.btnSubmit);

        btnSignup.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            int age = Integer.parseInt(etAge.getText().toString().trim());
            String gender = etGender.getText().toString().trim();
            String number = etNumber.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<SignupResponse> call = apiService.signupUser(name, age, gender, number, email, password);

            call.enqueue(new Callback<SignupResponse>() {
                @Override
                public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        SignupResponse signupResponse = response.body();
                        Toast.makeText(UserSignup.this, signupResponse.getMessage(), Toast.LENGTH_LONG).show();
                        if (signupResponse.isStatus()) {
                            finish(); // Optional: Go back to login
                        }
                    } else {
                        Toast.makeText(UserSignup.this, "Signup failed!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SignupResponse> call, Throwable t) {
                    Toast.makeText(UserSignup.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
