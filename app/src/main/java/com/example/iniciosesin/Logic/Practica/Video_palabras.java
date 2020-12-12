package com.example.iniciosesin.Logic.Practica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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

public class Video_palabras extends AppCompatActivity {
    private StorageReference mStorageRef;
    private FirebaseAuth myAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private String nombreCarpeta;


    private VideoView video;
    private List<String> options = new ArrayList<>();
    private TextView rightOptionButton;
    private List<TextView> wrongOptionButtons = new ArrayList<>();
    private TextView boton1;
    private TextView boton2;
    private TextView boton3;
    private TextView boton4;
    private ImageView exit;
    private ConstraintLayout constraintLayout;

    private int progreso;
    private int nivel;

    private LottieAnimationView tick;
    private LottieAnimationView cross;
    private int errores, aciertos;

    private TextView aciertosTextView, erroresTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_palabras);

        try {
            aciertos = getIntent().getExtras().getInt("aciertos");
            errores = getIntent().getExtras().getInt("errores");
        } catch (Exception e) {
            errores = 0;
            aciertos = 0;
        }

        aciertosTextView = findViewById(R.id.aciertos_parejas);
        erroresTextView = findViewById(R.id.errores_parejas);
        aciertosTextView.setText(Integer.toString(aciertos));
        erroresTextView.setText(Integer.toString(errores));

        exit = findViewById(R.id.exitVideoPalabras);
        PushDownAnim.setPushDownAnimTo(exit).setScale(PushDownAnim.MODE_SCALE, 0.89f).setOnClickListener(new View.OnClickListener() {
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

        constraintLayout = findViewById(R.id.constraintLayoutVideoPalabras);
        video = findViewById(R.id.video_en_video_palabras);
        boton1 = findViewById(R.id.boton_opcion1);
        boton2 = findViewById(R.id.boton_opcion2);
        boton3 = findViewById(R.id.boton_opcion3);
        boton4 = findViewById(R.id.boton_opcion4);

        tick = findViewById(R.id.tickImageVideoPalabras);
        tick.setVisibility(View.INVISIBLE);
        cross = findViewById(R.id.crossImageVideoPalabras);
        cross.setVisibility(View.INVISIBLE);

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
                    /*MediaController mediaController = new MediaController(Video_palabras.this);
                    video.setMediaController(mediaController);
                    mediaController.setAnchorView(mediaController);*/
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
        List<TextView> botones = new ArrayList<>();
        HashMap<TextView, String> diccBotonesNombres = new HashMap<>();

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
                rightOptionButton = (TextView) boton;
            } else {
                wrongOptionButtons.add((TextView) boton);
            }
        }
    }

    private void randomButton(List<TextView> botones, HashMap diccBotonesNombres, String optionName) {
        TextView boton = botones.get((int) (Math.random() * (botones.size())));
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

    public static void restart(Context context) {

        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        context.startActivity(mainIntent);
        Runtime.getRuntime().exit(0);
    }

    public void readData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mStorageRef = FirebaseStorage.getInstance().getReference();
                myRef = database.getReference().child(myAuth.getCurrentUser().getUid().toString());
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        if (dataSnapshot.exists()) {
                            nombreCarpeta = dataSnapshot.child("location").getValue().toString();
                            progreso = Integer.parseInt(dataSnapshot.child("progreso").getValue().toString());
                            nivel = Integer.parseInt(dataSnapshot.child("nivel").getValue().toString());
                            mStorageRef = FirebaseStorage.getInstance().getReference();
                            StorageReference ref = mStorageRef.child("videos").child(nombreCarpeta);
                            myRef = database.getReference().child(myAuth.getCurrentUser().getUid().toString());
                            ref.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                                @Override
                                public void onSuccess(ListResult listResult) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            randomOptions(listResult.getItems());
                                            addAnimation(rightOptionButton);
                                            /*PushDownAnim.setPushDownAnimTo(rightOptionButton).setScale(PushDownAnim.MODE_SCALE,0.89f).setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                }
                                            });*/
                                            rightOptionButton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    rightOptionButton.setEnabled(false);
                                                    aciertos++;
                                                    updateTextView(aciertosTextView,aciertos);
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
                                                            Intent i = new Intent(getApplicationContext(), Palabra_videos.class);
                                                            i.putExtra("errores", errores);
                                                            i.putExtra("aciertos", aciertos);
                                                            startActivity(i);
                                                            finish();
                                                        }

                                                    }.start();
                                                    //Toast.makeText(Video_palabras.this, "Respuesta correcta", Toast.LENGTH_SHORT).show();

                                                }
                                            });
                                            for (TextView boton : wrongOptionButtons) {
                                                addAnimation(boton);
                                                boton.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        cross.setVisibility(View.VISIBLE);
                                                        cross.playAnimation();
                                                        errores++;
                                                        updateTextView(erroresTextView,errores);

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
                                                    }
                                                });
                                            }
                                        }
                                    });

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
        }).start();
    }

    public void recuento() {
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

        restart(getApplicationContext());

        /*Snackbar snackbar = Snackbar.make(constraintLayout, "Tuviste:\n" + aciertos + " aciertos y " + errores + " errores.", Snackbar.LENGTH_LONG);
        snackbar.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        restart(getApplicationContext());
                    }
                });

            }
        }, 3000);*/





        /*new CountDownTimer(3000, 1500) {

            @Override
            public void onTick(long millisUntilFinished) {


                //Toast.makeText(getApplicationContext(), "Tuviste:\n" + aciertos + " aciertos.\n" + errores + " errores.", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFinish() {

                restart(getApplicationContext());
            }

        }.start();*/


        //restart(getApplicationContext());

        /*new CountDownTimer(3000, 1500) {

            @Override
            public void onTick(long millisUntilFinished) {
                //Toast.makeText(getApplicationContext(), "Tuviste:\n" + aciertos + " aciertos.\n" + errores + " errores.", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFinish() {
                // do something end times 1s
                restart(getApplicationContext());
            }

        }.start();*/

    }

    public static void addAnimation(View view) {
        PushDownAnim.setPushDownAnimTo(view).setScale(PushDownAnim.MODE_SCALE, 0.89f).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public static void updateTextView(TextView view, int number) {
        view.setText(Integer.toString(number));

    }
}
