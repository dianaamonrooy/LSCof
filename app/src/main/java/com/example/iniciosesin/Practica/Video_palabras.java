package com.example.iniciosesin.Practica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.iniciosesin.ControladorTab.LSC;
import com.example.iniciosesin.R;
import com.example.iniciosesin.actividades.TabbedActivity;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Video_palabras extends AppCompatActivity {
    private StorageReference mStorageRef;
    private FirebaseAuth myAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private String nombreCarpeta;


    private VideoView video;
    private List<String> options = new ArrayList<>();
    private Button rightOptionButton;
    private List<Button> wrongOptionButtons = new ArrayList<>();
    private Button boton1;
    private Button boton2;
    private Button boton3;
    private Button boton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_palabras);
        video = findViewById(R.id.video_en_video_palabras);
        boton1 = findViewById(R.id.boton_opcion1);
        boton2 = findViewById(R.id.boton_opcion2);
        boton3 = findViewById(R.id.boton_opcion3);
        boton4 = findViewById(R.id.boton_opcion4);

        onResume();
        readData();


    }

    public void randomOptions(List<StorageReference> items) {
        try {
            StorageReference chosenItem = items.get((int) (Math.random() * (items.size())));
            options.add(chosenItem.getName());

            chosenItem.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    MediaController mediaController = new MediaController(Video_palabras.this);
                    video.setMediaController(mediaController);
                    mediaController.setAnchorView(video);
                    video.setVideoURI(uri);
                    video.requestFocus();

                    video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.setLooping(true);
                            video.start();
                        }
                    });
                    video.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        @Override
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                            Log.d("video", "setOnErrorListener ");
                            return true;
                        }
                    });
                }
            });
            for (int i = 0; i < 3; i++) {
                addOptions(items, options);
            }
            setTextToButtons(options);
        } catch (Exception e) {

        }


    }

    private void setTextToButtons(List<String> opciones) {
        List<Button> botones = new ArrayList<>();
        HashMap<Button, String> diccBotonesNombres = new HashMap<>();

        botones.add(boton1);
        botones.add(boton2);
        botones.add(boton3);
        botones.add(boton4);

        for (int i = 0; i < 4; i++) {
            randomButton(botones, diccBotonesNombres, opciones.get(i));
        }
        Object[] arrayBotones = diccBotonesNombres.keySet().toArray();
        for (Object boton : arrayBotones) {
            if (diccBotonesNombres.get(boton) == opciones.get(0)) {
                rightOptionButton = (Button) boton;
            } else {
                wrongOptionButtons.add((Button) boton);
            }
        }
    }

    private void randomButton(List<Button> botones, HashMap diccBotonesNombres, String optionName) {
        Button boton = botones.get((int) (Math.random() * (botones.size())));
        while (!diccBotonesNombres.isEmpty() && diccBotonesNombres.containsKey(boton)) {
            boton = botones.get((int) (Math.random() * (botones.size())));
        }
        diccBotonesNombres.put(boton, optionName);
        boton.setText(optionName.substring(0, optionName.length() - 4));
    }

    public void addOptions(List<StorageReference> items, List<String> opciones) {
        String newOption = items.get((int) (Math.random() * (items.size()))).getName();
        while (opciones.contains(newOption)) {
            newOption = items.get((int) (Math.random() * (items.size()))).getName();
        }
        opciones.add(newOption);
    }

    @Override
    public void onBackPressed() {
        restart(getApplicationContext());
    }

    public static void restart(Context context){
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        context.startActivity(mainIntent);
        Runtime.getRuntime().exit(0);
    }

    public void readData() {
        /*new Thread(new Runnable() {
            @Override
            public void run() {*/
                mStorageRef = FirebaseStorage.getInstance().getReference();
                myRef = database.getReference().child(myAuth.getCurrentUser().getUid().toString());
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        if (dataSnapshot.exists()) {
                            nombreCarpeta = dataSnapshot.child("location").getValue().toString();
                            mStorageRef = FirebaseStorage.getInstance().getReference();
                            StorageReference ref = mStorageRef.child("videos").child(nombreCarpeta);
                            myRef = database.getReference().child(myAuth.getCurrentUser().getUid().toString());
                            ref.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                                @Override
                                public void onSuccess(ListResult listResult) {
                                    randomOptions(listResult.getItems());

                                    /*runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {*/
                                            rightOptionButton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Toast.makeText(Video_palabras.this, "Respuesta correcta", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getApplicationContext(), Palabra_videos.class));
                                                    finish();
                                                }
                                            });
                                            for (Button boton : wrongOptionButtons) {
                                                boton.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        Toast.makeText(Video_palabras.this, "Respuesta incorrecta", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }
                                    });

                                }
                            /*});
                        }*/
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("Error", "Failed to read value.", error.toException());
                    }
                });

            }
        /*}).start();
    }*/
}
