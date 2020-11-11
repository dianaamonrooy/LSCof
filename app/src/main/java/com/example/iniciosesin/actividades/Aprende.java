package com.example.iniciosesin.actividades;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.iniciosesin.NoSeUsan.PaginaPrincipalUsuario;
import com.example.iniciosesin.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Aprende extends AppCompatActivity {

    private StorageReference mStorageRef;
    private ArrayList<String> carpetas;
    private FirebaseAuth myAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprende);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        carpetas = new ArrayList<>();
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        TableLayout tableLayout = (TableLayout) findViewById(R.id.TableLayout);
        StorageReference ref = mStorageRef.child("videos");
        myAuth.getCurrentUser();
        myRef = database.getReference().child(myAuth.getCurrentUser().getUid().toString());
        myRef.child("location").setValue("Aprender");


        ref.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                int numeroTableRows;
                if (listResult.getPrefixes().size() % 3 == 0) {
                    numeroTableRows = listResult.getPrefixes().size() / 3;
                } else {
                    numeroTableRows = listResult.getPrefixes().size() / 3 + 1;
                }

                for (int i = 1; numeroTableRows >= i; i++) {
                    TableRow tableRow = new TableRow(getApplicationContext());
                    tableRow.setId(i);
                    tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    tableRow.setGravity(Gravity.CENTER);
                    tableLayout.addView(tableRow);

                }
                showFolders(listResult, tableLayout);
                /*int i = 1;
                for (StorageReference carpeta : listResult.getPrefixes()) {
                    carpetas.add(carpeta.getName());
                    Button botonCarpeta = new Button(getApplicationContext());
                    botonCarpeta.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    botonCarpeta.setId(i);
                    botonCarpeta.setText(carpeta.getName());
                    System.out.println("**********"+botonCarpeta);
                    botonCarpeta.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseAuth myAuth = FirebaseAuth.getInstance();
                            myAuth.getCurrentUser();
                            startActivity(new Intent(getApplicationContext(), Verbos.class));
                            finish();
                        }
                    });
                    int accordingTableRow;
                    if (i % 3 != 0) {
                        accordingTableRow = i / 3 + 1;
                    } else {
                        accordingTableRow = i / 3;
                    }
                    TableRow chosenTableRow = (TableRow) findViewById(accordingTableRow);
                    chosenTableRow.addView(botonCarpeta);
                    if (chosenTableRow.getChildCount() == 3) {
                        tableLayout.addView(chosenTableRow);
                    }

                    i++;

                }*/

            }
        }).addOnFailureListener(e -> {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getApplicationContext());
            builder1.setMessage("Ha ocurrido un error, revisa tu conexión a internet");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }
            );
            AlertDialog alert1 = builder1.create();
            alert1.show();


        });

    }

    public void irVerbos(View view) {
        startActivity(new Intent(getApplicationContext(), Verbos.class));
    }

    public void showFolders(ListResult listResult, TableLayout tableLayout) {
        int i = 1;
        for (StorageReference carpeta : listResult.getPrefixes()) {
            carpetas.add(carpeta.getName());
            Button botonCarpeta = new Button(getApplicationContext());
            botonCarpeta.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            botonCarpeta.setId(i+100);
            botonCarpeta.setText(carpeta.getName());
            System.out.println("**********" + botonCarpeta.getText());
            botonCarpeta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    myAuth.getCurrentUser();
                    myRef = database.getReference().child(myAuth.getCurrentUser().getUid().toString());
                    myRef.child("location").setValue("Aprender " + botonCarpeta.getText());


                    startActivity(new Intent(getApplicationContext(), Verbos.class));

                }
            });
            int accordingTableRow;
            if (i % 3 != 0) {
                accordingTableRow = i / 3 + 1;
            } else {
                accordingTableRow = i / 3;
            }
            TableRow chosenTableRow = (TableRow) findViewById(accordingTableRow);
            System.out.println(chosenTableRow.getId());
            chosenTableRow.addView(botonCarpeta);
            /*if (chosenTableRow.getChildCount() == 3) {
                tableLayout.addView(chosenTableRow);
            }*/

            i++;

        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent (getApplicationContext(), PaginaPrincipalUsuario.class));
        finish();
    }
}


