package com.example.badapp;

import androidx.annotation.NonNull;
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

public class LoginActivity extends AppCompatActivity {
    ProgressBar progressBar;
    TextInputLayout passwordLayout, usernameLayout;
    EditText editUsername, editPassword;
    Button loginButton;
    TextView registerText;
    FirebaseAuth authProfile;

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
//                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
//                    Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Opens the Register page
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, PatientHomeActivity.class));
            }
        });
    }

    private void loginUser(String email, String password) {
        authProfile.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //Check the role of user
                    //if: patient:
                    startActivity(new Intent(LoginActivity.this,PatientHomeActivity.class));
                    //if: doc:

                    Toast.makeText(LoginActivity.this, "Login Successful",Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        throw task.getException();
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        usernameLayout.setError("Wrong username or password. Please try again");
                        passwordLayout.requestFocus();
                    } catch (Exception e){
                        Log.e("Login Acitivity",e.getMessage());

                    }
                    //Toast.makeText(LoginActivity.this, "Something went wrong",Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}