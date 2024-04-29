package com.example.librarybookdueremainder;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminRemove extends AppCompatActivity {

    private EditText editTextRollNumber, editTextBookId, editTextName,editTextBookName;
    private FirebaseFirestore db;
    private Button removebtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_remove);

        editTextRollNumber = findViewById(R.id.RollNumberRemoveET);
        editTextBookId = findViewById(R.id.BookIdRemoveET);
        editTextName=findViewById(R.id.NameRemoveET);
        editTextBookName = findViewById(R.id.BookNameRemoveET);
        removebtn=findViewById(R.id.removebtn);

        db = FirebaseFirestore.getInstance();
        removebtn.setOnClickListener(this::onRemoveClicked);


    }
   public void onRemoveClicked(View view) {
        String rollNumber = editTextRollNumber.getText().toString().trim();
        String bookId = editTextBookId.getText().toString().trim();

        if (TextUtils.isEmpty(rollNumber) || TextUtils.isEmpty(bookId)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        // Remove the book from the student's database using roll number and book ID
        DocumentReference bookRef = db.collection("Students").document(rollNumber)
                .collection("BorrowedBooks").document(bookId);
        bookRef.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AdminRemove.this, "Book removed successfully", Toast.LENGTH_SHORT).show();
                            finish(); // Close the activity after removing the book
                        } else {
                            Toast.makeText(AdminRemove.this, "Failed to remove book", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    }