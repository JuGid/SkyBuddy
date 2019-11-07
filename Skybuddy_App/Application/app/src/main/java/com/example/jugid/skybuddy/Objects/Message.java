package com.example.jugid.skybuddy.Objects;

import com.google.gson.annotations.SerializedName;

public class Message {
    @SerializedName("contenu")
    private String contenu;
    @SerializedName("date")
    private String date;
    @SerializedName("idvols")
    private String idvols;
    @SerializedName("iduser")
    private String iduser;
    @SerializedName("envoyeur")
    private String envoyeur;

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setIdvols(String idvols) {
        this.idvols = idvols;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public void setEnvoyeur(String envoyeur) {
        this.envoyeur = envoyeur;
    }

    public String getContenu() {
        return contenu;
    }

    public String getDate() {
        return date;
    }

    public String getIdvols() {
        return idvols;
    }

    public String getIduser() {
        return iduser;
    }

    public String getEnvoyeur() {
        return envoyeur;
    }
}
