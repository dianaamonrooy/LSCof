package com.example.iniciosesin.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.iniciosesin.Practica.Video_palabras;
import com.example.iniciosesin.R;

import com.google.firebase.auth.FirebaseAuth;
import com.thekhaeng.pushdownanim.PushDownAnim;

public class MainActivity extends AppCompatActivity {

    private Button inicioSesion, registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicioSesion = findViewById(R.id.inicioSesion);
        registro = findViewById(R.id.registro);
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
            //splashScreen();
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

    public void splashScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(MainActivity.this, SplashScreen.class);
                startActivity(mainIntent);
                finish();
            }
        }, 2000);
    }


}