package com.example.iniciosesin.Logic.ControladorTab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iniciosesin.R;
import com.example.iniciosesin.Logic.CulturaSorda.VentanaLeyes2;
import com.example.iniciosesin.Data.DatosHistoria;
import com.example.iniciosesin.Data.DatosLeyes;
import com.example.iniciosesin.Data.Leyes;

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

        cardLeyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), VentanaLeyes2.class);
                i.putExtra("clickEvent", "Leyes");
                startActivity(i);
            }
        });

        cardLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), VentanaLeyes2.class);
                i.putExtra("clickEvent", "LS");
                startActivity(i);
            }
        });

        return vista;
    }

    public static ArrayList<Leyes> getLeyes(){
        ArrayList<Leyes> arrayLeyes;
        arrayLeyes = DatosLeyes.getLaws();
        return arrayLeyes;
    }

    public static ArrayList<Leyes> getHistoria(){
        ArrayList<Leyes> arrayHistoria;
        arrayHistoria = DatosHistoria.getHistory();
        return arrayHistoria;
    }


}