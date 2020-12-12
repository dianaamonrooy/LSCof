package com.example.iniciosesin.UI;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.example.iniciosesin.R;
import com.example.iniciosesin.Data.Leyes;

import java.util.ArrayList;


public class PopUpListas extends Activity {
    static TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popuplistas);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.4));

        recibirDatos();


    }

    private static String getInfo(ArrayList<Leyes> array, int i){
        String text = array.get(i).toString();
        return text;
    }

    private void recibirDatos(){
        Bundle extras = getIntent().getExtras();
        int pos  = extras.getInt("position");
        ArrayList<Leyes> array = extras.getParcelableArrayList("arraylist");

        info = (TextView)findViewById(R.id.textpopup);
        info.setText(getInfo(array,pos));
    }
}
