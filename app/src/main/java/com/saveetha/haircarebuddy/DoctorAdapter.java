package com.saveetha.haircarebuddy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.haircarebuddy.models.DoctorModel;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {

    private Context context;
    private List<DoctorModel> doctorList;

    public DoctorAdapter(Context context, List<DoctorModel> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_doctor_card, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        DoctorModel doctor = doctorList.get(position);

        holder.tvName.setText("Name: " + doctor.getName());
        holder.tvId.setText("ID: " + doctor.getDoctorId());
        holder.tvExperience.setText("Experience: " + doctor.getExperience());

        // Optional: Load image if available
        // holder.imageView.setImageResource(doctor.getImageResId());

        holder.btnBook.setOnClickListener(v -> {
            Intent intent = new Intent(context, Appointment.class);
            intent.putExtra("doctorId", doctor.getDoctorId()); // pass doctorId
            intent.putExtra("doctorName", doctor.getName());   // optional
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public static class DoctorViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvName, tvId, tvExperience;
        Button btnBook;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.doctorImage); // Optional doctor image
            tvName = itemView.findViewById(R.id.tvDoctorName);
            tvId = itemView.findViewById(R.id.tvDoctorId);
            tvExperience = itemView.findViewById(R.id.tvExperience);
            btnBook = itemView.findViewById(R.id.btnBook);
        }
    }
}
