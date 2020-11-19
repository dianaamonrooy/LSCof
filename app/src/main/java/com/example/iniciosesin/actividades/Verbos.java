package com.example.iniciosesin.actividades;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.iniciosesin.ControladorTab.LSC;
import com.example.iniciosesin.R;
import com.example.iniciosesin.popUps.PopUpShowVideos;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.takusemba.spotlight.CustomTarget;
import com.takusemba.spotlight.OnSpotlightEndedListener;
import com.takusemba.spotlight.OnSpotlightStartedListener;
import com.takusemba.spotlight.Spotlight;

import java.util.ArrayList;

public class Verbos extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private StorageReference mStorageRef;
    private ArrayList<String> carpetas;
    private FirebaseAuth myAuth = FirebaseAuth.getInstance();
    private DatabaseReference myRef;
    private String location;
    private TableLayout tableLayout;
    private ArrayList<TableRow> listaTableRows = new ArrayList<TableRow>();
    private WindowManager windowManager2;
    private WindowManager.LayoutParams params;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verbos);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        carpetas = new ArrayList<>();
        //LinearLayout linearLayout = (LinearLayout) getView().findViewById(R.id.LinealLayoutLSC);
        tableLayout = (TableLayout) findViewById(R.id.table_layout_aprende);

        myRef = database.getReference().child(myAuth.getCurrentUser().getUid().toString());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()) {
                    location = dataSnapshot.child("location").getValue().toString();
                    createRows(location);


                }

                //Log.d("Éxito", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Error", "Failed to read value.", error.toException());
            }
        });


        /*myRef = database.getReference().child(myAuth.getCurrentUser().getUid().toString());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()) {
                    String location = dataSnapshot.child("location").getValue().toString();


                }

                //Log.d("Éxito", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Error", "Failed to read value.", error.toException());
            }
        });*/
    }

    public void createRows(String location) {
        StorageReference ref = mStorageRef.child("videos").child(location);
        myAuth.getCurrentUser();
        myRef = database.getReference().child(myAuth.getCurrentUser().getUid().toString());


        ref.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                if (tableLayout.getChildCount()==0){
                    int numeroTableRows;
                    if (listResult.getItems().size() % 3 == 0) {
                        numeroTableRows = listResult.getItems().size() / 3;
                    } else {
                        numeroTableRows = listResult.getItems().size() / 3 + 1;
                    }

                    for (int i = 1; numeroTableRows >= i; i++) {
                        TableRow tableRow = new TableRow(getApplicationContext());
                        tableRow.setId(i + 9876);
                        listaTableRows.add(tableRow);
                        tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        tableRow.setGravity(Gravity.CENTER);
                        tableLayout.addView(tableRow);

                    }
                    showFolders(listResult, tableLayout, location);
                }



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

    public void showFolders(ListResult listResult, TableLayout tableLayout, String location) {

        int i = 1;
        for (StorageReference video : listResult.getItems()) {
            Space espacio = new Space(getApplicationContext());
            espacio.setId(i + 123456);
            espacio.setMinimumHeight(5);
            espacio.setMinimumWidth(10);
            carpetas.add(video.getName());
            Button botonVideo = new Button(getApplicationContext());
            botonVideo.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            botonVideo.setId(i + 111222);
            botonVideo.setBackgroundColor(Color.TRANSPARENT);
            botonVideo.setTextColor(Color.parseColor("#0F80AA"));
            botonVideo.setText(video.getName().substring(0, video.getName().length() - 4));
            setButtonIcon(botonVideo, location);
            botonVideo.setTextColor(getResources().getColor(R.color.purple_200));
            System.out.println("**********" + botonVideo.getText());

            botonVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myAuth.getCurrentUser();
                    myRef = database.getReference().child(myAuth.getCurrentUser().getUid().toString());
                    //esto se debe borrar luego porque es una prueba
                    DatabaseReference reff=database.getReference().child(myAuth.getCurrentUser().getUid().toString()).child("url");
                    reff.setValue("hi");
                    // hasta aquí


                    video.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            //Toast.makeText(getApplicationContext(), uri.toString(), Toast.LENGTH_SHORT).show();
                            FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                            StorageReference mStorageRef1;
                            FirebaseAuth myAuth1 = FirebaseAuth.getInstance();
                            DatabaseReference myRef1;
                            myRef1 = database1.getReference().child(myAuth1.getCurrentUser().getUid().toString());

                            myRef1.child("url").setValue(uri.toString());
                        }

                    });
                    openPopUp();
                    //showCustomPopupMenu();



                }
            });
            int accordingTableRow;
            if (i % 3 != 0) {
                accordingTableRow = (i / 3);
            } else {
                accordingTableRow = (i) / 3 - 1;
            }

            TableRow chosenTableRow = (TableRow) findViewById(listaTableRows.get(accordingTableRow).getId());
            //System.out.println(chosenTableRow.getId());
            chosenTableRow.addView(botonVideo);
            chosenTableRow.addView(espacio);


            i++;

        }
    }

    public void setButtonIcon(Button boton, String location) {
        switch (location.toLowerCase()) {
            case "comida":
                boton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.comida, 0, 0);
                break;
            case "números":
                boton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.numeros, 0, 0);
                break;
            case "verbos":
                boton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.verbosss, 0, 0);
                break;
            default:
                boton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.hands, 0, 0);
        }

    }

    public void openPopUp() {
        startActivity(new Intent(getApplicationContext(), PopUpShowVideos.class));
    }
    private void showCustomPopupMenu() {
        mStorageRef = FirebaseStorage.getInstance().getReference();
        myRef = database.getReference().child(myAuth.getCurrentUser().getUid().toString());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()) {
                    url = dataSnapshot.child("url").getValue().toString();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Error", "Failed to read value.", error.toException());
            }
        });
        final Dialog dialog = new Dialog(Verbos.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_verbos);
        dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        lp.copyFrom(dialog.getWindow().getAttributes());
        dialog.getWindow().setAttributes(lp);
        final VideoView videoview = (VideoView) dialog.findViewById(R.id.video);

        Uri uri = Uri.parse(url);
        videoview.setVideoURI(uri);
        videoview.start();
    }


}
