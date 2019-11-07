package com.example.jugid.skybuddy.Modules;

import android.util.Log;

import com.example.jugid.skybuddy.Objects.Articles;
import com.example.jugid.skybuddy.Objects.Localisation;
import com.example.jugid.skybuddy.Objects.Mark;
import com.example.jugid.skybuddy.Objects.Message;
import com.example.jugid.skybuddy.Objects.User;
import com.example.jugid.skybuddy.Objects.Vol;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Jason {

    private static Jason mInstance;

    private List<Vol> wrapper_vol;
    private List<Mark> wrapper_mark;
    private List<Articles> wrapper_articles;
    private List<Message> wrapper_messages;
    private List<Localisation> wrapper_localisation;

    private Jason (){

    }

    public static synchronized Jason getInstance() {
        if (mInstance == null) {
            mInstance = new Jason();
        }
        return mInstance;
    }

    public static User jsonToUser(String response){
        User user = new Gson().fromJson(response,User.class);
        return user;
    }

    public List<Vol> jsonToListVol(String response){
        Type listType = new TypeToken<ArrayList<Vol>>(){}.getType();
        wrapper_vol = new Gson().fromJson(response, listType);
        return wrapper_vol;
    }

    public List<Mark> jsonToListMark(String response){
        Type listType = new TypeToken<ArrayList<Mark>>(){}.getType();
        wrapper_mark = new Gson().fromJson(response, listType);
        return wrapper_mark;
    }

    public List<Localisation> jsonToListLocalisation(String response){
        Type listType = new TypeToken<ArrayList<Localisation>>(){}.getType();
        wrapper_localisation = new Gson().fromJson(response, listType);
        return wrapper_localisation;
    }

    public Vol jsonToVol(String response){
        Vol vol = new Gson().fromJson(response,Vol.class);
        return vol;
    }

    public List<Articles> jsonToListArticles(String response){
        Type listType = new TypeToken<ArrayList<Articles>>(){}.getType();
        wrapper_articles = new Gson().fromJson(response, listType);
        return wrapper_articles;
    }

    public List<Message> jsonToListMessages(String response){
        Type listType = new TypeToken<ArrayList<Message>>(){}.getType();
        wrapper_messages = new Gson().fromJson(response, listType);
        return wrapper_messages;
    }

    //2018-06-11 10:05:00
    public static String getHours(String mysqlDate){
        String total = mysqlDate.substring(11,16);
        String substring = total.substring(2,5);
        int hour = Integer.parseInt(total.substring(0,2));

        if( hour == 23 ){ hour = 1; }
        else{ hour += 2; }

        return String.format("%02d%s", hour,substring);
    }

    public static String getDate(String mysqlDate){
        String year = mysqlDate.substring(0,4);
        String month = mysqlDate.substring(5,7);
        String day = mysqlDate.substring(8,10);


        String datesTotal = day +"/"+month+"/"+year;
        return datesTotal;
    }

    //Exemple
    /*
    public List<Partie> jsonToParties(String jsonString){
        Type listType = new TypeToken<ArrayList<Partie>>(){}.getType();
        wrapper = new Gson().fromJson(jsonString, listType);
        return wrapper;
    }*/
}
