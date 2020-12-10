package com.example.iniciosesin.interfaces_cultura.datos_culturasorda;

import java.util.ArrayList;

public class DatosHistoria {
    public static ArrayList<Leyes> getHistory(){
        ArrayList<Leyes> history = new ArrayList<>();

        Leyes hist1 = new Leyes("Prueba", "no se","resultado");

        history.add(hist1);

        return history;
    }
}
