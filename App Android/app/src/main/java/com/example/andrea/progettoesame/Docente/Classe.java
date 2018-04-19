package com.example.andrea.progettoesame.Docente;

/**
 * Created by andrea on 12/04/2018.
 */

public class Classe {

    public String codClasse;
    public String clsez;
    public String indirizzo;
    public String scuola;

    public Classe(String codClasse, String clsez, String indirizzo, String scuola) {
        this.codClasse = codClasse;
        this.clsez = clsez;
        this.indirizzo = indirizzo;
        this.scuola = scuola;
    }

    public String getCodClasse() {
        return codClasse;
    }

    public void setCodClasse(String codClasse) {
        this.codClasse = codClasse;
    }

    public String getClsez() {
        return clsez;
    }

    public void setClsez(String clsez) {
        this.clsez = clsez;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getScuola() {
        return scuola;
    }

    public void setScuola(String scuola) {
        this.scuola = scuola;
    }

    @Override
    public String toString() {
        return "Classe{" +
                "clsez='" + clsez + '\'' +
                ", indirizzo='" + indirizzo + '\'' +
                ", scuola='" + scuola + '\'' +
                '}';
    }
}
