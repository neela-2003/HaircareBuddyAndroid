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

public class AcceptedFragment extends Fragment {

    public AcceptedFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accepted, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewAccepted);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dummy data
        List<AcceptedItem> itemList = new ArrayList<>();
        itemList.add(new AcceptedItem("Ananya", "10:00 AM", "2025-07-16", R.drawable.female));
        itemList.add(new AcceptedItem("Ravi Kumar", "11:30 AM", "2025-07-17", R.drawable.male));
        itemList.add(new AcceptedItem("Meera", "02:00 PM", "2025-07-18", R.drawable.female));

        AcceptedAdapter adapter = new AcceptedAdapter(itemList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
