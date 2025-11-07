package com.saveetha.haircarebuddy;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class AppointmentFragment extends Fragment {

    Button btnPending, btnApproved;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);

        btnPending = view.findViewById(R.id.btnPending);
        btnApproved = view.findViewById(R.id.btnApproved);

        // Load default fragment and update button styles
        loadFragment(new PendingFragment());
        highlightButton(btnPending);
        resetButton(btnApproved);

        btnPending.setOnClickListener(v -> {
            loadFragment(new PendingFragment());
            highlightButton(btnPending);
            resetButton(btnApproved);
        });

        btnApproved.setOnClickListener(v -> {
            loadFragment(new AcceptedFragment());
            highlightButton(btnApproved);
            resetButton(btnPending);
        });

        return view;
    }

    private void highlightButton(Button button) {
        button.setBackgroundColor(Color.parseColor("#FFC0CB")); // Light Pink
        button.setTextColor(Color.BLACK); // Optional
    }

    private void resetButton(Button button) {
        button.setBackgroundColor(Color.LTGRAY); // Or default color
        button.setTextColor(Color.BLACK); // Optional
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }
}
