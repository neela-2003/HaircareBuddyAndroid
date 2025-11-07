package com.saveetha.haircarebuddy;

import static android.view.View.INVISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HairAnalysisResultActivity extends AppCompatActivity {

    private Button homeButton;
    private TextView aiResultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hair_analysis_result);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Initialize TextView to show AI response
        aiResultText = findViewById(R.id.aiResultText);
        String aiResponse = getIntent().getStringExtra("ai_response");
        String userType = getIntent().getStringExtra("type");
        aiResultText.setText(aiResponse != null ? aiResponse : "No AI response available.");

        homeButton = findViewById(R.id.homeButton);

        if (userType.equals("DOC")) {
            homeButton.setVisibility(INVISIBLE);
        } else{
            homeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HairAnalysisResultActivity.this, MainActivity2.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            });
        }
        // Bind HOME button

    }
}
