package com.example.cxx.higoer;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MyApplication extends Application {
    public static final String TAG="VolleyPatterns";
    private RequestQueue myRequestQueue;
    private static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
    }
    public static synchronized MyApplication getsInstance(){
        return sInstance;
    }

    public RequestQueue getMyRequestQueue() {
        if(myRequestQueue == null){
            myRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return myRequestQueue;
    }
    public void addToRequestQueue(Request request){
        request.setTag(TAG);
        getMyRequestQueue().add(request);
    }
    public void cancelPendingRequest(Object tag){
        if(myRequestQueue!= null){
            myRequestQueue.cancelAll(tag);
        }
    }
}
