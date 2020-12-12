package com.example.iniciosesin.Logic.ControladorTab;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.iniciosesin.R;
import com.example.iniciosesin.Logic.Actividades.MainActivity;
import com.example.iniciosesin.UI.PopUpShowReferences;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thekhaeng.pushdownanim.PushDownAnim;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserInfo extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private FirebaseAuth myAuth = FirebaseAuth.getInstance();

    private TextView nombreTextView;
    private TextView apellidosTextView;
    private TextView dateTextView;
    private TextView emailTextView;
    private TextView nivelTextView;
    private Button logOut;
    private ProgressBar progressBar;
    private int progresoNum;
    private Button press;
    private ImageView info;

    private View vista;

    public UserInfo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserInfo.
     */
    // TODO: Rename and change types and number of parameters
    public static UserInfo newInstance(String param1, String param2) {
        UserInfo fragment = new UserInfo();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        vista = inflater.inflate(R.layout.fragment_user_info, container, false);

        nombreTextView = vista.findViewById(R.id.text_view_nombre_user);
        apellidosTextView = vista.findViewById(R.id.text_view_apellidos_user);
        dateTextView = vista.findViewById(R.id.view_last_login_date);
        emailTextView = vista.findViewById(R.id.view_email);
        progressBar = vista.findViewById(R.id.progressBar);
        //press = vista.findViewById(R.id.pressButton);
        nivelTextView = vista.findViewById(R.id.nivel);
        info = vista.findViewById(R.id.infoReferencias);


        //progressBar.setMax(100);
        progresoNum = 0;

        /*press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progresoNum += 10;
                progressBar.setProgress(progresoNum, true);
                if (progresoNum == 100) {
                    progresoNum = 0;
                }

            }
        });*/


        //myRef = database.getReference().child(myAuth.getCurrentUser().getUid().toString());
        //myRef.child("progreso").setValue();


        logOut = vista.findViewById(R.id.logOutUser);
        PushDownAnim.setPushDownAnimTo(logOut,info).setScale(PushDownAnim.MODE_SCALE, 0.89f).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), PopUpShowReferences.class));

            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAuth.signOut();
                startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                getActivity().finish();
            }
        });

        myRef = database.getReference().child(myAuth.getCurrentUser().getUid().toString());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()) {
                    String date = dataSnapshot.child("date").getValue().toString();
                    String email = dataSnapshot.child("email").getValue().toString();
                    String nombre = dataSnapshot.child("nombre").getValue().toString();
                    String apellidos = dataSnapshot.child("apellidos").getValue().toString();
                    int progreso = Integer.parseInt(dataSnapshot.child("progreso").getValue().toString());
                    int nivel = Integer.parseInt(dataSnapshot.child("nivel").getValue().toString());


                    progressBar.setMax(nivel * 10);
                    progressBar.setProgress(progreso, true);

                    nivelTextView.setText(Integer.toString(nivel));
                    dateTextView.setText(date);
                    emailTextView.setText(email);
                    nombreTextView.setText(nombre);
                    apellidosTextView.setText(apellidos);
                }

                //Log.d("Éxito", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Error", "Failed to read value.", error.toException());
            }
        });


        return vista;
    }

    public void logOut(View view) {
        myAuth.signOut();
        startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
        getActivity().finish();
    }
}