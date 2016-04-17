package com.example.cxx.higoer;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class AddressChoose extends Activity implements View.OnClickListener {
    private ImageView img1,img2,img3,img4;
    public String reponst=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_choose);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        img4.setOnClickListener(this);

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
