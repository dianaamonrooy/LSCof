package com.example.iniciosesin.interfaces_cultura;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iniciosesin.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.iniciosesin.interfaces_cultura.datos_culturasorda.Leyes;
import com.example.iniciosesin.ControladorTab.CulturaSorda;

import java.util.ArrayList;
import java.util.List;

public class VentanaLeyes extends AppCompatActivity {
        ListView listView;
        static ArrayList<Leyes> arraylist;
        static ArrayList<String> arraylist2;
        String origin;

        @Override
        protected void onCreate (Bundle savedInstaceState){
                super.onCreate (savedInstaceState);
                setContentView(R.layout.fragment_ventana_leyes);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                listView=(ListView)findViewById(R.id.listview);

                recibirClikEvent();

                if(origin.equals("Leyes")){
                        arraylist = CulturaSorda.getLeyes();
                }else{
                        arraylist = CulturaSorda.getHistoria();
                }

                arraylist2= makeListArray(arraylist);
                ArrayAdapter arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,arraylist2);
                listView.setAdapter(arrayAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                                Intent intent = new Intent(VentanaLeyes.this, PopUpListas.class);
                                intent.putExtra("position", position);
                                intent.putParcelableArrayListExtra("arraylist", (ArrayList<? extends Parcelable>) arraylist);
                                startActivity(intent);


                        }
                });
        }

        public static ArrayList<String> makeListArray(ArrayList<Leyes> infoLeyes){
                ArrayList<String> listLeyes = new ArrayList<>();

                for (int i=0; i<infoLeyes.size(); i++){
                        listLeyes.add(infoLeyes.get(i).getName());
                }

                return listLeyes;
        }


        public void recibirClikEvent(){
                Intent extras = getIntent();
                origin  = extras.getStringExtra("clickEvent");
        }








}
