package com.example.iniciosesin.Aprende;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.iniciosesin.R;
import com.example.iniciosesin.popUps.PopUpShowVideos;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ShowVideos extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private StorageReference mStorageRef;
    private ArrayList<String> carpetas;
    private FirebaseAuth myAuth = FirebaseAuth.getInstance();
    private DatabaseReference myRef;
    private String url;
    private VideoView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_videos);
        url=getIntent().getStringExtra("url");
        MediaController mediaController = new MediaController(ShowVideos.this);
        video = findViewById(R.id.video);
        video.setMediaController(mediaController);
        mediaController.setAnchorView(video);
        Uri uri = Uri.parse(url);
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
    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(ShowVideos.this,BotonesVideos.class);
        startActivity(setIntent);
        finish();

    }
}