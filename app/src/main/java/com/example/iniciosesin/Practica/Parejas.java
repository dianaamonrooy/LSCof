package com.example.iniciosesin.Practica;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iniciosesin.R;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.iniciosesin.R.drawable.buttonroundcorrect;
import static com.example.iniciosesin.R.drawable.buttonroundempty;

public class Parejas extends AppCompatActivity {
    private StorageReference mStorageRef;
    private FirebaseAuth myAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private String nombreCarpeta;
    private HashMap<VideoView, StorageReference> diccBotonesNombres = new HashMap<>();
    private List<VideoView> videos = new ArrayList<>();
    private HashMap<Integer, String> indexedAnswers = new HashMap<>();
    private TextView palabra1;
    private TextView palabra2;
    private TextView palabra3;
    private TextView palabra4;
    private ArrayList<Float> xCoordinates = new ArrayList<>();
    private ArrayList<Float> yCoordinates = new ArrayList<>();
    private ArrayList<TextView> palabras = new ArrayList<TextView>();
    private ArrayList<TextView> containers = new ArrayList<TextView>();
    private HashMap<Integer, TextView> indexedContain = new HashMap<>();
    private HashMap<TextView, Integer> indexedContainInverse = new HashMap<>();
    private VideoView video1;
    private VideoView video2;
    private VideoView video3;
    private VideoView video4;
    private TextView contain1;
    private TextView contain2;
    private TextView contain3;
    private TextView contain4;
    private Button buttonComprobar;
    private List<StorageReference> options = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parejas);
        palabra1 = findViewById(R.id.palabra1);
        palabra2 = findViewById(R.id.palabra2);
        palabra3 = findViewById(R.id.palabra3);
        palabra4 = findViewById(R.id.palabra4);
        video1 = findViewById(R.id.video1Pairs);
        video2 = findViewById(R.id.video2Pairs);
        video3 = findViewById(R.id.video3Pairs);
        video4 = findViewById(R.id.video4Pairs);
        buttonComprobar = findViewById(R.id.checkButtonPairs);
        contain1 = findViewById(R.id.textView1Pairs);
        contain2 = findViewById(R.id.textView2Pairs);
        contain3 = findViewById(R.id.textView3Pairs);
        contain4 = findViewById(R.id.textView4Pairs);
        palabra1.setOnLongClickListener(longClickListener);
        palabra2.setOnLongClickListener(longClickListener);
        palabra3.setOnLongClickListener(longClickListener);
        palabra4.setOnLongClickListener(longClickListener);
        contain1.setOnDragListener(dragListener);
        contain2.setOnDragListener(dragListener);
        contain3.setOnDragListener(dragListener);
        contain4.setOnDragListener(dragListener);
        buttonComprobar.setOnClickListener(onButtonClickListener);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        myRef = database.getReference().child(myAuth.getCurrentUser().getUid().toString());
        ///

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

    /////
    View.OnClickListener onButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            System.out.println(indexedContain);
            try {
                comprobar();
            } catch (Exception e) {
                Toast.makeText(Parejas.this, "Arrastra las palabras debajo del video correspondiente", Toast.LENGTH_SHORT).show();

            }

        }
    };

    public void comprobar() {
        boolean correcto = true;
        if (indexedContain.size() != 4) {
            throw new IllegalStateException("All fields must be filled in");
        }
        for (int i = 0; i < 4; ++i) {
            TextView view = indexedContain.get(i);
            TextView x = containers.get(i);
            String droppedName = ((String) view.getText()).trim();
            String neededAnswer = indexedAnswers.get(containers.indexOf(x)).trim();
            System.out.println("Soltado " + droppedName);
            System.out.println("Necesitado " + neededAnswer);
            Boolean prueba = neededAnswer.equals(droppedName);
            System.out.println(prueba);
            if (prueba) {
                x.setBackgroundResource(buttonroundcorrect);
            } else {
                x.setBackgroundResource(buttonroundempty);
                correcto = false;


            }
        }
        if (correcto) {
            Toast.makeText(Parejas.this, "Respuesta Correcta", Toast.LENGTH_SHORT).show();
            Video_palabras.restart(getApplicationContext());
        }
    }

    public void randomOptions(List<StorageReference> items) {
        palabras.add(palabra1);
        palabras.add(palabra2);
        palabras.add(palabra3);
        palabras.add(palabra4);

        try {
            for (int i = 0; i < 4; ++i) {
                xCoordinates.add(palabras.get(i).getX());
                yCoordinates.add(palabras.get(i).getY());
                StorageReference chosenItem = items.get((int) (Math.random() * (items.size())));
                while (options.contains(chosenItem)) {
                    chosenItem = items.get((int) (Math.random() * (items.size())));
                }
                palabras.get(i).setText(chosenItem.getName().substring(0, chosenItem.getName().length() - 4));
                options.add(chosenItem);
            }
            setURLToVideos(options);
        } catch (Exception e) {

        }


    }

    private void setURLToVideos(List<StorageReference> opciones) {


        videos.add(video1);
        videos.add(video2);
        videos.add(video3);
        videos.add(video4);
        containers.add(contain1);
        containers.add(contain2);
        containers.add(contain3);
        containers.add(contain4);


        for (int i = 0; i < 4; i++) {

            randomVideo(videos, diccBotonesNombres, opciones.get(i));
        }
    }

    private void randomVideo(List<VideoView> videos, HashMap diccBotonesNombres, StorageReference optionName) {
        int ran = (int) (Math.random() * (videos.size()));
        VideoView video = videos.get(ran);
        while (!diccBotonesNombres.isEmpty() && diccBotonesNombres.containsKey(video)) {
            ran = (int) (Math.random() * (videos.size()));
            video = videos.get(ran);
        }
        addUrlToVideo(video, optionName);
        diccBotonesNombres.put(video, optionName);
        String answerTotalName = optionName.getName();
        String answerName = answerTotalName.substring(0, answerTotalName.length() - 4);
        indexedAnswers.put(ran, answerName);


    }

    private void addUrlToVideo(VideoView video, StorageReference item) {
        item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                /*MediaController mediaController = new MediaController(Parejas.this);
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


    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View view) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder myShadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, myShadowBuilder, view, 0);
            return true;
        }
    };

    View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            TextView x = (TextView) v;
            int dragEvent = event.getAction();
            switch (dragEvent) {
                case DragEvent.ACTION_DRAG_ENTERED: {

                    break;
                }
                case DragEvent.ACTION_DRAG_EXITED: {
                    break;
                }
                case DragEvent.ACTION_DROP: {
                    try {
                        TextView view = (TextView) event.getLocalState();
                        System.out.println("Obtenido");
                        /* Le asigna una llave al contain */
                        int containerKey = containers.indexOf(x);
                        if (indexedContainInverse.containsKey(view)) {
                            int delkey = indexedContainInverse.get(view);
                            System.out.println("Removiendo: " + delkey + " + " + indexedContain.get(delkey));
                            indexedContain.remove(delkey);
                            indexedContainInverse.remove(view);
                        }

                        if (indexedContain.containsKey(containerKey)) {
                            TextView palabraDentro = indexedContain.get(containerKey);
                            /* Si hay una palabra dentro, la mueve a su posición original*/
                            palabraDentro.setX(xCoordinates.get(palabras.indexOf(palabraDentro)));
                            palabraDentro.setY(yCoordinates.get(palabras.indexOf(palabraDentro)));
                            indexedContainInverse.remove(palabraDentro);
                        }
                        System.out.println("Poniendo: " + containerKey + " + " + view.getText());
                        indexedContain.put(containerKey, view);
                        indexedContainInverse.put(view, containerKey);

                        view.setX(x.getX() + 5);
                        view.setY(x.getY() + 5);
                        view.bringToFront();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    break;
                }

            }

            return true;
        }
    };

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Video_palabras.restart(getApplicationContext());

    }
}