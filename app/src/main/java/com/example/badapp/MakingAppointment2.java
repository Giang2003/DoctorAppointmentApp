package com.example.badapp;

import androidx.recyclerview.widget.LinearLayoutManager;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MakingAppointment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MakingAppointment2 extends Fragment {
    private RecyclerView recyclerView;
    private List<Doctor> doctorsList; // Member variable for the doctors list
    private DoctorsAdapter doctorsAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MakingAppointment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MakingAppointment2.
     */
    // TODO: Rename and change types and number of parameters
    public static MakingAppointment2 newInstance(String param1, String param2) {
        MakingAppointment2 fragment = new MakingAppointment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_making_appointment2, container, false);
        View button3 = view.findViewById(R.id.set_appointment_button);

        //UNCOMMENT ME TO GO TO CONFIRMATION
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ConfirmationActivity confirmationActivity = new ConfirmationActivity();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_id, confirmationActivity).commit();
            }
        });

        recyclerView = view.findViewById(R.id.recycler_view_doctors);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        doctorsList = getListDoctor();

        // Set up the adapter with the list of doctors
        doctorsAdapter = new DoctorsAdapter(doctorsList, new DoctorsAdapter.OnDoctorClickListener() {
            @Override
            public void onDoctorClick(Doctor doctor) {
                // Update the clicked doctor's selection state
                for (Doctor d : doctorsList) {
                    d.setSelected(false); // deselect all
                }
                doctor.setSelected(true); // select the clicked one

                // Refresh the adapter
                doctorsAdapter.notifyDataSetChanged();
            }
        });

        // Attach the adapter to the RecyclerView
        recyclerView.setAdapter(doctorsAdapter);
        return view;
    }
    private List<Doctor> getListDoctor(){
        List<Doctor> doctorsList = new ArrayList<>();
        doctorsList.add(new Doctor("Dr. Edward", "Dentist", 50, 5, "+0930 235 049", "edward.dr@gmail.com", "Singapore"));
        doctorsList.add(new Doctor("Dr. Thanh", "Physician", 30, 10, "+0320 809 202", "drthanh@mail.com", "Ho Chi Minh"));
        doctorsList.add(new Doctor("Dr. Anna", "Cardiologist", 20, 5, "+0634 825 837", "annadr@hotmail.com", "Hanoi"));
        doctorsList.add(new Doctor("Dr. David", "General", 40, 15, "+0923 284 732", "doctordavid@gmail.com", "China"));
        doctorsList.add(new Doctor("Dr. Charlie", "Surgeon", 30, 5, "+0378 826 937", "charliedr@gmail.com", "New York"));
        return doctorsList;
    }

}