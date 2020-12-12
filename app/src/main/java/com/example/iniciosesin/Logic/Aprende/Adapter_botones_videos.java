package com.example.iniciosesin.Logic.Aprende;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.example.iniciosesin.Data.Model;
import com.example.iniciosesin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class Adapter_botones_videos extends PagerAdapter {

    private List<Model> models;
    private LayoutInflater layoutInflater;
    private Context context;
    private boolean onClick;


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private StorageReference mStorageRef;
    private FirebaseAuth myAuth = FirebaseAuth.getInstance();
    private DatabaseReference myRef;
    private String url;

    public Adapter_botones_videos(List<Model> models, Context context) {
        this.models = models;
        this.context = context;

    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item, container, false);

        onClick = false;

        ImageView imageView;
        TextView title, desc;

        imageView = view.findViewById(R.id.image_item);
        title = view.findViewById(R.id.title);
        desc = view.findViewById(R.id.desc);

        imageView.setImageResource(models.get(position).getImage());
        title.setText(models.get(position).getTitle());
        desc.setText(models.get(position).getDesc());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*onClick = true;
                mStorageRef = FirebaseStorage.getInstance().getReference();
                myRef = database.getReference().child(myAuth.getCurrentUser().getUid().toString());
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        if (dataSnapshot.exists()) {
                            if (onClick == true) {
                                url = dataSnapshot.child("url").getValue().toString();
                                Intent intent = new Intent(context, PopUpShowVideos.class);
                                intent.putExtra("url", url);
                                onClick=false;
                                context.startActivity(intent);
                                //((Activity)context).finish();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("Error", "Failed to read value.", error.toException());
                    }
                });

                // finish();*/
            }
        });

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}