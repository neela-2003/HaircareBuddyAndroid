package com.saveetha.haircarebuddy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.saveetha.haircarebuddy.api.AiResponse;
import com.saveetha.haircarebuddy.api.ApiClient;
import com.saveetha.haircarebuddy.api.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private final Context context;
    private final List<UserModel> userList;

    public UserAdapter(Context context, List<UserModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView userImage;
        TextView tvName, tvAge, tvGender, tvMobile;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.userImage);
            tvName = itemView.findViewById(R.id.tvName);
            tvAge = itemView.findViewById(R.id.tvAge);
            tvGender = itemView.findViewById(R.id.tvGender);
            tvMobile = itemView.findViewById(R.id.tvMobile);
        }
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_list_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserModel user = userList.get(position);
        holder.userImage.setImageResource(user.getImageResId());
        holder.tvName.setText(user.getName());
        holder.tvAge.setText("Age: " + user.getAge());
        holder.tvGender.setText("Gender: " + user.getGender());
        holder.tvMobile.setText("Mobile no: " + user.getMobile());
        
        holder.itemView.setOnClickListener(view -> {
            fetchuserairesponse(user.getEmail());
        });
    }

    private void fetchuserairesponse(String email) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<AiResponse> call = apiService.getAiResponse(email);

        call.enqueue(new Callback<AiResponse>() {
            @Override
            public void onResponse(Call<AiResponse> call, Response<AiResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    String aiResponse = response.body().getAi_response();

                    Intent intent = new Intent(context, HairAnalysisResultActivity.class);
                    intent.putExtra("ai_response", aiResponse);
                    intent.putExtra("type", "DOC");
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "No AI response found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AiResponse> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }
}
