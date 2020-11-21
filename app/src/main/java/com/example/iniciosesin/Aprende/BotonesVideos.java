package com.example.iniciosesin.Aprende;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

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
    private Integer[] colors = null;
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();

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
    public int setButtonImage(StorageReference video){
        switch (video.getName().toLowerCase().charAt(0)){
            case 'a':
                return R.drawable.letraa;
            case 'n':
                return R.drawable.letran;
            default:
                return R.drawable.verbosss;

        }


    }
    public void listAll(StorageReference ref){
        ref.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                models = new ArrayList<>();
                for (StorageReference video : listResult.getItems()) {
                    models.add(new Model(setButtonImage(video), video.getName().substring(0, video.getName().length() - 4), "Haz clic para conocer cómo se dice en LSC"));
                }

                adapter = new Adapter_botones_videos(models, BotonesVideos.this);

                viewPager = findViewById(R.id.viewPager_botones_videos);
                viewPager.setAdapter(adapter);
                viewPager.setPadding(130, 0, 130, 0);

                Integer[] colors_temp = {
                        getResources().getColor(R.color.color1),
                        //getResources().getColor(R.color.color2),
                        //getResources().getColor(R.color.color3),
                        getResources().getColor(R.color.color4)
                };

                colors = colors_temp;

                //viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

                /*CompositePageTransformer transformer = new CompositePageTransformer();
                transformer.addTransformer(new ViewPager2.PageTransformer() {
                    @Override
                    public void transformPage(@NonNull View page, float position) {
                        if (position < (adapter.getItemCount() - 1) && position < (colors.length - 1)) {
                            viewPager.setBackgroundColor(

                                    (Integer) argbEvaluator.evaluate(
                                            position,
                                            colors[(int)position],
                                            colors[(int)position + 1]
                                    )
                            );
                        } else {
                            viewPager.setBackgroundColor(colors[colors.length - 1]);
                        }
                        float v=1 - Math.abs(position);
                        page.setScaleY(0.8f+ v*0.2f);

                    }
                });*/

                viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        if (position < (adapter.getCount() - 1) && position < (colors.length - 1)) {
                            viewPager.setBackgroundColor(

                                    (Integer) argbEvaluator.evaluate(
                                            positionOffset,
                                            colors[position],
                                            colors[position + 1]
                                    )
                            );
                        } else {
                            viewPager.setBackgroundColor(colors[colors.length - 1]);
                        }
                    }

                    @Override
                    public void onPageSelected(int position) {
                        StorageReference video = listResult.getItems().get(position);
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

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            }
        });
    }

}