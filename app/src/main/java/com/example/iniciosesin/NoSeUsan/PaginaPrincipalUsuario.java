package com.example.iniciosesin.NoSeUsan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.iniciosesin.R;
import com.example.iniciosesin.NoSeUsan.InfoUser;
import com.example.iniciosesin.actividades.Aprende;
import com.example.iniciosesin.actividades.MainActivity;
import com.example.iniciosesin.actividades.TabbedActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PaginaPrincipalUsuario extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private FirebaseAuth myAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal_usuario);
        //writeDatabase();

    }

    /*public void writeDatabase() {
        Date logInDate = Calendar.getInstance().getTime();
        User usuario = new User(myAuth.getUid().toString(), myAuth.getCurrentUser().getEmail(), logInDate.toString());
        myRef = database.getReference(usuario.getId());
        myRef.setValue(usuario);

    }*/

    public void logOut(View view) {
        myAuth.signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
    public void infoUser(View view){
        startActivity(new Intent (getApplicationContext(), InfoUser.class));
        finish();
    }
    public void aprende(View view){
        startActivity(new Intent (getApplicationContext(), Aprende.class));
        finish();
    }
    public void irTab(View view){
        startActivity(new Intent (getApplicationContext(), TabbedActivity.class));
        finish();
    }
}