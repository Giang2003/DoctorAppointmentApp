package com.example.badapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class LoginActivity extends AppCompatActivity {
    ProgressBar progressBar;
    TextInputLayout passwordLayout, usernameLayout;
    EditText editUsername, editPassword;
    Button loginButton;
    TextView registerText;
    FirebaseAuth authProfile;
    FirebaseFirestore fStore;
    String userID;
    String TAG = "LoginActivity";
//    @Override
//    public void onStart() {
//        super.onStart();
//        if (authProfile.getCurrentUser()!= null){
//            Toast.makeText(LoginActivity.this,"Already logged in", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
//        }
//        else{
//            Toast.makeText(LoginActivity.this,"Login opened", Toast.LENGTH_SHORT).show();
//        }
//        //
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         usernameLayout = findViewById(R.id.usernameInputLayout);
         progressBar = findViewById(R.id.progressBar);
         editUsername = findViewById(R.id.usernameInputEditText);
         editPassword = findViewById(R.id.passwordInputEditText);
         loginButton = (Button) findViewById(R.id.registerButton);
         registerText = findViewById(R.id.textSignUp);
         registerText.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
         passwordLayout = findViewById(R.id.passwordInputLayout);
         authProfile = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editUsername.getText().toString();
                String password = editPassword.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    usernameLayout.setError("Email and password required");
                    editUsername.requestFocus();
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    loginUser(email,password);
                }
            }
        });

        //Opens the Register page
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void loginUser(String email, String password) {
        authProfile = FirebaseAuth.getInstance();
        authProfile.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    //Check the role of user
                    FirebaseUser user = authProfile.getCurrentUser();
                    //if: patient:
                    fStore = FirebaseFirestore.getInstance();

                    userID = authProfile.getCurrentUser().getUid();
                    DocumentReference userReference = fStore.collection("users").document(authProfile.getCurrentUser().getEmail());
                    //DocumentReference userReference = fStore.collection("users").document(userID);
                    userReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    if (document.getString("Role").equals("Doctor") || document.getString("Role").equals("Nurse")){
                                        startActivity(new Intent(LoginActivity.this,DoctorHomeActivity.class));
                                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    }
                                    else if (document.getString("Role").equals("Patient")){
                                        startActivity(new Intent(LoginActivity.this,PatientHomeActivity.class));
                                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(LoginActivity.this, "Something went wrong! ", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });

                }
                else{
                    try{
                        throw task.getException();
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        usernameLayout.setError("Wrong username or password. Please try again");
                        passwordLayout.requestFocus();

                    }
                    catch (FirebaseAuthInvalidUserException e){
                        usernameLayout.setError("Username not found or disabled");
                        passwordLayout.requestFocus();
                    }

                    catch (Exception e){
                        Log.e("Login Acitivity",e.getMessage());

                    }

                    //Toast.makeText(LoginActivity.this, "Something went wrong",Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}