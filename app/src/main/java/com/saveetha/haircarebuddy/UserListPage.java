package com.saveetha.haircarebuddy;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class UserListPage extends AppCompatActivity {
    RecyclerView recyclerViewUsers;
    UserAdapter adapter;
    List<UserModel> userList;
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_page);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);

        // Setup RecyclerView
        userList = getUsers(); // Dummy data
        adapter = new UserAdapter(this, userList);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewUsers.setAdapter(adapter);


    }

    private List<UserModel> getUsers() {
        List<UserModel> list = new ArrayList<>();
        list.add(new UserModel("Abii", 25, "Female", "9876543210", R.drawable.female));
        list.add(new UserModel("John", 30, "Male", "9123456789", R.drawable.female));
        list.add(new UserModel("Mira", 28, "Female", "9988776655", R.drawable.female));
        return list;
    }
}
