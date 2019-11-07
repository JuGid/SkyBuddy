package com.example.jugid.skybuddy.Objects;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Vol {
    @SerializedName("idvols")
    private String id;
    @SerializedName("nomVol")
    private String nomVol;
    @SerializedName("date_depart")
    private String date_depart;
    @SerializedName("date_arrivee")
    private String date_arrivee;
    @SerializedName("fini")
    private String fini;
    @SerializedName("idCreateur")
    private String idCreateur;
    @SerializedName("ville_depart")
    private String ville_depart;
    @SerializedName("ville_arrivee")
    private String ville_arrivee;
    @SerializedName("liste_user")
    private List<User> liste_user;
    @SerializedName("dernierMessage")
    private String dernierMessage;
    @SerializedName("nomPrenomCreateur")
    private String nomPrenomCreateur;
    @SerializedName("lastMessage")
    private String lastMessage;
    @SerializedName("nbUser")
    private String nbUser;

    public Vol(){}

    public String getLastMessage() {
        return lastMessage;
    }

    public String getIdvols() {
        return id;
    }

    public String getNomVol() {
        return nomVol;
    }

    public String getDate_depart() {
        return date_depart;
    }

    public String getDate_arrivee() {
        return date_arrivee;
    }

    public String getFini() {
        return fini;
    }

    public String getIdCreateur() {
        return idCreateur;
    }

    public String getVille_depart() {
        return ville_depart;
    }

    public String getVille_arrivee() {
        return ville_arrivee;
    }

    public List<User> getListe_user() {
        return liste_user;
    }

    public String getDernierMessage() {
        return dernierMessage;
    }

    public String getNomPrenomCreateur() {
        return nomPrenomCreateur;
    }

    public String getNbUser(){ return nbUser; }

    public void setIdvols(String idvols) {
        this.id = idvols;
    }

    public void setNomVol(String nomVol) {
        this.nomVol = nomVol;
    }

    public void setDate_depart(String date_depart) {
        this.date_depart = date_depart;
    }

    public void setDate_arrivee(String date_arrivee) {
        this.date_arrivee = date_arrivee;
    }

    public void setFini(String fini) {
        this.fini = fini;
    }

    public void setIdCreateur(String idCreateur) {
        this.idCreateur = idCreateur;
    }

    public void setVille_depart(String ville_depart) {
        this.ville_depart = ville_depart;
    }

    public void setVille_arrivee(String ville_arrivee) {
        this.ville_arrivee = ville_arrivee;
    }

    public void setListe_user(List<User> liste_user) {
        this.liste_user = liste_user;
    }

    public void setDernierMessage(String dernierMessage) {
        this.dernierMessage = dernierMessage;
    }

    public void setNomPrenomCreateur(String nomPrenomCreateur) {
        this.nomPrenomCreateur = nomPrenomCreateur;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setNbUser(String nbUser){ this.nbUser = nbUser;}

    public boolean isFollowing(User u){
        for(int i=0; i<liste_user.size(); i++) {
            if (liste_user.get(i).getId().equals(u.getId()) ){
                return true;
            }
        }
        return false;
    }
}
