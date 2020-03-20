package com.example.gestiondenotes;

public class Module_users {

  private String nom ;
  private String id ;
  private String  note_eliminatoire ;
  private String coef;
  Formule formule ;


    public String getNote_eliminatoire() {
        return note_eliminatoire;
    }

    public String getCoef() {
        return coef;
    }
    public String getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }

    public void setCoef(String coef) {
        this.coef = coef;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setNote_eliminatoire(String note_eliminatoire) {
        this.note_eliminatoire = note_eliminatoire;
    }

    public Module_users() {
    }

    public Formule getFormule() {
        return formule;
    }

    public Module_users(String nom, String note_eliminatoire, String coef, String test1, String test2, String participation, String absence ) {
        this.nom = nom;
        this.note_eliminatoire = note_eliminatoire;
        this.coef = coef;
        this.formule = new Formule(test1,test2,participation,absence); }
}
