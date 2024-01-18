package com.example.gestion;


import java.util.Date;

public class MembreStatueData {
    public int getId_membre() {
        return id_membre;
    }

    public void setId_membre(int id_membre) {
        this.id_membre = id_membre;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    private int id_membre;
    private String nom;
    private String typeAbnmt;
    private String prenom;
    private Date dateDebut;
    private Date dateFin;

    public MembreStatueData(int id_membre, String nom, String prenom, String typeAbnmt, Date dateDebut, Date dateFin){

        this.id_membre = id_membre;
        this.nom = nom;
        this.prenom = prenom;
        this.typeAbnmt = typeAbnmt;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public String getTypeAbnmt() {
        return typeAbnmt;
    }

    public void setTypeAbnmt(String typeAbnmt) {
        this.typeAbnmt = typeAbnmt;
    }
}
