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

public class FoodFragment extends Fragment {

    RecyclerView recyclerView;
    List<FoodItem> foodList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        recyclerView = view.findViewById(R.id.foodRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        foodList.add(new FoodItem("Dal Makhani", "7G PROTEIN • 12G FATS • 9G CARB", R.drawable.dal_makhani));
        foodList.add(new FoodItem("Strawberry blueberry smoothi", "29G PROTEIN • 4.2G FATS •26G CARB", R.drawable.smoothi));
        foodList.add(new FoodItem("Fish Grilled", "2G PROTEIN • 4.2G FATS • 26G CARB", R.drawable.fish));
        foodList.add(new FoodItem("Three bean salad", "41.4G PROTEIN • 3.2G FATS • 20G CARB", R.drawable.bean));
        foodList.add(new FoodItem("Nuts Milkshake", "7.3G PROTEIN • 13.8G FATS • 21.7G CARB", R.drawable.nuts));
        foodList.add(new FoodItem("Avocado corn salad", "11G PROTEIN • 25.9G FATS • 48G CARB", R.drawable.avocado));
        foodList.add(new FoodItem("Channa salad", "10G PROTEIN  • 3G FATS • 40.3G CARB", R.drawable.channa));
        foodList.add(new FoodItem("Chicken and sweet potato meal", "39G PROTEIN • 6.7G FATS • 15.3G CARB", R.drawable.sweet));
        foodList.add(new FoodItem("Pumpkin & sweet potato soup", "10.8G PROTEIN  • 3.3G FATS • 1.9G CARB", R.drawable.pumpkin));
        foodList.add(new FoodItem("Spinach Egg Salad", "5.1G PROTEIN • 4.8G FATS • 11.2G CARB", R.drawable.spinach));
        // Add more

        recyclerView.setAdapter(new FoodAdapter(foodList));
        return view;
    }
}
