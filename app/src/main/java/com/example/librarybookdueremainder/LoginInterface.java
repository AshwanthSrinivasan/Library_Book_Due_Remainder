package com.example.librarybookdueremainder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class LoginInterface extends AppCompatActivity  implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_interface);

        Button btnAdminLogin = findViewById(R.id.AdminLoginBtn);
        Button btnStudentLogin = findViewById(R.id.StudentLoginBtn);

        btnAdminLogin.setOnClickListener(this);
        btnStudentLogin.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.AdminLoginBtn || v.getId() == R.id.StudentLoginBtn) {
            Intent intent = new Intent(this, SignInActivity.class);
            if(v.getId() == R.id.AdminLoginBtn)
            {
                intent.putExtra("UserLogin","AdminLogin");
            }
            else {
                intent.putExtra("UserLogin","StudentLogin");
            }
            startActivity(intent);
            finish();
        }

    }
}