package com.saveetha.haircarebuddy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.haircarebuddy.api.ApiClient;
import com.saveetha.haircarebuddy.api.ApiService;
import com.saveetha.haircarebuddy.api.UserListResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListFragment extends Fragment {

    RecyclerView recyclerViewUsers;
    UserAdapter adapter;
    List<UserModel> userList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        recyclerViewUsers = view.findViewById(R.id.recyclerViewUsers);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new UserAdapter(requireContext(), userList);
        recyclerViewUsers.setAdapter(adapter);

        fetchUsers();

        return view;
    }

    private void fetchUsers() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<UserListResponse> call = apiService.getUserList();

        call.enqueue(new Callback<UserListResponse>() {
            @Override
            public void onResponse(Call<UserListResponse> call, Response<UserListResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().status) {
                    List<UserListResponse.UserData> data = response.body().users;
                    userList.clear();

                    for (UserListResponse.UserData user : data) {
                        userList.add(new UserModel(
                                user.Name,
                                Integer.parseInt(user.Age),
                                user.Gender,
                                user.Number,
                                user.Email,
                                user.Gender.equalsIgnoreCase("Male") ? R.drawable.male : R.drawable.female
                        ));
                    }

                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(requireContext(), "No users found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserListResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
