package com.saveetha.haircarebuddy;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.saveetha.haircarebuddy.api.ApiClient;
import com.saveetha.haircarebuddy.api.ApiService;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyReviewFragment extends Fragment {

    RatingBar ratingBar;
    EditText editTextReview;
    Button buttonSubmit;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_review, container, false);

        ratingBar = view.findViewById(R.id.ratingBar);
        editTextReview = view.findViewById(R.id.editTextReview);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(v -> {
            float rating = ratingBar.getRating();
            String review = editTextReview.getText().toString().trim();

            SharedPreferences preferences = getActivity().getSharedPreferences("user_session", MODE_PRIVATE);
            String email = preferences.getString("email","") ;

            if (review.isEmpty()) {
                Toast.makeText(getContext(), "Please write a review", Toast.LENGTH_SHORT).show();
                return;
            }

            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<Map<String, Object>> call = apiService.submitReview(email, review, rating);

            call.enqueue(new Callback<Map<String, Object>>() {
                @Override
                public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        String message = response.body().get("message").toString();
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

                        editTextReview.setText("");
                        ratingBar.setRating(0);
                    } else {
                        Toast.makeText(getContext(), "Submission failed!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                    Toast.makeText(getContext(), "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        return view;
    }
}
