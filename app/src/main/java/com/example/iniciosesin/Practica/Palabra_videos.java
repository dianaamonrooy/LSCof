package com.example.iniciosesin.Practica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.iniciosesin.R;
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
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Palabra_videos extends AppCompatActivity {

    private StorageReference mStorageRef;
    private FirebaseAuth myAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private String nombreCarpeta;

    private TextView palabra,aciertosTextView,erroresTextView;
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
    private ImageView exit;

    private LottieAnimationView tick;
    private LottieAnimationView cross;

    private int errores,aciertos,progreso,nivel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palabra_videos);
        aciertos= getIntent().getExtras().getInt("aciertos");
        errores = getIntent().getExtras().getInt("errores");
        aciertosTextView=findViewById(R.id.aciertos_parejas);
        erroresTextView=findViewById(R.id.errores_parejas);
        Video_palabras.updateTextView(aciertosTextView,aciertos);
        Video_palabras.updateTextView(erroresTextView,errores);

        System.out.println("Estos son los errores: "+ errores);
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

        tick = findViewById(R.id.tickImagePalabraVideos);
        tick.setVisibility(View.INVISIBLE);
        cross = findViewById(R.id.crossImagePalabraVideos);
        cross.setVisibility(View.INVISIBLE);

        exit = findViewById(R.id.exitPalabraVideos);
        PushDownAnim.setPushDownAnimTo(exit).setScale(PushDownAnim.MODE_SCALE,0.89f).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuento();
            }
        });
        /*exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuento();
            }
        });*/


        mStorageRef = FirebaseStorage.getInstance().getReference();
        myRef = database.getReference().child(myAuth.getCurrentUser().getUid().toString());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()) {
                    progreso = Integer.parseInt(dataSnapshot.child("progreso").getValue().toString());
                    nivel = Integer.parseInt(dataSnapshot.child("nivel").getValue().toString());
                    nombreCarpeta = dataSnapshot.child("location").getValue().toString();
                    mStorageRef = FirebaseStorage.getInstance().getReference();
                    StorageReference ref = mStorageRef.child("videos").child(nombreCarpeta);
                    myRef = database.getReference().child(myAuth.getCurrentUser().getUid().toString());
                    ref.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                        @Override
                        public void onSuccess(ListResult listResult) {
                            randomOptions(listResult.getItems());
                            Video_palabras.addAnimation(rightOptionVideo);
                            rightOptionVideo.setOnTouchListener(new View.OnTouchListener() {
                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    //Toast.makeText(Palabra_videos.this, "Respuesta correcta", Toast.LENGTH_SHORT).show();
                                    rightOptionVideo.setEnabled(false);
                                    aciertos++;
                                    Video_palabras.updateTextView(aciertosTextView,aciertos);
                                    tick.setVisibility(View.VISIBLE);
                                    tick.playAnimation();

                                    new CountDownTimer(1000, 1000) {

                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                            // do something after 1s
                                        }

                                        @Override
                                        public void onFinish() {
                                            // do something end times 1s
                                            finishAffinity();
                                            Intent ii = new Intent(getApplicationContext(), Parejas.class);
                                            ii.putExtra("errores",errores);
                                            ii.putExtra("aciertos",aciertos);
                                            startActivity(ii);
                                        }

                                    }.start();
                                    rightOptionVideo.setOnTouchListener(null);
                                    return false;
                                }
                            });

                            for (VideoView video : wrongOptionVideos) {
                                Video_palabras.addAnimation(video);
                                video.setOnTouchListener(new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {

                                        //Toast.makeText(Palabra_videos.this, "Respuesta incorrecta", Toast.LENGTH_SHORT).show();
                                        errores ++;
                                        cross.setVisibility(View.VISIBLE);
                                        cross.playAnimation();
                                        Video_palabras.updateTextView(erroresTextView,errores);

                                        new CountDownTimer(1000, 1000) {

                                            @Override
                                            public void onTick(long millisUntilFinished) {
                                                // do something after 1s
                                            }

                                            @Override
                                            public void onFinish() {
                                                // do something end times 1s
                                                cross.setVisibility(View.INVISIBLE);
                                            }

                                        }.start();
                                        video.setOnTouchListener(null);
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
                /*MediaController mediaController = new MediaController(Palabra_videos.this);
                video.setMediaController(mediaController);
                mediaController.setAnchorView(video);*/
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
    public void recuento(){
        myRef = database.getReference().child(myAuth.getCurrentUser().getUid().toString());

        int total = aciertos - errores;

        int newProgreso = progreso + total;
        if (newProgreso < 0) {
            newProgreso = 0;
        }
        if (newProgreso > nivel * 10) {
            nivel++;
            newProgreso = nivel * 10 - newProgreso;
        }


        myRef.child("progreso").setValue(newProgreso);
        myRef.child("nivel").setValue(nivel);
        Video_palabras.restart(getApplicationContext());


        /*new CountDownTimer(3000, 1500) {

            @Override
            public void onTick(long millisUntilFinished) {
                Toast.makeText(getApplicationContext(),"Tuviste:\n"+aciertos+" aciertos.\n"+errores+" errores.",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinish() {
                // do something end times 1s
                Video_palabras.restart(getApplicationContext());
            }

        }.start();*/

    }
}