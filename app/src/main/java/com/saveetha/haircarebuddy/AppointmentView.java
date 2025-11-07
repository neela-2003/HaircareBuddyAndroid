package com.saveetha.haircarebuddy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class AppointmentView extends AppCompatActivity {

    Button btnPending, btnApproved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_view); // your XML file name

        btnPending = findViewById(R.id.btnPending);
        btnApproved = findViewById(R.id.btnApproved);

        // Load PendingFragment by default
        loadFragment(new PendingFragment());

        btnPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new PendingFragment());
            }
        });

        btnApproved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new AcceptedFragment());
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.recyclerViewUserAccept, fragment); // Container ID to host fragments
        transaction.commit();
    }
}
