package com.saveetha.haircarebuddy;

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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.saveetha.haircarebuddy.api.ApiClient;
import com.saveetha.haircarebuddy.api.ApiService;
import com.saveetha.haircarebuddy.api.DoctorProfileResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorProfileFragment extends Fragment {

    private TextView nameBox, phoneBox, emailBox;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_doctor_profile, container, false);

        nameBox = view.findViewById(R.id.nameBox);
        phoneBox = view.findViewById(R.id.phoneBox);
        emailBox = view.findViewById(R.id.emailBox);

        ImageView deleteIcon = view.findViewById(R.id.deleteIcon);
        deleteIcon.setOnClickListener(v -> {
            // Optional: Clear doctor preferences
            SharedPreferences.Editor editor = requireContext().getSharedPreferences("doctor_prefs", Context.MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();

            // Navigate to DoctorLogin
            Intent intent = new Intent(requireActivity(), Doctor_Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        fetchDoctorProfile();

        return view;
    }

    private void fetchDoctorProfile() {
        SharedPreferences prefs = requireContext().getSharedPreferences("doctor_prefs", Context.MODE_PRIVATE);
        String doctorId = prefs.getString("doctor_id", null);

        if (doctorId == null) {
            Toast.makeText(requireContext(), "Doctor ID not found. Please login again.", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<DoctorProfileResponse> call = apiService.getDoctorProfile(doctorId);

        call.enqueue(new Callback<DoctorProfileResponse>() {
            @Override
            public void onResponse(Call<DoctorProfileResponse> call, Response<DoctorProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().status) {
                    DoctorProfileResponse.Data data = response.body().data;
                    nameBox.setText(data.name);
                    phoneBox.setText(data.doctorId); // Update if needed
                    emailBox.setText(data.experience + " years experience");
                } else {
                    Toast.makeText(requireContext(), "No profile found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DoctorProfileResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "Failed to fetch profile", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
