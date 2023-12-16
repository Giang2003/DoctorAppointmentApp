package com.example.badapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AppointmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        getSupportFragmentManager().beginTransaction().add(R.id.container_id, new MakingAppointment1()).commit();
    }
}