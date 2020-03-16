package com.example.gestiondenotes;

public class Etudiant {
 String Nom  ;
 String Prenom ;
 String NI ;
 String note1 ,note2 ;
 String ID ;
 public String getNom() {
  return Nom;
 }

 public String getNI() {
  return NI;
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
