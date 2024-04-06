package com.example.mobilepayroll;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    EditText usernameEditText, passwordEditText;

    @Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    db = FirebaseFirestore.getInstance();
    FirebaseApp.initializeApp(this);
    usernameEditText = findViewById(R.id.inputEmail);
    passwordEditText = findViewById(R.id.inputPassword);
    Button signinButton = findViewById(R.id.signbtn);

    signinButton.setOnClickListener(v -> {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (!username.isEmpty() && !password.isEmpty()) {
            signInWithCredentials(username, password);
        } else {
            Toast.makeText(MainActivity.this, "Please enter username and password.",
                    Toast.LENGTH_SHORT).show();
        }
    });
}
    public void signInWithCredentials(final String username, final String password) {
        db.collection("admin")
                .document(username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String storedPassword = document.getString("password");
                            String storedUsername = document.getString("username");
                            if (storedPassword != null && storedPassword.equals(password) &&
                                    storedUsername != null && storedUsername.equals(username)) {
                                // Passwords match, login successful
                                Toast.makeText(MainActivity.this, "Login successful.",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this,
                                        emlist_function.class);
                                startActivity(intent);
                            } else {
                                // Passwords don't match
                                Toast.makeText(MainActivity.this, "Incorrect password.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Username not found
                            Toast.makeText(MainActivity.this, "Username not found.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Error getting document
                        Toast.makeText(MainActivity.this, "Error: " +
                                        Objects.requireNonNull(task.getException()).getMessage(), // Log the exception message
                                Toast.LENGTH_SHORT).show();
                        Log.e("MainActivity", "Error getting document", task.getException());
                    }
                });
    }

}

