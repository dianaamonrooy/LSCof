package com.example.iniciosesin.NoSeUsan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.iniciosesin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InfoUser extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private FirebaseAuth myAuth = FirebaseAuth.getInstance();

    private TextView dateTextView;
    private TextView emailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);

        dateTextView = findViewById(R.id.fecha_ultimo_ingreso);
        emailTextView = findViewById(R.id.Text_view_email);

        myRef = database.getReference().child(myAuth.getCurrentUser().getUid().toString());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()) {
                    String date = dataSnapshot.child("date").getValue().toString();
                    String email = dataSnapshot.child("email").getValue().toString();


                    dateTextView.setText(date);
                    emailTextView.setText(email);


                }

                //Log.d("Éxito", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Error", "Failed to read value.", error.toException());
            }
        });


    }
    public void irAtras(View view){
        startActivity(new Intent(getApplicationContext(), PaginaPrincipalUsuario.class));
        finish();
    }
}