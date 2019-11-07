package com.example.jugid.skybuddy.Objects;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private String id;
    @SerializedName("nomUser")
    private String nomUser;
    @SerializedName("prenom")
    private String prenom;
    @SerializedName("age")
    private String age;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("userKey")
    private String userKey;
    @SerializedName("email")
    private String email;
    @SerializedName("description")
    private String description;
    @SerializedName("rang")
    private String rang;
    @SerializedName("noteMoyenne")
    private String noteMoyenne;
    @SerializedName("nbVol")
    private String nbVol;

    public String getId() {
        return id;
    }

    public String getNomUser() {
        return nomUser;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getAge() {
        return age;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserKey() {
        return userKey;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    public String getRang() {
        return rang;
    }

    public String getNoteMoyenne() {
        return noteMoyenne;
    }

    public String getNbVol() {
        return nbVol;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRang(String rang) {
        this.rang = rang;
    }

    public void setNoteMoyenne(String noteMoyenne) {
        this.noteMoyenne = noteMoyenne;
    }

    public void setNbVol(String nbVol) {
        this.nbVol = nbVol;
    }
}