package com.example.gestiondenotes;

public class Formule_calcul_Note {
    String Test_1 ;
    String Test_2 ;
    String abscence ;
    String Participation ;

    public Formule_calcul_Note(String test_1, String test_2, String abscence, String participation) {
        Test_1 = test_1;
        Test_2 = test_2;
        this.abscence = abscence;
        Participation = participation;
    }

    public String getAbscence() {
        return abscence;
    }

    public String getParticipation() {
        return Participation;
    }

    public String getTest_1() {
        return Test_1;
    }

    public String getTest_2() {
        return Test_2;
    }

    public void setAbscence(String abscence) {
        this.abscence = abscence;
    }

    public void setParticipation(String participation) {
        Participation = participation;
    }

    public void setTest_1(String test_1) {
        Test_1 = test_1;
    }

    public void setTest_2(String test_2) {
        Test_2 = test_2;
    }

}
