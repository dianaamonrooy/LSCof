package com.example.iniciosesin.Logic.Actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.graphics.PorterDuff;
import android.os.Bundle;

import com.example.iniciosesin.Logic.ControladorTab.PagerController;

import com.example.iniciosesin.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class TabbedActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem culturaSorda, LSC, userInfo;
    private PagerController pagerController;
    private StorageReference mStorageRef;
    private ArrayList<String> carpetas;
    private FirebaseAuth myAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        culturaSorda = findViewById(R.id.cultura_sorda);
        LSC = findViewById(R.id.LSC);
        userInfo = findViewById(R.id.user_info);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        carpetas = new ArrayList<>();
        StorageReference ref = mStorageRef.child("videos");
        myAuth.getCurrentUser();
        myRef = database.getReference().child(myAuth.getCurrentUser().getUid().toString());

        pagerController = new PagerController(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerController);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.white);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition()==0){
                    pagerController.notifyDataSetChanged();
                }
                if (tab.getPosition()==1){
                    pagerController.notifyDataSetChanged();
                }
                if (tab.getPosition()==2){
                    pagerController.notifyDataSetChanged();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.purple_700);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }
}