package com.example.gestiondenotes;

public class Groupes {
    private String nom , niveau ;
    String id ;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public Groupes() {
    }
    public Groupes(String nom, String niveau) {
        this.nom = nom;
        this.niveau = niveau;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
}
