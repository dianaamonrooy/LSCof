package com.example.iniciosesin.popUps;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.TableLayout;
import android.widget.VideoView;

import com.example.iniciosesin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class PopUpShowVideos extends AppCompatActivity {
    VideoView video;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private StorageReference mStorageRef;
    private ArrayList<String> carpetas;
    private FirebaseAuth myAuth = FirebaseAuth.getInstance();
    private DatabaseReference myRef;
    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_show_videos);
        DisplayMetrics dm =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int)(width*0.8f),(int)(height*.2));
        WindowManager.LayoutParams params=getWindow().getAttributes();
        //params.gravity = Gravity.CENTER;
        //params.gravity = Gravity.TOP;
        //params.gravity = Gravity.CENTER_HORIZONTAL;
        params.x=0;
        params.y=40;
        getWindow().setAttributes(params);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        myRef = database.getReference().child(myAuth.getCurrentUser().getUid().toString());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()) {
                    url = dataSnapshot.child("url").getValue().toString();
                    MediaController mediaController = new MediaController(PopUpShowVideos.this);
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
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Error", "Failed to read value.", error.toException());
            }
        });

    }
}