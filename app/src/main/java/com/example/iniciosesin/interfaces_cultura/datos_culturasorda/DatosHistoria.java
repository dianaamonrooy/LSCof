package com.example.iniciosesin.interfaces_cultura.datos_culturasorda;

import java.util.ArrayList;

public class DatosHistoria {
    public static ArrayList<Leyes> getHistory(){
        ArrayList<Leyes> history = new ArrayList<>();

        Leyes hist = new Leyes("¿Por qué?", "La lengua de señas surge como respuesta a las necesidades comunicativas " +
                "de la comunidad sorda.\n Se ha desarrollado a partir de un conjunto de principios conceptuales y prácticos.",
                "https://revistas.pedagogica.edu.co/index.php/PYS/article/view/6242/5695");
        Leyes hist1 = new Leyes("Principio","Los amerindios de la región de las Grandes Llanuras de América Norte, " +
                "usaban una lengua de señas para hacerse entender entre etnias que hablaban lenguas muy diferentes con fonologías " +
                "extremadamente  diversas. El  sistema estuvo  en  uso  hasta  mucho después de la conquista europea.\n\n" +
                "Otro caso, también amerindio, se dio en la isla de Manhattan, donde vivía una tribu única en la que un gran número de sus " +
                "integrantes eran sordos, debido a la herencia de desarrollo de un gen dominante, y que se comunicaban con una lengua gestual.",
                "https://docplayer.es/17314143-Lenguaje-de-senas-reduccion-de-las-letras-y-arte-para-ensenar-a-hablar-a-los-mudos-1620-de-juan-pablo-bonet.html");
        Leyes hist2 = new Leyes("En la antiguedad","1198\nEl papa Inocencio III argumenta \"el que no puede hablar, en " +
                "señas se puede manifestar\", dando así, reconocimiento social a la forma de comunicación por medio de señas.",
                "https://revistas.pedagogica.edu.co/index.php/PYS/article/view/6242/5695");
        Leyes hist3 = new Leyes("En España","Pedro Ponce de León, monje benedictino del siglo XVI , se considera el primer " +
                "maestro de sordos; en su comunidad se utilizaban las señas en actividades diarias.",
                "https://revistas.pedagogica.edu.co/index.php/PYS/article/view/6242/5695");
        Leyes hist4 = new Leyes("Reducción de las letras y Arte para  enseñar a hablar a los  Mudos",
                "Juan de Pablo Bonet - 1620\n" +
                        "considerado  como  el  primer  tratado  moderno de Fonética Logopedia, en el que se proponía un método  de  " +
                        "enseñanza oral de los sordos mediante el uso de señas alfabéticas configuradas un manualmente, divulgando " +
                        "así en toda Europa, y después en todo el mundo, el alfabeto manual, útil para mejorar la comunicación de " +
                        "los sordos y mudos.","https://docplayer.es/17314143-Lenguaje-de-senas-reduccion-de-las-letras-y-arte-para-ensenar-a-hablar-a-los-mudos-1620-de-juan-pablo-bonet.html");
        Leyes hist5 = new Leyes("Abad de L'Epée","Difundio la lengua de señas durante la segunda mitad del siglo XVIII.\n" +
                "Creó la primera escuela pública para sordos (1760).\n" +
                "Reconocio que los sordos eran capaces de comunicarse entre ellos mediante el uso de un sistema de gestos linguistico, " +
                "sistema que describio y recopilo en lo que se considera un diccionario de señas parisina.","https://revistas.pedagogica.edu.co/index.php/PYS/article/view/6242/5695");
        Leyes hist6 = new Leyes("Congreso de Milán","1880\n" +
                "Un grupo de oyentes maestros de sordos decidieron excluir la lengua de signos de la enseñanza de los Sordos, y también impusieron que el objetivo principal " +
                "de la escuela de Sordos debía ser enseñar el habla.\n" +
                "Además de alcanzar mediocres resultados en el aprendizaje del habla, los niños de las escuelas oralistas tenían habilidades lectoras correspondientes a " +
                "edades muy inferiores y muchos mostraban graves problemas psíquicos.\n" +
                "Esto también afecto el desarrollo de la lengua de señas en América Latina.","https://donsigno.com/historia-de-la-comunidad-sorda-congreso-de-milan-1880/");
        Leyes hist7 = new Leyes("William Stoke","Llevó a cabo estudios gramaticales y fonológicos de la lengua de señas y concluyó que la estructura es similar a la de cualquier lengua.\n" +
                "Desde entonces las categorías gramaticales universales (fonética, semántica, morfosintaxis) son aplicadas a la lengua de señas.",
                "https://revistas.pedagogica.edu.co/index.php/PYS/article/view/6242/5695");
        Leyes hist8 = new Leyes("Instituto Nuestra Señora de la Sabiduria","Colombia\n" +
                "El instituto ofrecia programas educativos dirigidos a jóvenes sordos.\n" +
                "Se emplearon métodos centrados en la enseñanza del lenguaje hablado, escrito y lectura labio-facial.",
                "https://revistas.pedagogica.edu.co/index.php/PYS/article/view/6242/5695");
        Leyes hist9 = new Leyes("Federación Nacional de Sordos de Colombia","1984\n" +
                "Promueve el uso de la lengua de señas colombiana LSC, su estudio linguistico y sociolinguistico.",
                "https://revistas.pedagogica.edu.co/index.php/PYS/article/view/6242/5695");


        history.add(hist);
        history.add(hist1);
        history.add(hist2);
        history.add(hist3);
        history.add(hist4);
        history.add(hist5);
        history.add(hist6);
        history.add(hist7);
        history.add(hist8);
        history.add(hist9);

        return history;
    }
}
