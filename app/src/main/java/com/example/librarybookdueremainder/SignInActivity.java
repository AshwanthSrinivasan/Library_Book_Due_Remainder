package com.example.librarybookdueremainder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button loginButton;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;

    // private Object AuthResult;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Intent intent = getIntent();
        String userLogin = intent.getStringExtra("UserLogin");
        TextView accountSpecification = findViewById(R.id.AccountSpecificationTV);
        System.out.println(userLogin);
        if (userLogin.equals("AdminLogin")) {

            accountSpecification.setText("Admin Login");
        }
        if (userLogin.equals("StudentLogin")) {
            accountSpecification.setText("Student Login");
        }

        // login purpose  with Db
        editTextUsername = findViewById(R.id.usernameET);
        editTextPassword = findViewById(R.id.passwordET);
        loginButton = findViewById(R.id.LoginButton);

        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        loginButton.setOnClickListener(this::onLoginClicked);

    }

    public void onLoginClicked(View view) {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Enter Email");
            editTextUsername.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Enter password");
            editTextPassword.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {
                            // Sign in success
                            String rollNumber = extractRollNumber(username); // Extract roll number from email
                            saveRollNumber(rollNumber); // Save roll number to SharedPreferences
                            handleLoginSuccess(username);

                        } else {
                            // Sign in failed
                            Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }

    private String extractRollNumber(String email) {
        // Extract roll number logic based on your email format
        // For example, if the email is "12345@domain.com", roll number could be "12345"
        // Modify this method according to your email format
        return email.split("@")[0];
    }
    private void saveRollNumber(String rollNumber) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("rollNumber", rollNumber);
        editor.apply();
    }
    private void handleLoginSuccess(String username)
    {
        String userEmail = mAuth.getCurrentUser().getEmail();
        if (userEmail != null) {
            if (userEmail.matches("[a-zA-Z]+@rajalakshmi.edu.in")) {
                // Admin login (Email starts with letters)
                Toast.makeText(SignInActivity.this, "Login as Admin", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignInActivity.this, AdminMain.class));
                //finish();
            } else if (userEmail.matches("\\d+@rajalakshmi.edu.in")) {
                // Student login (Email starts with digits)
                Toast.makeText(SignInActivity.this, "Login as Student", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignInActivity.this, StudentMain.class));
                // finish();
            } else {
                // Invalid user
                Toast.makeText(SignInActivity.this, "Invalid user", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }

}

