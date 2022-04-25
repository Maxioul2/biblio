package com.example.demo;

public class Livre {
    private String isbn;
    private String titre;
    private String auteur;
    private String editeur;
    private String datePublication;
    private String langue;
    private String genre;

    public Livre(String isbn, String titre, String auteur, String editeur, String datePublication, String langue, String genre) {
        this.isbn = isbn;
        this.titre = titre;
        this.auteur = auteur;
        this.editeur = editeur;
        this.datePublication = datePublication;
        this.langue = langue;
        this.genre = genre;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public String getEditeur() {
        return editeur;
    }

    public String getDatePublication() {
        return datePublication;
    }

    public String getLangue() {
        return langue;
    }

    public String getGenre() {
        return genre;
    }

}
