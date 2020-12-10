package com.example.iniciosesin.ControladorTab;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.iniciosesin.Aprende.BotonesVideos;
import com.example.iniciosesin.R;
import com.example.iniciosesin.interfaces_cultura.ComunicacionFragments;
import com.example.iniciosesin.interfaces_cultura.VentanaLeyes;
import com.example.iniciosesin.interfaces_cultura.datos_culturasorda.DatosHistoria;
import com.example.iniciosesin.interfaces_cultura.datos_culturasorda.DatosLeyes;
import com.example.iniciosesin.interfaces_cultura.datos_culturasorda.Leyes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CulturaSorda#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CulturaSorda extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View vista;
    Activity actividad;
    CardView cardLeyes, cardLS, cardAprenderMas;
    ComunicacionFragments interfaceComunicacionFragments;

    public CulturaSorda() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CulturaSorda.
     */
    // TODO: Rename and change types and number of parameters
    public static CulturaSorda newInstance(String param1, String param2) {
        CulturaSorda fragment = new CulturaSorda();
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
        vista = inflater.inflate(R.layout.fragment_cultura_sorda, container, false);

        cardLeyes = vista.findViewById(R.id.leyes);
        cardLS = vista.findViewById(R.id.signlanguage);
        cardAprenderMas = vista.findViewById(R.id.aprendemas);

        cardLeyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), VentanaLeyes.class);
                i.putExtra("clickEvent", "Leyes");
                startActivity(i);
                /*interfaceComunicacionFragments.iniciarLeyes();*/
            }
        });

        cardLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), VentanaLeyes.class);
                i.putExtra("clickEvent", "LS");
                startActivity(i);
               /* interfaceComunicacionFragments.iniciarLS();*/
            }
        });

        cardAprenderMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Espere actualizaciones", Toast.LENGTH_SHORT).show();
                /*interfaceComunicacionFragments.iniciarAprenderMas();*/
            }
        });

        return vista;
    }

/*    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            actividad = (Activity) context;
            interfaceComunicacionFragments= (ComunicacionFragments) actividad;
        }
    }*/

    public static ArrayList<Leyes> getLeyes (){
        ArrayList<Leyes> arrayLeyes;
        arrayLeyes = DatosLeyes.getLaws();
        return arrayLeyes;
    }

    public static ArrayList<Leyes> getHistoria (){
        ArrayList<Leyes> arrayHistoria;
        arrayHistoria = DatosHistoria.getHistory();
        return arrayHistoria;
    }


}