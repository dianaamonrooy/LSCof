package com.example.iniciosesin.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.iniciosesin.Logic.Practica.Video_palabras;
import com.example.iniciosesin.R;

public class PopUpShowReferences extends AppCompatActivity {
    private ImageView imagen;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_show_references);
        imagen = findViewById(R.id.creative_commons);
        textView = findViewById(R.id.link_referencias);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .4));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        //params.gravity = Gravity.CENTER;
        //params.gravity = Gravity.TOP;
        //params.gravity = Gravity.CENTER_HORIZONTAL;
        params.x = 0;
        params.y = 40;
        getWindow().setAttributes(params);
        Video_palabras.addAnimation(imagen);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://creativecommons.org/licenses/by-nc-nd/4.0/deed.es");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


    }
}