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
                ", data='" + data + '\'' +
                '}';
    }

}
