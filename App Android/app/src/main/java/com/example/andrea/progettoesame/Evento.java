package com.example.andrea.progettoesame;

/**
 * Created by andrea on 14/03/2018.
 */

public class Evento {

    public String materia;
    public String descrizione;
    public String data;
    public int itemType;

    public Evento(String materia, String descrizione, String data, int itemType) {
        this.materia = materia;
        this.descrizione = descrizione;
        this.data = data;
        this.itemType = itemType;
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

    public int getItemType() {
        return itemType;
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
