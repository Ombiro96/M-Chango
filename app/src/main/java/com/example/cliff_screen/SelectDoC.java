package com.example.cliff_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SelectDoC extends AppCompatActivity {

    private Button donorButton, charOrgButton;
    private TextView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_do_c);

        donorButton= findViewById(R.id.donorButton);
        charOrgButton= findViewById(R.id.charOrgButton);
        backButton= findViewById(R.id.backButton);

        donorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    Intent intent=new Intent(SelectDoC.this, DonorRegistrationActivity.class);
                    startActivity(intent);
            }
        }
        });
        charOrgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    Intent intent=new Intent(SelectDoC.this, Charity_Organization.class);
                    startActivity(intent);
                }
            }
        } );
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    Intent intent= new Intent(SelectDoC.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        } );
    }
}