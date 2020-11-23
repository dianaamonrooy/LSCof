package com.example.iniciosesin.ControladorTab;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.iniciosesin.Aprende.Adapter;
import com.example.iniciosesin.Aprende.Model;
import com.example.iniciosesin.R;
import com.example.iniciosesin.Aprende.BotonesVideos;
import com.example.iniciosesin.Practica.Video_palabras;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.takusemba.spotlight.CustomTarget;
import com.takusemba.spotlight.OnSpotlightEndedListener;
import com.takusemba.spotlight.OnSpotlightStartedListener;
import com.takusemba.spotlight.Spotlight;

import java.util.ArrayList;
import java.util.List;

import xyz.hanks.library.bang.SmallBangView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LSC#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LSC extends Fragment {
    private StorageReference mStorageRef;
    private ArrayList<String> carpetas;
    private FirebaseAuth myAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private View vista;
    private View vista2;

    //

    private ViewPager viewPager;
    private Adapter adapter;
    private List<Model> models;
    private Integer[] colors = null;
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    private int blue = Color.parseColor("#0F80AA");

    private Drawable imagen;
    private SmallBangView smallBangView;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public LSC() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LSC.
     */
    // TODO: Rename and change types and number of parameters
    public static LSC newInstance(String param1, String param2) {
        LSC fragment = new LSC();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        imagen = ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.deaficon);

        vista = inflater.inflate(R.layout.fragment_l_s_c, container, false);
        vista2 = inflater.inflate(R.layout.layout_target, container, false);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        carpetas = new ArrayList<>();
        //LinearLayout linearLayout = (LinearLayout) getView().findViewById(R.id.LinealLayoutLSC);
        //TableLayout tableLayout = (TableLayout) vista.findViewById(R.id.TableLayoutLSC);
        StorageReference ref = mStorageRef.child("videos");
        myRef = database.getReference().child(myAuth.getCurrentUser().getUid().toString());
        myRef.child("location").setValue("LSC");

        ref.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                models = new ArrayList<>();
                for (StorageReference carpeta : listResult.getPrefixes()) {
                    models.add(new Model(setButtonImage(carpeta), carpeta.getName(), ""));
                }
                /*models.add(new Model(R.drawable.hands, "Brochure", "Brochure is an informative paper document (often also used for advertising) that can be folded into a template"));
                models.add(new Model(R.drawable.comida, "Sticker", "Sticker is a type of label: a piece of printed paper, plastic, vinyl, or other material with pressure sensitive adhesive on one side"));
                models.add(new Model(R.drawable.verbosss, "Poster", "Poster is any piece of printed paper designed to be attached to a wall or vertical surface."));
                models.add(new Model(R.drawable.usuario, "Namecard", "Business cards are cards bearing business information about a company or individual."));
                */
                adapter = new Adapter(models, getActivity());

                viewPager = vista.findViewById(R.id.viewPager_LSC);
                viewPager.setAdapter(adapter);
                viewPager.setPadding(130, 0, 130, 0);

                Integer[] colors_temp = {
                        getResources().getColor(R.color.color1),
                        getResources().getColor(R.color.color2),
                        getResources().getColor(R.color.color3),
                        getResources().getColor(R.color.color4)
                };

                colors = colors_temp;

                viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        /*if (position < (adapter.getCount() - 1) && position < (colors.length - 1)) {
                            viewPager.setBackgroundColor(

                                    (Integer) argbEvaluator.evaluate(
                                            positionOffset,
                                            colors[position],
                                            colors[position + 1]
                                    )
                            );
                        } else {
                            viewPager.setBackgroundColor(colors[colors.length - 1]);
                        }*/
                        Button aprende = vista.findViewById(R.id.aprende_lsc);
                        aprende.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String titulo=models.get(position).getTitle();
                                myRef.child("location").setValue(titulo);
                                Intent i = new Intent(getActivity().getApplicationContext(), BotonesVideos.class);
                                //i.putExtra("nombreCarpeta",titulo);
                                startActivity(i);


                            }
                        });
                        Button practica = vista.findViewById(R.id.practica_lsc);
                        practica.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String titulo=models.get(position).getTitle();
                                myRef.child("location").setValue(titulo);
                                Intent i = new Intent(getActivity().getApplicationContext(), Video_palabras.class);
                                //i.putExtra("nombreCarpeta",titulo);
                                startActivity(i);
                                getActivity().finish();
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

            }
        });
        return vista;
    }

    public void showFolders(ListResult listResult, TableLayout tableLayout, View vista) {

        int i = 1;
        for (StorageReference carpeta : listResult.getPrefixes()) {
            Space espacio = new Space(getActivity().getApplicationContext());
            espacio.setId(i + 1000);
            espacio.setMinimumHeight(5);
            espacio.setMinimumWidth(10);
            carpetas.add(carpeta.getName());
            Button botonCarpeta = new Button(getActivity().getApplicationContext());
            botonCarpeta.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            botonCarpeta.setId(i + 100);
            botonCarpeta.setBackgroundColor(Color.TRANSPARENT);
            botonCarpeta.setTextColor(Color.parseColor("#0F80AA"));
            botonCarpeta.setText(carpeta.getName());
            setButtonIcon(botonCarpeta, carpeta);
            botonCarpeta.setTextColor(getResources().getColor(R.color.purple_200));


            System.out.println("**********" + botonCarpeta.getText());
            botonCarpeta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    myAuth.getCurrentUser();
                    myRef = database.getReference().child(myAuth.getCurrentUser().getUid().toString());
                    myRef.child("location").setValue(botonCarpeta.getText());
                    ConstraintLayout constraintLayout = vista2.findViewById(R.id.layout_aprede_practica);
                    CustomTarget thirdTarget =
                            new CustomTarget.Builder(getActivity()).setPoint(botonCarpeta)
                                    .setRadius(130f)
                                    .setView(R.layout.layout_target)
                                    .build();

                    Spotlight.with(getActivity())
                            .setDuration(500L)
                            .setAnimation(new DecelerateInterpolator(2f))
                            .setTargets(thirdTarget)
                            .setOnSpotlightStartedListener(new OnSpotlightStartedListener() {
                                @Override
                                public void onStarted() {
                                    LinearLayout linearLayout = new LinearLayout(getActivity().getApplicationContext());
                                    Button aprende_boton = vista2.findViewById(R.id.aprende_boton);
                                    Button practica_boton = vista2.findViewById(R.id.practica_boton);

                                    ConstraintLayout.LayoutParams paramsAprende = (ConstraintLayout.LayoutParams) aprende_boton.getLayoutParams();
                                    paramsAprende.leftMargin = (int) botonCarpeta.getX() - 20;
                                    paramsAprende.topMargin = (int) botonCarpeta.getY() - 20;

                                    ConstraintLayout.LayoutParams paramsPractica = (ConstraintLayout.LayoutParams) practica_boton.getLayoutParams();
                                    paramsPractica.leftMargin = (int) botonCarpeta.getX() - 20;
                                    paramsPractica.topMargin = (int) botonCarpeta.getY() - 40;


                                    aprende_boton.setLayoutParams(paramsAprende);

                                    practica_boton.setLayoutParams(paramsPractica);
                                    Animation scaleUp, scaleDown;
                                    scaleUp = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_up);
                                    scaleDown = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_up);
                                    aprende_boton.setOnTouchListener(new View.OnTouchListener() {
                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                                aprende_boton.startAnimation(scaleUp);

                                            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                                                aprende_boton.startAnimation(scaleDown);

                                            }
                                            return true;
                                        }
                                    });
                                    practica_boton.setOnTouchListener(new View.OnTouchListener() {
                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                                                practica_boton.startAnimation(scaleUp);
                                            } else if (event.getAction() == MotionEvent.ACTION_UP) {

                                                practica_boton.startAnimation(scaleDown);
                                            }
                                            return true;
                                        }
                                    });

                                    //Toast.makeText(getActivity(), "spotlight is started", Toast.LENGTH_SHORT).show();

                                }
                            })
                            .setOnSpotlightEndedListener(new OnSpotlightEndedListener() {
                                @Override
                                public void onEnded() {

                                    //Toast.makeText(getActivity(), "spotlight is ended", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .start();

                }
            });
            int accordingTableRow;
            if (i % 3 != 0) {
                accordingTableRow = i / 3 + 1;
            } else {
                accordingTableRow = i / 3;
            }
            TableRow chosenTableRow = (TableRow) vista.findViewById(accordingTableRow);
            System.out.println(chosenTableRow.getId());
            chosenTableRow.addView(botonCarpeta);
            chosenTableRow.addView(espacio);
            /*if (chosenTableRow.getChildCount() == 3) {
                tableLayout.addView(chosenTableRow);
            }*/

            i++;

        }
    }

    public void setButtonIcon(Button boton, StorageReference carpeta) {
        switch (carpeta.getName().toLowerCase()) {
            case "comida":
                boton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.comida, 0, 0);
                break;
            case "números":
                boton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.numeros, 0, 0);
                break;
            case "verbos":
                boton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.verbosss, 0, 0);
                break;
            default:
                boton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.hands, 0, 0);
        }

    }

    public int setButtonImage(StorageReference carpeta) {
        switch (carpeta.getName().toLowerCase()) {
            case "comida":
                return R.drawable.comida;
            case "números":
                return R.drawable.numeros;
            case "verbos":
                return R.drawable.verbosss;
            default:
                return R.drawable.hands;

        }

    }

    public void aprende(){


    }
    public void practica(){

    }


}