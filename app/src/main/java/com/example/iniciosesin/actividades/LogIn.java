package com.example.iniciosesin.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iniciosesin.R;
import com.example.iniciosesin.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
//comentario de prueba
public class LogIn extends AppCompatActivity {
    private EditText emailEdit;
    private EditText passwordEdit;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        mAuth = FirebaseAuth.getInstance();
        emailEdit = findViewById(R.id.email_log_in);
        passwordEdit = findViewById(R.id.password_log_in);
        if (mAuth.getCurrentUser()!=null){
            writeDatabase();
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
                            Log.d("ÉXITO", "signInWithEmail:success");
                            Toast.makeText(getApplicationContext(), "Usuario ingresado con éxito.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            writeDatabase();
                            Intent i = new Intent (getApplicationContext(),TabbedActivity.class);
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
        if (email.isEmpty() || password.isEmpty() ) {
            Toast.makeText(getApplicationContext(), "Ingresa los espacios vacíos",
                    Toast.LENGTH_SHORT).show();
        } else {
            this.signInWithEmailAndPassword(email,password);
        }
    }
    public void writeDatabase() {
        Date logInDate = Calendar.getInstance().getTime();
        User usuario = new User(mAuth.getUid().toString(), mAuth.getCurrentUser().getEmail(), logInDate.toString(),"location");
        myRef = database.getReference(usuario.getId());
        myRef.setValue(usuario);

    }
}