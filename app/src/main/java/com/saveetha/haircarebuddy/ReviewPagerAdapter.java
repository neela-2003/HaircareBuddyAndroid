package com.saveetha.haircarebuddy;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ReviewPagerAdapter extends FragmentStateAdapter {

    public ReviewPagerAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) return new MyReviewFragment();
        else return new OthersReviewFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
