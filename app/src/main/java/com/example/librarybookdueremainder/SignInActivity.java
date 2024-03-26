package com.example.librarybookdueremainder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button loginButton;
    private FirebaseAuth mAuth;

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
                .addOnCompleteListener(this,  new OnCompleteListener<AuthResult>()  {
                    @Override
                    public void onComplete(@NonNull  Task<AuthResult> task) {


                        if (task.isSuccessful()) {
                            // Sign in success
                            String userEmail = mAuth.getCurrentUser().getEmail();
                            if (userEmail != null)
                                if (userEmail.matches("[a-zA-Z]+@rajalakshmi.edu.in")) {
                                    // Admin login (Email starts with letters)
                                    Toast.makeText(SignInActivity.this, "Login as Admin", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignInActivity.this, AdminMain.class));
                                    finish();
                                } else if (userEmail.matches("\\d+@rajalakshmi.edu.in")) {
                                    // Student login (Email starts with digits)
                                    Toast.makeText(SignInActivity.this, "Login as Student", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignInActivity.this, StudentMain.class));
                                    finish();
                                } else {
                                    // Invalid user
                                    Toast.makeText(SignInActivity.this, "Invalid user", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // Email is null
                                Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Sign in failed
                            Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }


    }

