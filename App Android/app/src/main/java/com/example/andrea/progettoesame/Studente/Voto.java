package com.example.andrea.progettoesame.Studente;

/**
 * Created by andrea on 02/03/2018.
 */

public class Voto {

    public String voto;
    public String materia;
    public String descrizione;
    public String data;

    public Voto(String voto,String materia,String descrizione,String data){
        this.voto=voto;
        this.materia=materia;
        this.descrizione=descrizione;
        this.data=data;
    }

    public String getVoto() {
        return voto;
    }

    public String getMateria() {
        return materia;
    }

    public String getData() { return data; }

    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public String toString() {
        return "Voto{" +
                "voto='" + voto + '\'' +
                ", materia='" + materia + '\'' +
                ", descrizione='" + descrizione + '\'' +
                '}';
    }

    public static Voto[] voti = {
            new Voto("9","Matematica","Integrali e serie numeriche","02/03/2018"),
            new Voto("10","Informatica","Database e forme normali","28/02/2018"),
            new Voto("6","Lingua e letteratura italiana","Tema Storico","12/02/2018"),
            new Voto("5 1/2","Scienze motorie e sportive","Preparazione pallamano","25/01/2018")
    };
}
