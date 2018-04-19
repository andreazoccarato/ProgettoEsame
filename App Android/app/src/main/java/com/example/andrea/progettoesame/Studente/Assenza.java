package com.example.andrea.progettoesame.Studente;

/**
 * Created by andrea on 19/04/2018.
 */

public class Assenza {

    private String data;
    private String descrizione;
    private boolean giustificato;
    private String tipologia;
    public int itemType;

    public Assenza(int itemType, String data, String descrizione, boolean giustificato, String tipologia) {
        this.data = data;
        this.descrizione = descrizione;
        this.giustificato = giustificato;
        this.tipologia = tipologia;
        this.itemType = itemType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public boolean isGiustificato() {
        return giustificato;
    }

    public void setGiustificato(boolean giustificato) {
        this.giustificato = giustificato;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public String toString() {
        return "Assenza{" +
                "data='" + data + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", giustificato=" + giustificato +
                ", tipologia='" + tipologia + '\'' +
                '}';
    }
}
