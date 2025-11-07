package com.saveetha.haircarebuddy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.haircarebuddy.api.ApiClient;
import com.saveetha.haircarebuddy.api.ApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OthersReviewFragment extends Fragment {

    RecyclerView recyclerView;
    ReviewAdapter adapter;
    List<ReviewModel> reviewList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_others_review, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewReviews);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ReviewAdapter(reviewList);
        recyclerView.setAdapter(adapter);

        fetchReviewsFromServer();

        return view;
    }

    private void fetchReviewsFromServer() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<Map<String, Object>> call = apiService.getAllReviews();
        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.isSuccessful() && response.body() != null && (boolean) response.body().get("status")) {
                    List<Map<String, Object>> data = (List<Map<String, Object>>) response.body().get("data");

                    reviewList.clear();

                    for (Map<String, Object> item : data) {
                        String email = item.get("email").toString();
                        String view = item.get("view").toString();
                        float rating = Float.parseFloat(item.get("rating").toString());

                        reviewList.add(new ReviewModel(email, rating, view));
                    }

                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "No reviews found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to fetch reviews: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
