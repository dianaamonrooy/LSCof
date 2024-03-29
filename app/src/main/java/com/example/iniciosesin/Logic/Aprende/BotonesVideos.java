package com.example.iniciosesin.Logic.Aprende;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.iniciosesin.Data.Model;
import com.example.iniciosesin.Logic.Practica.Video_palabras;
import com.example.iniciosesin.R;
import com.example.iniciosesin.UI.PopUpShowVideos;
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
import java.util.List;

public class BotonesVideos extends AppCompatActivity {
    private StorageReference mStorageRef;
    private FirebaseAuth myAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private String nombreCarpeta;

    private ViewPager viewPager;
    private Adapter_botones_videos adapter;
    private List<Model> models;
    private List<String> nombresVideos;
    private Integer[] colors = null;
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    private int pagePosition = 0;
    private ListResult ButtonsNamesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botones_videos);
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
                    listAll(ref);

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Error", "Failed to read value.", error.toException());
            }
        });


        //nombreCarpeta = getIntent().getStringExtra("nombreCarpeta");


        //myRef.child("location").setValue("Aprende");


    }

    public int setButtonImage(StorageReference video) {
        switch (video.getName().toLowerCase().charAt(0)) {
            case 'a':
                return R.drawable.letraa;
            case 'b':
                return R.drawable.letrab;
            case 'c':
                return R.drawable.letrac;
            case 'd':
                return R.drawable.letrad;
            case 'e':
                return R.drawable.letrae;
            case 'f':
                return R.drawable.letraf;
            case 'g':
                return R.drawable.letrag;
            case 'h':
                return R.drawable.letrah;
            case 'i':
                return R.drawable.letrai;
            case 'j':
                return R.drawable.letraj;
            case 'k':
                return R.drawable.letrak;
            case 'l':
                return R.drawable.letral;
            case 'm':
                return R.drawable.letram;
            case 'n':
                return R.drawable.letran;
            case 'ñ':
                return R.drawable.letranie;
            case 'o':
                return R.drawable.letrao;
            case 'p':
                return R.drawable.letrap;
            case 'q':
                return R.drawable.letraq;
            case 'r':
                return R.drawable.letrar;
            case 's':
                return R.drawable.letras;
            case 't':
                return R.drawable.letrat;
            case 'u':
                return R.drawable.letrau;
            case 'v':
                return R.drawable.letrav;
            case 'w':
                return R.drawable.letraw;
            case 'x':
                return R.drawable.letrax;
            case 'y':
                return R.drawable.letray;
            case 'z':
                return R.drawable.letraz;
            default:
                return R.drawable.verbosss;

        }


    }

    public void listAll(StorageReference ref) {
        ref.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                ButtonsNamesList = listResult;
                models = new ArrayList<>();
                nombresVideos = new ArrayList<>();
                for (StorageReference video : listResult.getItems()) {
                    nombresVideos.add(video.getName());
                    models.add(new Model(setButtonImage(video), video.getName().substring(0, video.getName().length() - 4), "Haz clic para conocer cómo se dice en LSC"));
                }

                adapter = new Adapter_botones_videos(models, BotonesVideos.this);

                viewPager = findViewById(R.id.viewPager_botones_videos);
                viewPager.setAdapter(adapter);
                viewPager.setPadding(130, 0, 130, 0);


                Integer[] colors_temp = {
                        getResources().getColor(R.color.color1),
                        //getResources().getColor(R.color.color2),
                        getResources().getColor(R.color.color3),
                        getResources().getColor(R.color.color4),
                };

                colors = colors_temp;

                ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        if ((position%colors.length)!=(colors.length-1)){
                            viewPager.setBackgroundColor(
                                    (Integer) argbEvaluator.evaluate(
                                            positionOffset,
                                            colors[position%colors.length],
                                            colors[(position%colors.length)+1]
                                    )
                            );

                        }else{
                            viewPager.setBackgroundColor(
                                    (Integer) argbEvaluator.evaluate(
                                            positionOffset,
                                            colors[position%colors.length],
                                            colors[((position+1)%colors.length)]
                                    )
                            );
                        }
                        /*if (position < (adapter.getCount() - 1) && position < (colors.length - 1)) {
                            int color1 = colors[(position + 1) % colors.length];
                            int color2 = colors[(position + 1) % colors.length - 1];
                            viewPager.setBackgroundColor(
                                    (Integer) argbEvaluator.evaluate(
                                            positionOffset,
                                            colors[(position + 1) % colors.length-1],
                                            colors[(position + 1) % colors.length]
                                    )
                            );
                        } else {
                            viewPager.setBackgroundColor(colors[colors.length-1]);
                        }*/
                        Button boton_reprod_video = findViewById(R.id.boton_video);
                        Video_palabras.addAnimation(boton_reprod_video);
                        boton_reprod_video.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pagePosition = position;
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        StorageReference video = listResult.getItems().get(position);
                                        video.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                //Toast.makeText(getApplicationContext(), uri.toString(), Toast.LENGTH_SHORT).show();
                                                myRef = database.getReference().child(myAuth.getCurrentUser().getUid().toString());
                                                myRef.child("url").setValue(uri.toString());
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        onPause();

                                                        Intent i = new Intent(getApplicationContext(), PopUpShowVideos.class);
                                                        startActivity(i);

                                                    }
                                                });
                                            }
                                        });
                                    }
                                }).start();
                            }
                        });
                    }

                    @Override
                    public void onPageSelected(int position) {
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                };
                viewPager.addOnPageChangeListener(pageChangeListener);
                pageChangeListener.onPageSelected(pagePosition);
            }
        });
    }
}
