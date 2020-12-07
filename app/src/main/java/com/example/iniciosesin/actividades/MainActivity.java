package com.example.iniciosesin.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.iniciosesin.R;
import com.example.iniciosesin.interfaces_cultura.ComunicacionFragments;
import com.google.firebase.auth.FirebaseAuth;

public class   MainActivity extends AppCompatActivity implements ComunicacionFragments {

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

    @Override
    public void iniciarLeyes() {
        Toast.makeText(getApplicationContext(), "Prueba de click en leyes", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void iniciarLS() {
        Toast.makeText(getApplicationContext(), "Prueba de click en lenguas", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void iniciarAprenderMas() {
        Toast.makeText(getApplicationContext(), "Prueba de click en aprenderm", Toast.LENGTH_SHORT).show();
    }
}