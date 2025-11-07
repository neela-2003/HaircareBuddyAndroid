package com.saveetha.haircarebuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChoosePage extends AppCompatActivity {

    AppCompatButton doc;
    View bottomLine2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_page);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 👇 Navigation to Doctor_Login on ImageView click
        ImageView userImage = findViewById(R.id.user);
        ImageView docImage = findViewById(R.id.doc);


        userImage.setOnClickListener(v -> {
            Intent intent = new Intent(ChoosePage.this, UserLogin.class);
            startActivity(intent);
        });

        docImage.setOnClickListener(v -> {
            Intent intent = new Intent(ChoosePage.this, Doctor_Login.class);
            startActivity(intent);
        });
    }
}