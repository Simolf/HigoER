package com.example.cxx.higoer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.cxx.higoer.volleyget.GetImage;

public class AddressChoose extends Activity implements View.OnClickListener {
    private ImageView[] img = new ImageView[4];
    private String url = "http://10.0.2.2/address";
    public String reponst=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_choose);
        img[0] = (ImageView) findViewById(R.id.img1);
        img[1] = (ImageView) findViewById(R.id.img2);
        img[2] = (ImageView) findViewById(R.id.img3);
        img[3] = (ImageView) findViewById(R.id.img4);
        img[0].setOnClickListener(this);
        img[1].setOnClickListener(this);
        img[2].setOnClickListener(this);
        img[3].setOnClickListener(this);
        GetImage getImage = new GetImage();
        for(int i=0;i<4;i++){
            String imageUrl = url+i+".jpg";
            getImage.getLoadImage(img[i],imageUrl);
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img1:
                reponst="img1";
                break;
            case R.id.img2:
                reponst="img2";
                break;
            case R.id.img3:
                reponst="img3";
                break;
            case R.id.img4:
                reponst="img4";
                break;
        }
        Intent intent = new Intent();
        intent.putExtra("address",reponst);
        setResult(1,intent);
        finish();
    }
}
