package com.example.librarybookdueremainder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;

import android.view.View;


public class AdminMain extends AppCompatActivity {

    private Button addBtn ;
    private Button removeBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        addBtn = findViewById(R.id.AddBtn);

        removeBtn = findViewById(R.id.RemoveBtn);


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAdminAddActivity ();
            }
        });

        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAdminRemoveActivity();
            }
        });
    }
    private void openAdminAddActivity() {
        Intent intent = new Intent(this, AdminAdd.class);
        startActivity(intent);
    }

    private void openAdminRemoveActivity() {
        Intent intent = new Intent(this, AdminRemove.class);
        startActivity(intent);
    }

}