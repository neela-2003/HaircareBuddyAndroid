package com.saveetha.haircarebuddy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.saveetha.haircarebuddy.api.ApiClient;
import com.saveetha.haircarebuddy.api.ApiService;
import com.saveetha.haircarebuddy.api.UserProfileResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileFragment1 extends Fragment {

    TextView nameBox, phoneBox, emailBox, passwordBox;
    ImageView deleteIcon, profileImage;

    SharedPreferences preferences;
    String email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile1, container, false);

        // Initialize UI components
        nameBox = view.findViewById(R.id.nameBox);
        phoneBox = view.findViewById(R.id.phoneBox);
        emailBox = view.findViewById(R.id.emailBox);
        passwordBox = view.findViewById(R.id.passwordBox);
        deleteIcon = view.findViewById(R.id.deleteIcon);
        profileImage = view.findViewById(R.id.profileImage);

        preferences = requireActivity().getSharedPreferences("user_session", Context.MODE_PRIVATE);
        email = preferences.getString("email", "");

        if (!email.isEmpty()) {
            fetchUserProfile(email);
        } else {
            Toast.makeText(getContext(), "No user email found!", Toast.LENGTH_SHORT).show();
        }

        // Set delete action
        deleteIcon.setOnClickListener(v -> showDeleteConfirmation());

        return view;
    }

    private void fetchUserProfile(String email) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<UserProfileResponse> call = apiService.getUserProfile(email);

        call.enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    UserProfileResponse.Data data = response.body().getData();

                    nameBox.setText(data.getName());
                    phoneBox.setText(data.getNumber());
                    emailBox.setText(email);
                    passwordBox.setText("********");
                } else {
                    Toast.makeText(getContext(), "Failed: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDeleteConfirmation() {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account?")
                .setPositiveButton("Yes", (dialog, which) -> deleteUserAccount())
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteUserAccount() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<UserProfileResponse> call = apiService.deleteUser(email);

        call.enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    Toast.makeText(getContext(), "Account deleted!", Toast.LENGTH_SHORT).show();

                    // Clear session
                    preferences.edit().clear().apply();

                    // Go to login
                    startActivity(new Intent(requireContext(), UserLogin.class));
                    requireActivity().finish();
                } else {
                    Toast.makeText(getContext(), "Delete failed: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
