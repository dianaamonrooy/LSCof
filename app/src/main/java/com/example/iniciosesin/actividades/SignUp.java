package com.example.iniciosesin.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SignUp extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private EditText emailEdit;
    private EditText passwordEdit;
    private EditText passwordAgainEdit;
    private EditText nombre;
    private EditText apellidos;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        emailEdit = findViewById(R.id.email_log_in);
        passwordEdit = findViewById(R.id.password_log_in);
        passwordAgainEdit = findViewById(R.id.confirm_password);
        nombre = findViewById(R.id.nombreEditText);
        apellidos = findViewById(R.id.apellidosEditText);
        signUp = findViewById(R.id.boton_sign_up);
        Video_palabras.addAnimation(signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registro(signUp);
            }
        });
        if (mAuth.getCurrentUser() != null) {
            writeDatabase();
            //splashScreen();
            startActivity(new Intent(getApplicationContext(), TabbedActivity.class));
            finish();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }

    private void updateUI(FirebaseUser currentUser) {
        Log.i("User", "" + currentUser);
    }

    public void createUserWithEmailAndPassword(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("ÉXITO", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            writeDatabase();
                            Toast.makeText(getApplicationContext(), "Usuario creado con éxito",
                                    Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), TabbedActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Log.w("ERROR", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void registro(View view) {
        String email = emailEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();
        String passwordAgain = passwordAgainEdit.getText().toString().trim();
        String nombreText = nombre.getText().toString().trim();
        String apellidosText = apellidos.getText().toString().trim();
        if (email.isEmpty() || password.isEmpty() || passwordAgain.isEmpty() || nombreText.isEmpty() || apellidosText.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Ingresa los espacios vacíos",
                    Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "La contraseña debe tener 6 o más caracteres",
                    Toast.LENGTH_SHORT).show();
        } else if (password.equals(passwordAgain)) {
            this.createUserWithEmailAndPassword(email, password);
        } else {
            Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden, intenta nuevamente",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void writeDatabase() {
        Date logInDate = Calendar.getInstance().getTime();
        String date = new SimpleDateFormat("MM-dd-yyyy").format(logInDate);
        logInDate = new Date();   // given date
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(logInDate);
        String hour = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);

        User usuario = new User(nombre.getText().toString().trim(), apellidos.getText().toString().trim(), mAuth.getUid().toString(), mAuth.getCurrentUser().getEmail(), date+"\n"+hour, "location", "aprende_practica", "url",0,1);
        myRef = database.getReference(usuario.getId());
        myRef.setValue(usuario);

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent (getApplicationContext(),MainActivity.class));
        finish();
    }
    public void splashScreen(){
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SignUp.this,SplashScreen.class);
                startActivity(mainIntent);
                finish();
            }
        }, 2000);
    }
}

