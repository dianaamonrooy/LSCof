package com.example.iniciosesin.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.iniciosesin.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), TabbedActivity.class));
            finish();
        }
    }

    public void irInicioSesion(View view) {
        Intent i = new Intent(this, LogIn.class);
        startActivity(i);
        finish();

    }

    public void irIRegistro(View view) {
        Intent i = new Intent(this, SignUp.class);
        startActivity(i);
        finish();
    }

}