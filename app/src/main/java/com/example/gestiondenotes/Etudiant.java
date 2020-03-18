package com.example.gestiondenotes;

public class Etudiant {
 private String Nom  ;
private String Prenom ;
 private String NI ;
 private String email ;
 private String note1 ,note2 ;
 private String ID ;
 String PICTURE ;

 public String getPICTURE() {
  return PICTURE;
 }

 public void setPICTURE(String PICTURE) {
  this.PICTURE = PICTURE;
 }

 public String getNom() {
  return Nom;
 }

 public String getNI() {
  return NI;
 }
 public Etudiant(String Nom ,String prenom , String NI , String email, String PICTURE) {
  this.Nom = Nom ;
  this.Prenom = prenom ;
  this.NI = NI ;
  this.email = email ;
  this.PICTURE = PICTURE; ;
 }

 public void setEmail(String email) {
  this.email = email;
 }

 public String getEmail() {
  return email;
 }

 public String getPrenom() {
  return Prenom;
 }

 public String getID() {
  return ID;
 }

 public String getNote1() {
  return note1;
 }

 public String getNote2() {
  return note2;
 }

 public void setNI(String NI) {
  this.NI = NI;
 }

 public void setNote1(String note1) {
  this.note1 = note1;
 }

 public void setNote2(String note2) {
  this.note2 = note2;
 }
 public void setNom(String Nom) {
 this.Nom = Nom;
 }
 public void setID(String ID) {
  this.ID = ID;
 }
 public void setPrenom(String prenom) {
  Prenom = prenom;
 }
 public Etudiant(String nom, String prenom, String NI) {
  this.Nom = nom;
  Prenom = prenom;
  this.NI = NI;
 }
 public Etudiant() {
 }
}
