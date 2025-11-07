package com.saveetha.haircarebuddy;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.saveetha.haircarebuddy.api.ApiClient;
import com.saveetha.haircarebuddy.api.ApiService;
import com.saveetha.haircarebuddy.api.AppointmentResponse;

import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Appointment extends AppCompatActivity {

    EditText etDate, etTime;
    Button btnBook;
    ImageView ivBack;

    String doctorId = "1"; // Default/fallback
    String patientName = "Neelaveny";
    String patientEmail = "neelaveny@example.com";
    String status = "Pending";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Get doctorId if passed via Intent
        if (getIntent() != null) {
            doctorId = getIntent().getStringExtra("doctorId");
            if (doctorId == null) doctorId = "1"; // fallback
        }

        etDate = findViewById(R.id.etAppointmentDate);
        etTime = findViewById(R.id.etAppointmentTime);
        btnBook = findViewById(R.id.btnBookAppointment);
        ivBack = findViewById(R.id.ivBack);

        // 🔙 Go back to AllDoctors page
        ivBack.setOnClickListener(v -> {
            Intent intent = new Intent(Appointment.this, AllDoctors.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        // 📅 Date Picker
        etDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(Appointment.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String date = String.format(Locale.getDefault(), "%02d-%02d-%04d", selectedDay, selectedMonth + 1, selectedYear);
                        etDate.setText(date);
                    }, year, month, day);

            datePickerDialog.show();
        });

        // 🕓 Time Picker
        etTime.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(Appointment.this,
                    (view, selectedHour, selectedMinute) -> {
                        String amPm = selectedHour >= 12 ? "PM" : "AM";
                        int formattedHour = selectedHour % 12;
                        if (formattedHour == 0) formattedHour = 12;
                        String time = String.format(Locale.getDefault(), "%02d:%02d %s", formattedHour, selectedMinute, amPm);
                        etTime.setText(time);
                    }, hour, minute, false);

            timePickerDialog.show();
        });

        // ✅ Book Appointment
        btnBook.setOnClickListener(v -> {
            String date = etDate.getText().toString();
            String time = etTime.getText().toString();

            if (date.isEmpty() || time.isEmpty()) {
                Toast.makeText(this, "Please select both date and time", Toast.LENGTH_SHORT).show();
                return;
            }

            // 🚀 Retrofit API Call
            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<AppointmentResponse> call = apiService.bookAppointment(
                    doctorId,
                    patientName,
                    patientEmail,
                    date,
                    time,
                    status
            );

            call.enqueue(new Callback<AppointmentResponse>() {
                @Override
                public void onResponse(Call<AppointmentResponse> call, Response<AppointmentResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        AppointmentResponse res = response.body();
                        Toast.makeText(Appointment.this, res.getMessage(), Toast.LENGTH_LONG).show();

                        if (res.isStatus()) {
                            Intent intent = new Intent(Appointment.this, MainActivity2.class);
                            intent.putExtra("fragment", "home");
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(Appointment.this, "Server error. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AppointmentResponse> call, Throwable t) {
                    Toast.makeText(Appointment.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
