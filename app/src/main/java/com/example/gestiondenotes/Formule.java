package com.example.gestiondenotes;

public class Formule {
    String id = "000" ;
    String Test1 ;
    String Test2 ;
    String Participation ;
    String abscence ;
    public String getParticipation() {
        return Participation;
    }
    public  Formule() {
    }
    public String getAbscence() {
        return abscence;
    }

    public String getId() {
        return id;
    }

    public String getTest1() {
        return Test1;
    }

    public String getTest2() {
        return Test2;
    }

    public void setParticipation(String participation) {
        Participation = participation;
    }

    public void setAbscence(String abscence) {
        this.abscence = abscence;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setTest1(String test1) {
        Test1 = test1;
    }
    public void setTest2(String test2) {
        Test2 = test2;
    }
    public Formule(String test1, String test2, String participation, String abscence) {
        this.Test1 = test1;
        this.Test2 = test2;
        this.Participation = participation;
        this.abscence = abscence;
    }
}
