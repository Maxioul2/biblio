package com.example.demo;

public class Pret {
    private int user_id;
    private Livre livre;
    private String date_debut;
    private String date_fin;
    private boolean renouvele;

    public Pret(int user_id, Livre livre, String date_debut, String date_fin, boolean renouvele) {
        this.user_id = user_id;
        this.livre = livre;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.renouvele = renouvele;
    }

    public int getUserId() {
        return user_id;
    }

    public Livre getLivre() {
        return livre;
    }

    public String getDateDebut() {
        return date_debut;
    }

    public String getDateFin() {
        return date_fin;
    }

    public boolean isRenouvele() {
        return renouvele;
    }
}
