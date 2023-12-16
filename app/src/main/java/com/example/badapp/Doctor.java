package com.example.badapp;

public class Doctor {

    public int resourceID;
    private String FullName;
    private String Specialty;
    private int PatientCount;
    private int Experienceyears;
    private String Phone;
    private String Email;
    private String Location;

    // Constructor to initialize the member variables
    public Doctor(String FullName, String Specialty, int PatientCount, int Experienceyears, String Phone, String Email, String Location) {
        this.FullName = FullName;
        this.Specialty = Specialty;
        this.PatientCount = PatientCount;
        this.Experienceyears = Experienceyears;
        this.Phone = Phone;
        this.Email = Email;
        this.Location = Location;
    }
    public Doctor(int resourceID,String FullName, String Specialty, int PatientCount, int Experienceyears, String Phone, String Email, String Location) {
        this.resourceID = resourceID;
        this.FullName = FullName;
        this.Specialty = Specialty;
        this.PatientCount = PatientCount;
        this.Experienceyears = Experienceyears;
        this.Phone = Phone;
        this.Email = Email;
        this.Location = Location;
    }

    // Getter methods to access the information
    public String getName() {
        return FullName;
    }

    public String getSpecialty() {
        return Specialty;
    }

    public int getPatientCount() {
        return PatientCount;
    }

    public int getExperienceYears() {
        return Experienceyears;
    }

    public String getPhoneNumber() {
        return Phone;
    }

    public String getEmail() {
        return Email;
    }

    public String getLocation() {
        return Location;
    }

    // Setter methods (optional, if you need to modify the data later)
    public void setName(String FullName) {
        this.FullName = FullName;
    }
    public void setSpecialty(String Specialty){
        this.Specialty = Specialty;
    }
    public void setPatientCount(int PatientCount){
        this.PatientCount = PatientCount;
    }
    public void setExperienceYears(int Experienceyears){
        this.Experienceyears = Experienceyears;
    }
    public void setPhoneNumber(String Phone){
        this.Phone = Phone;
    }
    public void setEmail(String Email){
        this.Email = Email;
    }
    public void setLocation(String Location){
        this.Location = Location;
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

