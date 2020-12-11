package com.example.iniciosesin.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iniciosesin.Practica.Video_palabras;
import com.example.iniciosesin.R;
import com.example.iniciosesin.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

//comentario de prueba
public class LogIn extends AppCompatActivity {
    private EditText emailEdit;
    private EditText passwordEdit;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private StorageReference mStorageRef;

    private String nombre;
    private String apellidos;

    private Button logIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        logIn = findViewById(R.id.boton_log_in);
        Video_palabras.addAnimation(logIn);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn(logIn);
            }
        });
        mAuth = FirebaseAuth.getInstance();
        emailEdit = findViewById(R.id.email_log_in);
        passwordEdit = findViewById(R.id.password_log_in);
        if (mAuth.getCurrentUser() != null) {
            writeDatabase2();
            startActivity(new Intent(getApplicationContext(), TabbedActivity.class));
            finish();

        }
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        Log.i("User", "" + currentUser);
    }


    public void signInWithEmailAndPassword(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            getNombres_Apellidos();
                            String nom = nombre;
                            String ape = apellidos;
                            Log.d("ÉXITO", "signInWithEmail:success");
                            Toast.makeText(getApplicationContext(), "Usuario ingresado con éxito.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            writeDatabase2();
                            Intent i = new Intent(getApplicationContext(), TabbedActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("ERROR", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });

    }

    public void logIn(View view) {
        String email = emailEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Ingresa los espacios vacíos",
                    Toast.LENGTH_SHORT).show();
        } else {

            this.signInWithEmailAndPassword(email, password);
        }
    }

    public void writeDatabase2() {
        Date logInDate = Calendar.getInstance().getTime();
        String date = new SimpleDateFormat("MM-dd-yyyy").format(logInDate);
        logInDate = new Date();   // given date
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(logInDate);
        String hour = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);

        mAuth = FirebaseAuth.getInstance();
        myRef = database.getReference().child(mAuth.getCurrentUser().getUid().toString());
        myRef.child("date").setValue(date+"\n"+hour);

    }

    public void writeDatabase() {
        Date logInDate = Calendar.getInstance().getTime();
        String date = new SimpleDateFormat("MM-dd-yyyy").format(logInDate);

        getNombres_Apellidos();
        String nom = nombre;
        String ape = apellidos;
        User usuario = new User(nombre, apellidos, mAuth.getUid().toString(), mAuth.getCurrentUser().getEmail(), date, "location", "aprende_practica", "url", 0, 1);
        myRef = database.getReference(usuario.getId());
        myRef.setValue(usuario);

    }

    public void getNombres_Apellidos() {
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        myRef = database.getReference().child(mAuth.getCurrentUser().getUid().toString());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()) {
                    System.out.println("\n*\n*\n*\n" + dataSnapshot.child("nombre").getValue().toString() + "\n*\n*\n*\n");
                    String nombr = dataSnapshot.child("nombre").getValue().toString();
                    nombre = dataSnapshot.child("nombre").getValue().toString();
                    apellidos = dataSnapshot.child("apellidos").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}