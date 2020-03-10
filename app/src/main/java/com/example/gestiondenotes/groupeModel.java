package com.example.gestiondenotes;

public class groupeModel {
    private String Nom_du_groupe ;
    private String Nom_du_Module;
    private String Annee;

    public groupeModel(String nom_du_groupe, String nom_du_module, String Annee) {
        this.Nom_du_groupe = nom_du_groupe;
        this.Nom_du_Module = nom_du_module;
        this.Annee = Annee;
    }




    public String getAnnee() {
        return Annee;
    }

    public String getNom_du_groupe() {
        return Nom_du_groupe;
    }

    public String getNom_du_Module() {
        return Nom_du_Module;
    }

    }

