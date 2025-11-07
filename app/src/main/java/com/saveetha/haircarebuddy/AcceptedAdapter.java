package com.saveetha.haircarebuddy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AcceptedAdapter extends RecyclerView.Adapter<AcceptedAdapter.ViewHolder> {

    private final List<AcceptedItem> itemList;

    public AcceptedAdapter(List<AcceptedItem> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public AcceptedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_accepted, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AcceptedAdapter.ViewHolder holder, int position) {
        AcceptedItem item = itemList.get(position);
        holder.tvName.setText(item.getName());
        holder.tvTime.setText("TIME: " + item.getTime());
        holder.tvDate.setText("DATE: " + item.getDate());
        holder.profileImage.setImageResource(item.getImageResId());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvTime, tvDate;
        ImageView profileImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvDate = itemView.findViewById(R.id.tvDate);
            profileImage = itemView.findViewById(R.id.profileImage);
        }
    }
}
