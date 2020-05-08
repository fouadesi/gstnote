package com.example.gestiondenotes;

public class Etudiant {
 private String Nom  ;
 private String Prenom ;
 private String NI ;
 private String Abscence ;
 private String email ;
 private String note1 ,note2 ;
 private String ID ;
 String picture ;

 public Etudiant(String nom, String prenom, String NI, String abscence, String email, String note1, String note2,  String picture) {
  Nom = nom;
  Prenom = prenom;
  this.NI = NI;
  Abscence = abscence;
  this.email = email;
  this.note1 = note1;
  this.note2 = note2;
  this.ID = ID;
  this.picture = picture;
 }

 public String getAbscence() {
  return Abscence;
 }

 public void setAbscence(String abscence) {
  Abscence = abscence;
 }
 public String getNom() {
  return Nom;
 }

 public String getNI() {
  return NI;
 }

 public String getPicture() {
  return picture;
 }

 public void setPicture(String picture) {
  this.picture = picture;
 }

 public Etudiant(String Nom , String prenom , String NI , String email, String PICTURE) {
  this.Nom = Nom ;
  this.Prenom = prenom ;
  this.NI = NI ;
  this.email = email ;
  this.picture = PICTURE; ;
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
