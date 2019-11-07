package com.example.jugid.skybuddy.Modules;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.jugid.skybuddy.Interfaces.VolleyCallback;
import com.example.jugid.skybuddy.Objects.Vol;
import com.example.jugid.skybuddy.Skybuddy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//Personne en charge des requêtes
public class Chloe {

    private static Chloe mInstance;
    private Context mContext;

    private static String adr = "http://10.0.2.2:3000"; //"http://592a8402.ngrok.io";

    private Chloe (Context ctx){
        this.mContext = ctx;

    }

    public static synchronized Chloe getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Chloe(context);
        }
        return mInstance;
    }

    public static void login(final Context context, final String username, final String password, final VolleyCallback callback){

        StringRequest sr = new StringRequest(Request.Method.POST,adr+"/connect", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("THIS", "Erreur de connexion au serveur. Verifier votre connexion internet");
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("username",username);
                params.put("password", password);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        Joey.getInstance(context).addToRequestQueue(sr);
    }

    public static void subscribe(final Context context, final String nom, final String prenom, final String username, final String password,final String email, final String age,final VolleyCallback callback){

        StringRequest sr = new StringRequest(Request.Method.POST,adr+"/subscribe", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("THIS", "Erreur de la requête");
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("username",username);
                params.put("password", password);
                params.put("nom", nom);
                params.put("prenom", prenom);
                params.put("age", age);
                params.put("email", email);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        Joey.getInstance(context).addToRequestQueue(sr);
    }

    public static void disconnect(final Context context, final VolleyCallback callback){

        StringRequest sr = new StringRequest(Request.Method.POST,adr+"/disconnect", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("THIS", error.toString());
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("userKey",Thibaut.user.getUserKey());
                params.put("username",Thibaut.user.getUsername());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        Joey.getInstance(context).addToRequestQueue(sr);
    }

    public static void getFollowedByUser(final Context context, final VolleyCallback callback){
        StringRequest request = new StringRequest(Request.Method.GET, adr+"/users/"+Thibaut.user.getId()+"/follow", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Erreur : problème de connexion",Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });

        Joey.getInstance(context).addToRequestQueue(request);
    }

    public static void getAllVols(final Context context, final VolleyCallback callback){
        StringRequest request = new StringRequest(Request.Method.GET, adr+"/flights", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Erreur : problème de connexion",Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });

        Joey.getInstance(context).addToRequestQueue(request);
    }

    public static void getVolsByLike(final Context context, final VolleyCallback callback,String value){
        StringRequest request = new StringRequest(Request.Method.GET, adr+"/flights/like/"+value, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Erreur : problème de connexion",Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });

        Joey.getInstance(context).addToRequestQueue(request);
    }

    public static void getMarksOfUser(final Context context, final VolleyCallback callback){
        StringRequest request = new StringRequest(Request.Method.GET, adr+"/users/"+Thibaut.user.getId()+"/comments", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Erreur : problème de connexion",Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });

        Joey.getInstance(context).addToRequestQueue(request);
    }

    public static void getVolById(final Context context,final VolleyCallback callback,String id){
        StringRequest request = new StringRequest(Request.Method.GET, adr+"/flights/"+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Erreur : problème de connexion",Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });

        Joey.getInstance(context).addToRequestQueue(request);
    }

    public static void getUserById(final Context context,final VolleyCallback callback,String id){
        StringRequest request = new StringRequest(Request.Method.GET, adr+"/users/"+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Erreur : problème de connexion",Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });

        Joey.getInstance(context).addToRequestQueue(request);
    }

    public static void getAllArticles(final Context context,final VolleyCallback callback){
        StringRequest request = new StringRequest(Request.Method.GET, adr+"/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Erreur : problème de connexion",Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });

        Joey.getInstance(context).addToRequestQueue(request);
    }

    public static void getFollowedByUserWithMessage(final Context context, final VolleyCallback callback){
        StringRequest request = new StringRequest(Request.Method.GET, adr+"/messages/"+Thibaut.user.getId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Erreur : problème de connexion",Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });

        Joey.getInstance(context).addToRequestQueue(request);
    }

    public static void getAllMessagesByVolId(final Context context, final String idvol, final VolleyCallback callback){
        StringRequest request = new StringRequest(Request.Method.GET, adr+"/messages/ask/"+idvol, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Erreur : problème de connexion",Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });

        Joey.getInstance(context).addToRequestQueue(request);
    }

    public static void updateUser(final Context context, final String password, final String mail, final String description, final VolleyCallback callback){

        StringRequest sr = new StringRequest(Request.Method.POST,adr+"/users/"+Thibaut.user.getId()+"/update", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("THIS", "Erreur de la requête");
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("iduser",Thibaut.user.getId());
                params.put("password",password);
                params.put("mail", mail);
                params.put("description",description);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        Joey.getInstance(context).addToRequestQueue(sr);
    }

    public static void addComment(final Context context, final String value, final String contenu, final String id_user_note, final String id_vol, final VolleyCallback callback){
        StringRequest sr = new StringRequest(Request.Method.POST,adr+"/comments/create", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("THIS", "Erreur de la requête");
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("note",value);
                params.put("contenu", contenu);
                params.put("id_user_note", id_user_note);
                params.put("id_vol", id_vol);
                params.put("id_user_noteur",Thibaut.user.getId());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        Joey.getInstance(context).addToRequestQueue(sr);
    }

    public static void participer(final Context context, final String idvols, final VolleyCallback callback){
        StringRequest sr = new StringRequest(Request.Method.POST,adr+"/flights/participer", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("THIS", "Erreur de la requête");
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("idvols",idvols);
                params.put("iduser",Thibaut.user.getId());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        Joey.getInstance(context).addToRequestQueue(sr);
    }

    public static void debarquer(final Context context, final String idvols, final VolleyCallback callback){
        StringRequest sr = new StringRequest(Request.Method.POST,adr+"/flights/debarquer", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("THIS", "Erreur de la requête");
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("idvols",idvols);
                params.put("iduser",Thibaut.user.getId());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        Joey.getInstance(context).addToRequestQueue(sr);
    }

    public static void addVol(final Context context, final Vol vol, final VolleyCallback callback){
        StringRequest sr = new StringRequest(Request.Method.POST,adr+"/flights/create", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("THIS", "Erreur de la requête");
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("nomVol",vol.getNomVol());
                params.put("date_depart",vol.getDate_depart());
                params.put("date_arrivee",vol.getDate_arrivee());
                params.put("idCreateur",Thibaut.user.getId());
                params.put("ville_arrivee",vol.getVille_arrivee());
                params.put("ville_depart",vol.getVille_depart());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        Joey.getInstance(context).addToRequestQueue(sr);
    }

    public static void addMessage(final Context context, String idvol, final String contenu, final String date, final VolleyCallback callback){
        StringRequest sr = new StringRequest(Request.Method.POST,adr+"/messages/addmessages/"+idvol, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("THIS", "Erreur de la requête");
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("contenu",contenu);
                params.put("date",date);
                params.put("iduser",Thibaut.user.getId());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        Joey.getInstance(context).addToRequestQueue(sr);
    }

    public static void getAllLocalisations(final Context context, String idvol, final VolleyCallback callback){
        StringRequest request = new StringRequest(Request.Method.GET, adr+"/localisations/"+idvol, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Erreur : problème de connexion",Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });

        Joey.getInstance(context).addToRequestQueue(request);
    }

    public static void localiser(final Context context, final String idsvols, final String posx, final String posy, final VolleyCallback callback){
        StringRequest sr = new StringRequest(Request.Method.POST,adr+"/localisations/localise", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("THIS", "LOCALISER ERREUR REQUETE");
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("idvols",idsvols);
                params.put("iduser",Thibaut.user.getId());
                params.put("posx",posx);
                params.put("posy",posy);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        Joey.getInstance(context).addToRequestQueue(sr);
    }

    public static void getMessageUpdate(final Context context, final String date, final VolleyCallback callback){
        StringRequest sr = new StringRequest(Request.Method.POST,adr+"/messages/maj/"+Thibaut.user.getId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("THIS", "UPDATE MESSAGE REQUETE");
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("date",date);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        Joey.getInstance(context).addToRequestQueue(sr);
    }
}