package com.example.iniciosesin.Logic.ControladorTab;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.iniciosesin.Logic.Aprende.Adapter;
import com.example.iniciosesin.Data.Model;
import com.example.iniciosesin.R;
import com.example.iniciosesin.Logic.Aprende.BotonesVideos;
import com.example.iniciosesin.Logic.Practica.Video_palabras;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;



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


    //

    private ViewPager viewPager;
    private Adapter adapter;
    private List<Model> models;
    private Integer[] colors = null;
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    private int blue = Color.parseColor("#0F80AA");

    private Drawable imagen;



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

                        Button aprende = vista.findViewById(R.id.aprende_ventanaLeyes2);
                        Button practica = vista.findViewById(R.id.practica_ventanaLeyes2);
                        PushDownAnim.setPushDownAnimTo(aprende, practica).setScale(PushDownAnim.MODE_SCALE, 0.89f).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });

                        aprende.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String titulo = models.get(position).getTitle();
                                myRef.child("location").setValue(titulo);
                                Intent i = new Intent(getActivity().getApplicationContext(), BotonesVideos.class);
                                //i.putExtra("nombreCarpeta",titulo);
                                startActivity(i);


                            }
                        });


                        practica.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String titulo = models.get(position).getTitle();
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

    public void setButtonIcon(Button boton, StorageReference carpeta) {
        switch (carpeta.getName().toLowerCase()) {
            case "comida":
                boton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.comida, 0, 0);
                break;
            case "números":
                boton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.numeros, 0, 0);
                break;
            case "verbos":
                boton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.verbos, 0, 0);
                break;
            case "aprender":
                boton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.aprende, 0, 0);
                break;
            case "arte y festividades":
                boton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.arte, 0, 0);
                break;
            case "aseo":
                boton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.aseo, 0, 0);
                break;
            case "características":
                boton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.caracteristicas, 0, 0);
                break;
            case "ciudad":
                boton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ciudad, 0, 0);
                break;
            case "deporte":
                boton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.deporte, 0, 0);
                break;
            case "hogar":
                boton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.hogar, 0, 0);
                break;
            case "relaciones personales":
                boton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.familia, 0, 0);
                break;
            case "salud":
                boton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.salud, 0, 0);
                break;
            case "tecnología":
                boton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tecnologia, 0, 0);
                break;
            case "tiempo":
                boton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tiempo, 0, 0);
                break;
            case "transporte":
                boton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.transporte, 0, 0);
                break;
            case "ubicación":
                boton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ubicacion, 0, 0);
                break;
            case "vestuario":
                boton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.vestimenta, 0, 0);
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
                return R.drawable.verbos;
            case "aprender":
                return R.drawable.aprende;
            case "arte y festividades":
                return R.drawable.arte;
            case "aseo":
                return R.drawable.aseo;
            case "características":
                return R.drawable.caracteristicas;

            case "ciudad":
                return R.drawable.ciudad;

            case "deporte":
                return R.drawable.deporte;

            case "hogar":
                return R.drawable.hogar;

            case "relaciones personales":
                return R.drawable.familia;

            case "salud":
                return R.drawable.salud;

            case "tecnología":
                return R.drawable.tecnologia;

            case "tiempo":
                return R.drawable.tiempo;

            case "transporte":
                return R.drawable.transporte;

            case "ubicación":
                return R.drawable.ubicacion;

            case "vestuario":
                return R.drawable.vestimenta;

            default:
                return R.drawable.hands;

        }

    }

    public void aprende() {


    }

    public void practica() {

    }
    /**/

}