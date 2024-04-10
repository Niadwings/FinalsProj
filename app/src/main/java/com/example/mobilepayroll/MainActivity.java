
package com.example.mobilepayroll;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        EditText usernameEditText = findViewById(R.id.inputEmail);
        EditText passwordEditText = findViewById(R.id.inputPassword);
        Button signinButton = findViewById(R.id.signbtn);
        TextView signup = findViewById(R.id.Sign_up);

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

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Signup.class);
                startActivity(intent);
            }
        });
    }
    public void signInWithCredentials(final String username, final String password) {
        db.collection("users")
                .document(username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String storedPassword = document.getString("password");
                            if (storedPassword != null && storedPassword.equals(encryptPassword(password))) {
                                // Passwords match, login successful
                                Toast.makeText(MainActivity.this, "Login successful.",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this,
                                        emlist_function.class);
                                startActivity(intent);
                            } else {
                                // Passwords don't match
                                Toast.makeText(MainActivity.this, "Incorrect password." + encryptPassword(password),
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
                                        Objects.requireNonNull(task.getException()).getMessage(),
                                Toast.LENGTH_SHORT).show();
                        Log.e("MainActivity", "Error getting document", task.getException());
                    }
                });

    }

    public String encryptPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
