package com.example.mobilepayroll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class emlist_function extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emlist_function);

        ImageButton emp_profile = findViewById(R.id.emp_btn);
        ImageButton payslip = findViewById(R.id.payslip_btn1);
        ImageButton payroll = findViewById(R.id.STP_btn);
        ImageButton user_profile = findViewById(R.id.userprofile_btn);

        emp_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(emlist_function.this,
                        com.example.mobilepayroll.Emp_profile.class);
                startActivity(intent);
            }
        });

        payslip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(emlist_function.this
                        , com.example.mobilepayroll.payslip.class);

                startActivity(intent);
            }
        });

        payroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(emlist_function.this, Payroll.class);
                startActivity(intent);
            }
        });
        user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(emlist_function.this, user_profile.class);
                startActivity(intent);
            }
        });
    }
}
