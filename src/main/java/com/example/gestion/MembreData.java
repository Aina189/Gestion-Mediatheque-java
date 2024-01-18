package com.example.gestion;

import java.util.Date;

public class MembreData {
    public int getId_membre() {
        return id_membre;
    }

    public void setId_membre(int id_membre) {
        this.id_membre = id_membre;
    }

    public String getNom_membre() {
        return nom_membre;
    }

    public void setNom_membre(String nom_membre) {
        this.nom_membre = nom_membre;
    }

    public String getPrenom_membre() {
        return prenom_membre;
    }

    public void setPrenom_membre(String prenom_membre) {
        this.prenom_membre = prenom_membre;
    }

    public String getAdress_membre() {
        return adress_membre;
    }

    public void setAdress_membre(String adress_membre) {
        this.adress_membre = adress_membre;
    }

    public int getCin_membre() {
        return cin_membre;
    }

    public void setCin_membre(int cin_membre) {
        this.cin_membre = cin_membre;
    }

    public String getMail_membre() {
        return mail_membre;
    }

    public void setMail_membre(String mail_membre) {
        this.mail_membre = mail_membre;
    }

    public String getOccupation_membre() {
        return occupation_membre;
    }

    public void setOccupation_membre(String occupation_membre) {
        this.occupation_membre = occupation_membre;
    }

    public Date getDate_naissance_membre() {
        return date_naissance_membre;
    }

    public void setDate_naissance_membre(Date date_naissance_membre) {
        this.date_naissance_membre = date_naissance_membre;
    }

    public String getSex_membre() {
        return sex_membre;
    }

    public void setSex_membre(String sex_membre) {
        this.sex_membre = sex_membre;
    }

    public Date getDate_debut_abn() {
        return date_debut_abn;
    }

    public void setDate_debut_abn(Date date_debut_abn) {
        this.date_debut_abn = date_debut_abn;
    }

    public Date getDate_fin_abn() {
        return date_fin_abn;
    }

    public void setDate_fin_abn(Date date_fin_abn) {
        this.date_fin_abn = date_fin_abn;
    }

    public String getType_abn() {
        return type_abn;
    }

    public void setType_abn(String type_abn) {
        this.type_abn = type_abn;
    }

    private int id_membre;
    private String nom_membre;
    private String prenom_membre;
    private String adress_membre;
    private int cin_membre;
    private String mail_membre;

    private String occupation_membre;
    private Date date_naissance_membre;

    private String sex_membre;

    private Date date_debut_abn;
    private Date date_fin_abn;

    private String type_abn;

    public MembreData(int id_membre, String nom_membre, String prenom_membre, String adress_membre, int cin_membre, String mail_membre, String occupation_membre, Date date_naissance_membre, String sex_membre) {
        this.id_membre = id_membre;
        this.nom_membre = nom_membre;
        this.prenom_membre = prenom_membre;
        this.adress_membre = adress_membre;
        this.cin_membre = cin_membre;
        this.mail_membre = mail_membre;
        this.occupation_membre = occupation_membre;
        this.date_naissance_membre = date_naissance_membre;
        this.sex_membre = sex_membre;
    }

    public MembreData(String type_abn, Date date_debut_abn, Date date_fin_abn) {
        this.type_abn = type_abn;
        this.date_debut_abn = date_debut_abn;
        this.date_fin_abn = date_fin_abn;
    }

    public MembreData(int id_membre, String nom_membre, String prenom_membre, String adress_membre, int cin_membre, String mail_membre, String occupation_membre, Date date_naissance_membre, String sex_membre, String type_abn, Date date_debut_abn, Date date_fin_abn) {
        // Appeler le constructeur existant pour initialiser les propriétés de base
        this(id_membre, nom_membre, prenom_membre, adress_membre, cin_membre, mail_membre, occupation_membre, date_naissance_membre, sex_membre);

        // Initialiser les propriétés spécifiques à la surcharge
        this.type_abn = type_abn;
        this.date_debut_abn = date_debut_abn;
        this.date_fin_abn = date_fin_abn;
    }

    // Les getters et setters restent inchangés
}
