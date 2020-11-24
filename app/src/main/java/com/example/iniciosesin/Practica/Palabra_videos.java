package com.example.iniciosesin.Practica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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

public class Palabra_videos extends AppCompatActivity {

    private StorageReference mStorageRef;
    private FirebaseAuth myAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private String nombreCarpeta;

    private TextView palabra;
    private VideoView video1;
    private VideoView video2;
    private VideoView video3;
    private VideoView video4;


    private VideoView video;
    private List<StorageReference> options = new ArrayList<>();
    private VideoView rightOptionVideo;
    private List<VideoView> wrongOptionVideos = new ArrayList<>();
    private Button boton1;
    private Button boton2;
    private Button boton3;
    private Button boton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palabra_videos);
        palabra = findViewById(R.id.chosen_word_palabra_videos);
        video1 = findViewById(R.id.video1_palabra_videos);
        video2 = findViewById(R.id.video2_palabra_videos);
        video3 = findViewById(R.id.video3_palabra_videos);
        video4 = findViewById(R.id.video4_palabra_videos);

        video = findViewById(R.id.video_en_video_palabras);
        boton1 = findViewById(R.id.boton_opcion1);
        boton2 = findViewById(R.id.boton_opcion2);
        boton3 = findViewById(R.id.boton_opcion3);
        boton4 = findViewById(R.id.boton_opcion4);


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
                            rightOptionVideo.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    Toast.makeText(Palabra_videos.this, "Respuesta correcta", Toast.LENGTH_SHORT).show();
                                    finishAffinity();
                                    startActivity(new Intent(getApplicationContext(), Parejas.class));
                                    //startActivity(new Intent (getApplicationContext(),TabbedActivity.class));
                                    //finish();
                                    return false;
                                }
                            });

                            for (VideoView video : wrongOptionVideos) {
                                video.setOnTouchListener(new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        Toast.makeText(Palabra_videos.this, "Respuesta incorrecta", Toast.LENGTH_SHORT).show();
                                        return false;
                                    }
                                });

                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Error", "Failed to read value.", error.toException());
            }
        });
    }

    public void randomOptions(List<StorageReference> items) {
        try {
            StorageReference chosenItem = items.get((int) (Math.random() * (items.size())));
            palabra.setText(chosenItem.getName().substring(0, chosenItem.getName().length() - 4));
            options.add(chosenItem);

            for (int i = 0; i < 3; i++) {
                addOptions(items, options);
            }
            setURLToVideos(options);

        } catch (Exception e) {

        }


    }

    private void setURLToVideos(List<StorageReference> opciones) {
        List<VideoView> videos = new ArrayList<>();
        HashMap<VideoView, StorageReference> diccBotonesNombres = new HashMap<>();

        videos.add(video1);
        videos.add(video2);
        videos.add(video3);
        videos.add(video4);

        for (int i = 0; i < 4; i++) {
            randomVideo(videos, diccBotonesNombres, opciones.get(i));
        }
        Object[] arrayVideos = diccBotonesNombres.keySet().toArray();
        for (Object video : arrayVideos) {
            if (diccBotonesNombres.get(video) == opciones.get(0)) {
                rightOptionVideo = (VideoView) video;
            } else {
                wrongOptionVideos.add((VideoView) video);
            }
        }
    }

    private void addUrlToVideo(VideoView video, StorageReference item) {
        item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                MediaController mediaController = new MediaController(Palabra_videos.this);
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


    }

    private void randomVideo(List<VideoView> videos, HashMap diccBotonesNombres, StorageReference optionName) {
        VideoView video = videos.get((int) (Math.random() * (videos.size())));
        while (!diccBotonesNombres.isEmpty() && diccBotonesNombres.containsKey(video)) {
            video = videos.get((int) (Math.random() * (videos.size())));
        }
        addUrlToVideo(video, optionName);
        diccBotonesNombres.put(video, optionName);


    }

    public void addOptions(List<StorageReference> items, List<StorageReference> opciones) {
        StorageReference newOption = items.get((int) (Math.random() * (items.size())));
        while (opciones.contains(newOption)) {
            newOption = items.get((int) (Math.random() * (items.size())));
        }
        opciones.add(newOption);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Video_palabras.restart(getApplicationContext());

    }
}