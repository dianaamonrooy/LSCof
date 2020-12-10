package com.example.iniciosesin.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.iniciosesin.Practica.Video_palabras;
import com.example.iniciosesin.R;
import com.example.iniciosesin.interfaces_cultura.ComunicacionFragments;
import com.google.firebase.auth.FirebaseAuth;
import com.thekhaeng.pushdownanim.PushDownAnim;

public class   MainActivity extends AppCompatActivity implements ComunicacionFragments {

    private Button inicioSesion,registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicioSesion=findViewById(R.id.inicioSesion);
        registro=findViewById(R.id.registro);
        Video_palabras.addAnimation(inicioSesion);
        Video_palabras.addAnimation(registro);
        inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irInicioSesion(inicioSesion);
            }
        });
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irIRegistro(registro);
            }
        });
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

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