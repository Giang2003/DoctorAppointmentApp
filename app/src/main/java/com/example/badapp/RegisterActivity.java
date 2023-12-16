package com.example.badapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout editPasswordLayout;
    private EditText editTextFullname, editTextBirthDate, editTextPhone, editTextPassword, editTextEmail;
    private TextView loginText;

    private Button registerButton;
    private DatePickerDialog picker;
    private Spinner genderSpinner , roleSpinner;
            //= findViewById(R.id.genderSpinner);
    private ArrayAdapter<CharSequence> adapter, adapter2;
                    //= ArrayAdapter.createFromResource(this, R.array.genders,android.R.layout.simple_spinner_item);

    private ProgressBar progressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fAuth = FirebaseAuth.getInstance();
        editPasswordLayout = findViewById(R.id.passwordInputLayout);
        fStore = FirebaseFirestore.getInstance();
        genderSpinner = findViewById(R.id.genderSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.genders,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner = findViewById(R.id.roleSpinner);
        adapter2 = ArrayAdapter.createFromResource(this, R.array.roles, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter2);
        genderSpinner.setAdapter(adapter);
        loginText = findViewById(R.id.loginText);
        editTextFullname = findViewById(R.id.editTextRegisterFullname);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextBirthDate = findViewById(R.id.editTextBirthDate);
        editTextPhone = findViewById((R.id.editTextPhoneNumber));
        editTextPassword = findViewById(R.id.editTextPassword);
        registerButton = findViewById(R.id.registerButton);
        progressBar = findViewById(R.id.progressBar);
        loginText.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        editTextBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(calendar.YEAR);

                picker = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editTextBirthDate.setText(dayOfMonth+"/"+ (month+1) + "/" + year);
                    }
                }, year,month,day);
                picker.show();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Obtain the data entered

                String fullName = editTextFullname.getText().toString();
                String email = editTextEmail.getText().toString();
                String birthdate = editTextBirthDate.getText().toString();
                String phone = editTextPhone.getText().toString();
                String password = editTextPassword.getText().toString();
                String gender = genderSpinner.getSelectedItem().toString();
                String role = roleSpinner.getSelectedItem().toString();

                if (TextUtils.isEmpty(fullName)){
                    editTextFullname.setError("Fullname is required");
                    editTextFullname.requestFocus();
                }

                else if (TextUtils.isEmpty(email)){
                    editTextEmail.setError("Email is required");
                    editTextEmail.requestFocus();
                }

                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    editTextEmail.setError("Please enter a valid email");
                    editTextEmail.requestFocus();
                }

                else if (TextUtils.isEmpty(birthdate)){
                    editTextBirthDate.setError("Birthdate is required");
                    editTextBirthDate.requestFocus();
                }
                else if (TextUtils.isEmpty(phone)){
                    editTextPhone.setError("Phone number is required");
                    editTextPhone.requestFocus();
                }

                else if(TextUtils.isEmpty(password)){
                    editPasswordLayout.setError("Passwords cannot be empty");
                    editPasswordLayout.requestFocus();
                }

                else if(password.length() < 8){
                    editPasswordLayout.setError("Passwords must be longer than 8 characters");
                    //editTextPassword.requestFocus();
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    //Toast.makeText(getApplicationContext(),"Register success",Toast.LENGTH_SHORT).show();
                    registerUser(fullName,email,birthdate,phone,password,gender,role);
                }
            }
        });


        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private void registerUser(String fullName, String email, String birthdate, String phone, String password,String gender, String role) {


        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,"User registered successfully", Toast.LENGTH_SHORT).show();
                            FirebaseUser firebaseUser = fAuth.getCurrentUser();
                            firebaseUser.sendEmailVerification();
                            userID = fAuth.getCurrentUser().getUid();
                            //DocumentReference userReference = fStore.collection("users").document(userID);
                            DocumentReference userReference = fStore.collection("users").document(fAuth.getCurrentUser().getEmail());
                            Map<String,Object> user = new HashMap<>();
                            user.put("FullName", fullName);
                            user.put("Email", email);
                            user.put("Birthdate",birthdate);
                            user.put("Phone",phone);
                            user.put("Password",password);
                            user.put("Gender",gender);
                            user.put("Role",role);

                            userReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("REGISTER", "Success: A new account is created for ID " + userID);
                                }
                            });


                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidCredentialsException e){
                                editTextEmail.setError("Your email is invalid or already in use. Please use a different email");
                                editTextEmail.requestFocus();
                            } catch (FirebaseAuthUserCollisionException e){
                                editTextEmail.setError("A user is already registered with the given email. Please use a different email");
                                editTextEmail.requestFocus();
                            } catch (Exception e){
                                Log.e("Register Activity",e.getMessage());
                                Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        }
                        progressBar.setVisibility(View.GONE);
                    }

                });
    }
}