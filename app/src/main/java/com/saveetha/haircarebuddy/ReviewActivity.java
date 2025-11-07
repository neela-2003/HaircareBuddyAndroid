package com.saveetha.haircarebuddy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ReviewActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    ReviewPagerAdapter adapter;
    ImageButton ivBack; // Back button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // View bindings
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        ivBack = findViewById(R.id.ivBack);

        // Set up ViewPager and Tabs
        adapter = new ReviewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0) tab.setText("My Review");
                    else tab.setText("Others Review");
                }).attach();

        // 🔙 Back button logic
        ivBack.setOnClickListener(v -> {
            Intent intent = new Intent(ReviewActivity.this, MainActivity2.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }
}
