package com.example.cxx.higoer;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CXX on 2016/4/15.
 */
public class GetJSON {
    public GetJSON(){
        System.out.println("tihs is getjson class");
    }
    private List<ItemData> itemDatas;
    public  List<ItemData> getPerson(){
        String url = "http://10.0.2.2/personjson.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    public void onResponse(String response) {
                        System.out.println(response);
                        Gson gson = new Gson();
                        JsonParser jsonParser = new JsonParser();
                        itemDatas = new ArrayList<ItemData>();
                        JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();
                        JsonArray jsonArray = jsonObject.getAsJsonArray("user");
                        Type type = new TypeToken<ItemData>(){}.getType();
                        for(int i=0;i<jsonArray.size();i++){
                            JsonElement element = jsonArray.get(i);
                            ItemData data = gson.fromJson(element,type);
                            itemDatas.add(data);
                            System.out.println(itemDatas.get(i).getName());
                        }


                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {

            }
        }
        );
       MyApplication.getsInstance().addToRequestQueue(stringRequest);
        return itemDatas;
    }
    public void getIamge(MyAdapter.ViewHolder vh,String url){
        final LruCache<String,Bitmap> lruCache = new LruCache<String,Bitmap>(20);
        final ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String s) {
                return lruCache.get(s);
            }
            @Override
            public void putBitmap(String s, Bitmap bitmap) {
                lruCache.put(s,bitmap);
            }
        };
        ImageLoader imageLoader = new ImageLoader(MyApplication.getsInstance().getMyRequestQueue(),imageCache);
        ImageLoader.ImageListener listener = imageLoader.getImageListener(vh.getPhoto(),R.drawable.ic_launcher,R.drawable.ic_launcher);
        imageLoader.get(url,listener);
    }

}
