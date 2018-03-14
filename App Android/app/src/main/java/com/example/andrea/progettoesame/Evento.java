package com.example.andrea.progettoesame;

/**
 * Created by andrea on 14/03/2018.
 */

public class Evento {

    public String materia;
    public String descrizione;
    public String data;

    public Evento(String materia, String descrizione, String data) {
        this.materia = materia;
        this.descrizione = descrizione;
        this.data = data;
    }

    public String getMateria() {
        return materia;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "materia='" + materia + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
