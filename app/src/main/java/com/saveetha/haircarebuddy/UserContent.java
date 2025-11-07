package com.saveetha.haircarebuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

 public class UserContent extends AppCompatActivity {

    Button agreeButton, cancelButton;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_user_content);

         agreeButton = findViewById(R.id.agreeButton);
         cancelButton = findViewById(R.id.cancelButton);

         // ✅ Get the ai_response from Intent
         String aiResponse = getIntent().getStringExtra("ai_response");

         agreeButton.setOnClickListener(v -> {
             Intent intent = new Intent(UserContent.this, HairAnalysisResultActivity.class);
             intent.putExtra("ai_response", aiResponse); // ✅ Pass it forward
             startActivity(intent);
         });

         cancelButton.setOnClickListener(v -> {
             Intent intent = new Intent(UserContent.this, HomeFragment1.class);
             startActivity(intent);
         });
     }

 }
