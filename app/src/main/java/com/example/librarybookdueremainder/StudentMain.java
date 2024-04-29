package com.example.librarybookdueremainder;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class StudentMain extends AppCompatActivity {


    private static final int REQUEST_PERMISSION_CODE = 101;

    private static final String TAG = "StudentMain";
    public static String userRollNumber;


    private RecyclerView recyclerViewBookDetails;
    private BookAdapter bookAdapter;
    private List<Book> bookList;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        // Initialize Firebase instances
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();




        // Initialize RecyclerView and BookAdapter
        recyclerViewBookDetails = findViewById(R.id.recyclerViewBookDetails);
        recyclerViewBookDetails.setHasFixedSize(true);
        recyclerViewBookDetails.setLayoutManager(new LinearLayoutManager(this));
        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(this, bookList);
        recyclerViewBookDetails.setAdapter(bookAdapter);

        // Load the borrowed books for the current student
        loadBorrowedBooks();

        FirebaseApp.initializeApp(this);



    }

    private void loadBorrowedBooks() {
        // Get the current user
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            String rollNumber = user.getEmail().split("@")[0];
            // Query the Firestore database to get the borrowed books for the current student
            db.collection("Students")
                    .document(rollNumber) // Assuming the user ID is the document ID for student data
                    .collection("BorrowedBooks")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (DocumentChange document : task.getResult().getDocumentChanges()) {
                                // Convert Firestore document to Book object
                                Book book = document.getDocument().toObject(Book.class);
                                // Add book to the list
                                bookList.add(book);
                                // Notify adapter of the data change
                                bookAdapter.notifyDataSetChanged();

                            }
                        } else {
                            Toast.makeText(StudentMain.this, "Failed to load borrowed books", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            // User is not authenticated
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }


    }


