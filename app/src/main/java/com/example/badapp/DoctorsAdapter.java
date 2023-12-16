package com.example.badapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;


public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder> {

    private List<Doctor> doctorsList;
    private OnDoctorClickListener listener;

    // Interface for callback when an item is clicked
    public interface OnDoctorClickListener {
        void onDoctorClick(Doctor doctor);
    }

    // Adapter constructor
    public DoctorsAdapter(List<Doctor> doctorsList, OnDoctorClickListener listener) {
        this.doctorsList = doctorsList;
        this.listener = listener;
    }

    public void setDoctorsList(List<Doctor> doctorsList){
        this.doctorsList = doctorsList;
        notifyDataSetChanged();
    }
    public void onDoctorClick(Doctor doctor) {
        // Update the selection state of the clicked doctor
        doctor.setSelected(!doctor.isSelected());

        // Refresh the entire list or use notifyItemChanged for better performance
        notifyDataSetChanged();
    }

    // Method to create view holders
    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_item, parent, false);
        return new DoctorViewHolder(itemView);
    }

    // Method to bind data to the view holder
    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctor doctor = doctorsList.get(position);
        holder.bind(doctor, listener);
    }

    // Method to get the count of items
    @Override
    public int getItemCount() {

        return doctorsList.size();
    }
    // Method to handle the logic when a doctor is clicked

    // ViewHolder class for the adapter
    static class DoctorViewHolder extends RecyclerView.ViewHolder {
        TextView doctorName, doctorSpecialty, patientCount, experienceYears, location, phoneNumber, email;

        DoctorViewHolder(View itemView) {
            super(itemView);
            // Bind the views directly to the TextViews, not the LinearLayouts
            doctorName = itemView.findViewById(R.id.doctor_name);
            doctorSpecialty = itemView.findViewById(R.id.doctor_specialty);
            // Assuming you have TextViews with these IDs inside the LinearLayouts
            patientCount = itemView.findViewById(R.id.patientnum);
            experienceYears = itemView.findViewById(R.id.expyear);
            location = itemView.findViewById(R.id.location);
            phoneNumber = itemView.findViewById(R.id.phonenum);
            email = itemView.findViewById(R.id.email);
        }

        void bind(final Doctor doctor, final OnDoctorClickListener listener) {
            doctorName.setText(doctor.getName());
            doctorSpecialty.setText(doctor.getSpecialty());
            patientCount.setText(String.format(Locale.getDefault(), "%d+ Patients", doctor.getPatientCount()));
            experienceYears.setText(String.format(Locale.getDefault(), "%d Years Experience", doctor.getExperienceYears()));
            location.setText(doctor.getLocation());
            phoneNumber.setText(doctor.getPhoneNumber());
            email.setText(doctor.getEmail());
            // Set the background color based on the selected state
            itemView.setBackgroundResource(doctor.isSelected() ? R.drawable.selected_background : R.drawable.card_border);

            // Set the click listener for the entire item view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Notify the listener that an item has been clicked
                    listener.onDoctorClick(doctor);
                }
            });

        }
    }

}
