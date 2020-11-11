package com.example.iniciosesin.ControladorTab;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.solver.widgets.Rectangle;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.iniciosesin.R;
import com.example.iniciosesin.actividades.Verbos;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

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

    private int blue = Color.parseColor("#0F80AA");

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
        vista = inflater.inflate(R.layout.fragment_l_s_c, container, false);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        carpetas = new ArrayList<>();
        //LinearLayout linearLayout = (LinearLayout) getView().findViewById(R.id.LinealLayoutLSC);
        TableLayout tableLayout = (TableLayout) vista.findViewById(R.id.TableLayoutLSC);
        StorageReference ref = mStorageRef.child("videos");
        myAuth.getCurrentUser();
        myRef = database.getReference().child(myAuth.getCurrentUser().getUid().toString());
        myRef.child("location").setValue("Aprender");

        ref.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                int numeroTableRows;
                if (listResult.getPrefixes().size() % 3 == 0) {
                    numeroTableRows = listResult.getPrefixes().size() / 3;
                } else {
                    numeroTableRows = listResult.getPrefixes().size() / 3 + 1;
                }

                for (int i = 1; numeroTableRows >= i; i++) {
                    TableRow tableRow = new TableRow(getActivity().getApplicationContext());
                    tableRow.setId(i);
                    tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    tableRow.setGravity(Gravity.CENTER);
                    tableLayout.addView(tableRow);

                }
                showFolders(listResult, tableLayout, vista);


            }
        }).addOnFailureListener(e -> {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity().getApplicationContext());
            builder1.setMessage("Ha ocurrido un error, revisa tu conexión a internet");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }
            );
            AlertDialog alert1 = builder1.create();
            alert1.show();


        });
        return vista;
    }

    public void showFolders(ListResult listResult, TableLayout tableLayout, View vista) {

        int i = 1;
        for (StorageReference carpeta : listResult.getPrefixes()) {
            Space espacio = new Space(getActivity().getApplicationContext());
            espacio.setId(i+1000);
            espacio.setMinimumHeight(5);
            espacio.setMinimumWidth(10);
            carpetas.add(carpeta.getName());
            Button botonCarpeta = new Button(getActivity().getApplicationContext());
            botonCarpeta.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            botonCarpeta.setId(i + 100);
            //botonCarpeta.setBackgroundColor(blue);
            botonCarpeta.setText(carpeta.getName());
            System.out.println("**********" + botonCarpeta.getText());
            botonCarpeta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    myAuth.getCurrentUser();
                    myRef = database.getReference().child(myAuth.getCurrentUser().getUid().toString());
                    myRef.child("location").setValue("Aprender " + botonCarpeta.getText());


                    startActivity(new Intent(getActivity().getApplicationContext(), Verbos.class));

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

    public void irVerbos(View view) {
        startActivity(new Intent(getActivity().getApplicationContext(), Verbos.class));
    }
}