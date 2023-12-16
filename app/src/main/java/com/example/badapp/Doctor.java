package com.example.badapp;

public class Doctor {

    public int resourceID;
    private String name;
    private String specialty;
    private int patientCount;
    private int experienceYears;
    private String phoneNumber;
    private String email;
    private String location;

    // Constructor to initialize the member variables
    public Doctor(String name, String specialty, int patientCount, int experienceYears, String phoneNumber, String email, String location) {
        this.name = name;
        this.specialty = specialty;
        this.patientCount = patientCount;
        this.experienceYears = experienceYears;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.location = location;
    }

    public Doctor(int resourceID, String name, String specialty, int patientCount, int experienceYears, String phoneNumber, String email, String location) {
        this.resourceID = resourceID;
        this.name = name;
        this.specialty = specialty;
        this.patientCount = patientCount;
        this.experienceYears = experienceYears;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.location = location;
    }

    // Getter methods to access the information
    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public int getPatientCount() {
        return patientCount;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }

    // Setter methods (optional, if you need to modify the data later)
    public void setName(String name) {
        this.name = name;
    }
    public void setSpecialty(String specialty){
        this.specialty = specialty;
    }
    public void setPatientCount(int patientCount){
        this.patientCount = patientCount;
    }
    public void setExperienceYears(int experienceYears){
        this.experienceYears = experienceYears;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setLocation(String location){
        this.location = location;
    }
    private boolean isSelected;

    // Getter and setter for isSelected
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

