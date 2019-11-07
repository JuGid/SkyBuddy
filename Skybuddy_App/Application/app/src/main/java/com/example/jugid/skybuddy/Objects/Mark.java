package com.example.jugid.skybuddy.Objects;

import com.google.gson.annotations.SerializedName;

public class Mark {
    @SerializedName("idmark")
    private String idmark;
    @SerializedName("contenu")
    private String contenu;
    @SerializedName("valeur")
    private String valeur;
    @SerializedName("dateMark")
    private String dateMark;
    @SerializedName("idvols")
    private String idvols;
    @SerializedName("user_noteur")
    private String user_noteur;
    @SerializedName("user_note")
    private String user_note;

    public String getIdmark() {
        return idmark;
    }

    public String getContenu() {
        return contenu;
    }

    public String getValeur() {
        return valeur;
    }

    public String getDateMark() {
        return dateMark;
    }

    public String getIdvols() {
        return idvols;
    }

    public String getUser_noteur() {
        return user_noteur;
    }

    public String getUser_note() {
        return user_note;
    }

    public void setIdmark(String idmark) {
        this.idmark = idmark;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public void setDateMark(String dateMark) {
        this.dateMark = dateMark;
    }

    public void setIdvols(String idvols) {
        this.idvols = idvols;
    }

    public void setUser_noteur(String user_noteur) {
        this.user_noteur = user_noteur;
    }

    public void setUser_note(String user_note) {
        this.user_note = user_note;
    }
}
