package com.saveetha.haircarebuddy;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.haircarebuddy.api.ApiClient;
import com.saveetha.haircarebuddy.api.ApiService;
import com.saveetha.haircarebuddy.models.DoctorListResponse;
import com.saveetha.haircarebuddy.models.DoctorModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllDoctors extends AppCompatActivity {

    RecyclerView recyclerView;
    DoctorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_doctors);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        recyclerView = findViewById(R.id.recyclerViewDoctors);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Back button
        ImageView backBtn = findViewById(R.id.ivBack);
        backBtn.setOnClickListener(v -> finish());

        fetchDoctors();
    }

    private void fetchDoctors() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getAllDoctors().enqueue(new Callback<DoctorListResponse>() {
            @Override
            public void onResponse(Call<DoctorListResponse> call, Response<DoctorListResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().status) {
                    List<DoctorModel> doctorList = response.body().data;
                    adapter = new DoctorAdapter(AllDoctors.this, doctorList);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(AllDoctors.this, "Failed: " + response.body().message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DoctorListResponse> call, Throwable t) {
                Toast.makeText(AllDoctors.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
