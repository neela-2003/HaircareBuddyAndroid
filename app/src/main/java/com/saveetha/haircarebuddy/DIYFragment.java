package com.saveetha.haircarebuddy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class DIYFragment extends Fragment {

    RecyclerView recyclerView;
    List<DIYItem> diyList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diy, container, false);

        recyclerView = view.findViewById(R.id.diyRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        diyList.add(new DIYItem(R.drawable.aloe_vera, R.drawable.rose,  R.drawable.amla,"Hair Growth"));
        diyList.add(new DIYItem(R.drawable.aloe_vera, R.drawable.honey, R.drawable.power, "Split Ends"));
        diyList.add(new DIYItem(R.drawable.aloe_vera, R.drawable.neem,  R.drawable.lemon,"Dandruff"));
        diyList.add(new DIYItem(R.drawable.aloe_vera, R.drawable.yogurt,  R.drawable.honey,"Deep Conditioning"));
        diyList.add(new DIYItem(R.drawable.aloe_vera, R.drawable.egg,  R.drawable.olive,"Hair Smooth"));
        // Add more

        recyclerView.setAdapter(new DIYAdapter(diyList));
        return view;
    }
}
