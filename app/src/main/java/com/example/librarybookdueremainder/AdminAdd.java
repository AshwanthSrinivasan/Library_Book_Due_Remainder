package com.example.librarybookdueremainder;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;



public class AdminAdd extends AppCompatActivity {

    private EditText editTextName, editTextRollNumber, editTextBookName ,editTextBookId;
    private EditText editTextReturnDate;
    private FirebaseFirestore db;
    private Button Addbtn  ;
    private  DatePickerDialog picker;
   // private Button dateBtn ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add);


        editTextName = findViewById(R.id.NameAddET);
        editTextRollNumber = findViewById(R.id.RollNumberET);
        editTextBookName = findViewById(R.id.BookNameET);
        editTextReturnDate = findViewById(R.id.BookReturnDateET);
        editTextBookId = findViewById(R.id.BookIdET);
      //  dateBtn = findViewById(R.id.DateBtn);

        Addbtn=findViewById(R.id.Addbtn);



// setting up DatePicker on EditText
        editTextReturnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar=Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // Date Picker Dialog
                    picker =  new DatePickerDialog(AdminAdd.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            editTextReturnDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                        }
                    },year,month,day);
                    picker.show();
            }
        });{

        }

        db = FirebaseFirestore.getInstance();
        Addbtn.setOnClickListener(this::onAddClicked);

    }
    public void onAddClicked(View view) {
        String name = editTextName.getText().toString().trim();
        String rollNumber = editTextRollNumber.getText().toString().trim();
        String bookName = editTextBookName.getText().toString().trim();
        String returnDate = editTextReturnDate.getText().toString().trim();
        String bookId = editTextBookId.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(rollNumber)
                || TextUtils.isEmpty(bookName) || TextUtils.isEmpty(returnDate) || TextUtils.isEmpty(bookId)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }





        // Create a map to store book details including bookId
        Map<String, Object> book = new HashMap<>();
        book.put("name",name);
        book.put("bookId", bookId);
        book.put("bookName", bookName);
        book.put("returnDate", returnDate);
        book.put("rollnumber",rollNumber);


        // Add the book to the student's database based on roll number
        DocumentReference studentRef = db.collection("Students").document(rollNumber).collection("BorrowedBooks").document(bookId);
        studentRef.set(book)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AdminAdd.this, "Book added successfully", Toast.LENGTH_SHORT).show();
                            finish(); // Close the activity after adding the book
                        } else {
                            Toast.makeText(AdminAdd.this, "Failed to add book", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}