package com.saveetha.haircarebuddy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PendingFragment extends Fragment {

    public PendingFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewPending);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dummy data
        List<PendingItem> list = new ArrayList<>();
        list.add(new PendingItem("M. SANGAVI ANU", "10:00AM", "13/12/2024",R.drawable.female));
        list.add(new PendingItem("J. RAVI", "12:30PM", "15/12/2024",R.drawable.male));

        PendingAdapter adapter = new PendingAdapter(list);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
