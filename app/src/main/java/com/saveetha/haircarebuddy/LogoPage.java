package com.saveetha.haircarebuddy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LogoPage extends AppCompatActivity {

    AppCompatButton getStartedBtn;
    View bottomLine2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_logo_page);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        bottomLine2 = findViewById(R.id.bottomLine2);
        getStartedBtn = findViewById(R.id.getStartedBtn);

        getStartedBtn.setOnClickListener(view -> {
            Intent intent = new Intent(LogoPage.this,ChoosePage.class);
            startActivity(intent);
        });

        // Set initial alpha
        bottomLine2.setAlpha(0f);
        getStartedBtn.setAlpha(0f);

        // Delay and animate
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                bottomLine2.setVisibility(View.VISIBLE);
                getStartedBtn.setVisibility(View.VISIBLE);

                bottomLine2.animate().alpha(1f).setDuration(800).start();
                getStartedBtn.animate().alpha(1f).setDuration(800).start();
            }
        }, 2000); // Delay in milliseconds (2000ms = 2 seconds)

    }
}