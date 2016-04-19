package com.example.cxx.higoer.volleyget;

import android.graphics.Bitmap;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.cxx.higoer.MyApplication;
import com.example.cxx.higoer.R;

public class GetImage {
    private RequestQueue requestQueue = MyApplication.getsInstance().getMyRequestQueue();
    private LruCache<String,Bitmap> lruCache = MyApplication.getsInstance().getLruCache();
    public void  getLoadImage(ImageView image,String imageUrl){
        ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String s) {
                return lruCache.get(s);
            }
            @Override
            public void putBitmap(String s, Bitmap bitmap) {
                lruCache.put(s,bitmap);
            }
        };
        ImageLoader imageLoad = new ImageLoader(requestQueue,imageCache);
        ImageLoader.ImageListener listener = imageLoad.getImageListener(image, R.drawable.ic_launcher,R.drawable.ic_launcher);
        imageLoad.get(imageUrl,listener);
    }
    public  void getNetworkImage(NetworkImageView imageView,String imageUrl){
        ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String s) {
                System.out.println("正在获取照片");
                return lruCache.get(s);
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {
                System.out.println("传递照片地址："+s);
                lruCache.put(s,bitmap);
            }
        };
        ImageLoader imageLoader = new ImageLoader(requestQueue,imageCache);
        imageView.setTag("url");
        imageView.setDefaultImageResId(R.drawable.ic_launcher);
        imageView.setErrorImageResId(R.drawable.ic_launcher);
        imageView.setImageUrl(imageUrl, imageLoader);
        System.out.println("照片获取结束");
    }
}
