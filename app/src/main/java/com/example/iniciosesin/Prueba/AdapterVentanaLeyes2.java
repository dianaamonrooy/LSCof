package com.example.iniciosesin.Prueba;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.example.iniciosesin.Aprende.Model;
import com.example.iniciosesin.R;
import com.example.iniciosesin.interfaces_cultura.PopUpListas;

import java.util.ArrayList;
import java.util.List;

public class AdapterVentanaLeyes2 extends PagerAdapter {

    private List<Model> models;
    private List<String> nombres;
    private LayoutInflater layoutInflater;
    private Context context;
    private LinearLayout linearLayout;
    private ArrayList<String> titulos;

    public AdapterVentanaLeyes2(List<Model> models, Context context) {
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

        titulos = new ArrayList<>();

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
                //Intent intent = new Intent(context, PopUpListas.class);
                /*intent.putExtra("position", position);
                intent.putParcelableArrayListExtra("arraylist", (ArrayList<? extends Parcelable>) models);*/
                //context.startActivity(intent);
                // finish();
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