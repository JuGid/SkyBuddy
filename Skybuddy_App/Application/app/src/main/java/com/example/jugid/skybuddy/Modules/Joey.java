package com.example.jugid.skybuddy.Modules;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

//S'occupe de garder les requêtes et de s'assurer de leur envoi
//On dit merci Joey !
public class Joey {

    private static Joey mInstance;
    private RequestQueue mRequestQueue;
    private Context mCtx;

    private Joey(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized Joey getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Joey(context);
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
