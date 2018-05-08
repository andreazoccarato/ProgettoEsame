package com.example.andrea.progettoesame.Docente;

/**
 * Created by andrea on 16/04/2018.
 */

public class Studente {

    private String codF;
    private String nome;
    private String cognome;
    private String dataNascita;

    public Studente(String codF, String nome, String cognome, String dataNascita) {
        this.codF = codF;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
    }

    public String getCodF() {
        return codF;
    }

    public void setCodF(String codF) {
        this.codF = codF;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    @Override
    public String toString() {
        return "Studente{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataNascita='" + dataNascita + '\'' +
                '}';
    }
}
