package com.example.badapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MakingAppointment1 extends Fragment {

    Button button;
    Spinner spinner;

    FirebaseFirestore fStore;
    EditText editDate;

    TextView backPageText;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MakingAppointment1() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private void chooseDate(){
        // Get a calendar instance
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH); // Use DAY_OF_MONTH instead of DATE
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        // Create a DatePickerDialog and set its OnDateSetListener
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(), // context should be getActivity() when inside a Fragment
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        // Set the calendar to the chosen date
                        calendar.set(selectedYear, selectedMonth, selectedDay);

                        // Format the date as you want it to be
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        editDate.setText(dateFormat.format(calendar.getTime()));
                    }
                }, year, month, day);

        datePickerDialog.show(); // Show the DatePickerDialog
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fStore = FirebaseFirestore.getInstance();
        View view = inflater.inflate(R.layout.fragment_making_appointment1, container, false);

        backPageText = view.findViewById(R.id.backPageText);
        backPageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PatientHomeActivity.class));
            }
        });

        button = view.findViewById(R.id.buttonNext);
        button.setOnClickListener(new View.OnClickListener(){
                                      @Override
                                      public void onClick(View view){

                                          DocumentReference appointmentReference = fStore.collection("appointments").document();


                                          MakingAppointment2 makeAppointment2 = new MakingAppointment2();
                                          getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_id, makeAppointment2).commit();
                                      }
                                  }
        );
        Spinner spinner = (Spinner) view.findViewById(R.id.spinnerGender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.gender_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Spinner spinner2 = (Spinner) view.findViewById(R.id.spinnerAppointmentType);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.appointment_array,
                android.R.layout.simple_spinner_item
        );
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        Spinner spinner3 = (Spinner) view.findViewById(R.id.spinnerTime);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.time_array,
                android.R.layout.simple_spinner_item
        );
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);

        editDate = (EditText) view.findViewById(R.id.editTextDate);
        editDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });

        return view;


//        private void chooseDate(){
//            Calendar calendar = Calendar.getInstance();
//            int day = calendar.get(Calendar.DATE);
//            int month = calendar.get(Calendar.MONTH);
//            int year = calendar.get(Calendar.YEAR);
//            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
//                @Override
//                public void onDateSet(DatePicker view, int i, int i1, int i2) {
//                    calendar.set(i, i1, i2);
//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//                    edtDate.setText(simpleDateFormat.format(calendar.getTime()));
//                }
//            }, year,month, day);
//            datePickerDialog.show();
//        }

    }
}