package com.example.jugid.skybuddy.Objects;

import com.google.gson.annotations.SerializedName;

public class Localisation {

    @SerializedName("idlocalisation")
    private String idlocalisation;
    @SerializedName("iduser")
    private String iduser;
    @SerializedName("idvols")
    private String idvols;
    @SerializedName("posx")
    private String posx;
    @SerializedName("posy")
    private String posy;
    @SerializedName("nomuser")
    private String nomuser;

    public String getIdlocalisation() {
        return idlocalisation;
    }

    public String getIduser() {
        return iduser;
    }

    public String getIdvols() {
        return idvols;
    }

    public String getPosx() {
        return posx;
    }

    public String getPosy() {
        return posy;
    }

    public String getNomuser() {
        return nomuser;
    }
}
