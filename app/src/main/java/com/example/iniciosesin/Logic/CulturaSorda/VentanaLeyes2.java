package com.example.iniciosesin.Logic.CulturaSorda;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.iniciosesin.Data.Model;
import com.example.iniciosesin.Logic.ControladorTab.CulturaSorda;
import com.example.iniciosesin.Logic.Practica.Video_palabras;
import com.example.iniciosesin.R;
import com.example.iniciosesin.Data.Leyes;
import com.example.iniciosesin.UI.PopUpListas;

import java.util.ArrayList;

public class VentanaLeyes2 extends AppCompatActivity {
    static ArrayList<Leyes> arraylist;
    static ArrayList<Model> models;
    String origin;
    AdapterVentanaLeyes2 adapter;
    ViewPager viewPager;
    private Integer[] colors = null;
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.activity_ventana_leyes_2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        recibirClikEvent();

        if (origin.equals("Leyes")) {
            arraylist = CulturaSorda.getLeyes();
        } else {
            arraylist = CulturaSorda.getHistoria();
        }

        models = makeListArray(arraylist);
        adapter = new AdapterVentanaLeyes2(models, VentanaLeyes2.this);
        viewPager = findViewById(R.id.viewPager_LSC);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130, 0, 130, 0);

        Integer[] colors_temp = {
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color4)
        };

        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if ((position % colors.length) != (colors.length - 1)) {
                    viewPager.setBackgroundColor(
                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position % colors.length],
                                    colors[(position % colors.length) + 1]
                            )
                    );

                } else {
                    viewPager.setBackgroundColor(
                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position % colors.length],
                                    colors[((position + 1) % colors.length)]
                            )
                    );
                }
                Button descubre = findViewById(R.id.aprende_ventanaLeyes2);
                Video_palabras.addAnimation(descubre);
                descubre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(VentanaLeyes2.this, PopUpListas.class);
                        intent.putExtra("position", position);
                        intent.putParcelableArrayListExtra("arraylist", (ArrayList<? extends Parcelable>) arraylist);
                        startActivity(intent);
                    }
                });


            }


            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        /*ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arraylist2);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(VentanaLeyes2.this, PopUpListas.class);
                intent.putExtra("position", position);
                intent.putParcelableArrayListExtra("arraylist", (ArrayList<? extends Parcelable>) arraylist);
                startActivity(intent);


            }
        });*/
    }

    public static ArrayList<Model> makeListArray(ArrayList<Leyes> infoLeyes) {
        ArrayList<Model> listLeyes = new ArrayList<>();

        for (int i = 0; i < infoLeyes.size(); i++) {
            String nombre = infoLeyes.get(i).getName();
            listLeyes.add(new Model(setButtonImageVentanaLeyes(nombre), nombre, ""));
        }

        return listLeyes;
    }

    public static int setButtonImageVentanaLeyes(String nombre) {
        switch (nombre) {
            case "¿Por qué?":
                return R.drawable.porque;
            case "Principio":
                return R.drawable.principio;
            case "En la antiguedad":
                return R.drawable.antiguedad;
            case "En España":
                return R.drawable.espana;
            case "Reducción de las letras y Arte para  enseñar a hablar a los  Mudos":
                return R.drawable.reduccionletras;
            case "Abad de L'Epée":
                return R.drawable.abad;
            case "Congreso de Milán":
                return R.drawable.milan;

            case "William Stoke":
                return R.drawable.william;

            case "Instituto Nuestra Señora de la Sabiduria":
                return R.drawable.instituto;

            case "Federación Nacional de Sordos de Colombia":
                return R.drawable.colombia;

            case "Ley General de Educación\nArticulo 46":
                return R.drawable.educacion;

            case "Ley 1618 de 2013":
                return R.drawable.ley1618;

            case "Ley 324 de 1996\nPor el cual se crean algunas normas a favor de la población sorda.":
                return R.drawable.ley324;

            case "Decreto 2106 de 2013\nPor el cual se modifica la estructura del Instituto Nacional para Sordos (Insor).":
                return R.drawable.decreto2106;

            case "Ley 982 de 2005\nNormas para la equiparación de oportunidades.":
                return R.drawable.oportunidades;

            default:
                return R.drawable.hands;

        }

    }


    public void recibirClikEvent() {
        Intent extras = getIntent();
        origin = extras.getStringExtra("clickEvent");
    }


}
