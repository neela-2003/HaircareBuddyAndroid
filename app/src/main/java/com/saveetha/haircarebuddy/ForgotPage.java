package com.saveetha.haircarebuddy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.saveetha.haircarebuddy.api.ApiClient;
import com.saveetha.haircarebuddy.api.ApiService;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPage extends AppCompatActivity {

    EditText etEmail, etNewPassword, etConfirmPassword;
    Button btnSave;
    ImageButton backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_page);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        etEmail = findViewById(R.id.etEmail);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSave = findViewById(R.id.btnSave);
        backArrow = findViewById(R.id.backArrow);

        backArrow.setOnClickListener(v -> onBackPressed());

        btnSave.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String newPassword = etNewPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            if (email.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            checkEmailAndResetPassword(email, newPassword);
        });
    }

    private void checkEmailAndResetPassword(String email, String newPassword) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<ResponseBody> call = apiService.checkEmailForReset(email);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        String res = response.body().string();
                        JSONObject jsonObject = new JSONObject(res);
                        boolean status = jsonObject.getBoolean("status");

                        if (status) {
                            // Proceed to call actual reset password API (you must create it)
                            Toast.makeText(ForgotPage.this, "User found. Password reset allowed.", Toast.LENGTH_SHORT).show();
                            // TODO: Add password update API here
                        } else {
                            Toast.makeText(ForgotPage.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ForgotPage.this, "Server error", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ForgotPage.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ForgotPage.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
