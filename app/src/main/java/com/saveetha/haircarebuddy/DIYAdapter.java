package com.saveetha.haircarebuddy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DIYAdapter extends RecyclerView.Adapter<DIYAdapter.DIYViewHolder> {
    List<DIYItem> diyList;

    public DIYAdapter(List<DIYItem> diyList) {
        this.diyList = diyList;
    }

    @NonNull
    @Override
    public DIYViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diy, parent, false);
        return new DIYViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DIYViewHolder holder, int position) {
        DIYItem item = diyList.get(position);
        holder.imgLeft.setImageResource(item.leftImage);
        holder.imgRight.setImageResource(item.rightImage);
        holder.imgMid.setImageResource(item.midImage);
        holder.txtLabel.setText(item.label);
    }

    @Override
    public int getItemCount() {
        return diyList.size();
    }

    static class DIYViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLeft, imgRight,imgMid;
        TextView txtLabel;

        public DIYViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLeft = itemView.findViewById(R.id.imgLeft);
            imgRight = itemView.findViewById(R.id.imgRight);
            imgMid = itemView.findViewById(R.id.imgMid);
            txtLabel = itemView.findViewById(R.id.txtLabel);
        }
    }
}
