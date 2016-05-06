package com.example.cxx.higoer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.cxx.higoer.order_place.PlaceOrder;
import com.example.cxx.higoer.volleyget.GetImage;
import com.google.gson.Gson;

public class SellerInfo extends AppCompatActivity {
    private TextView name,card_number,phone,domitory;
    private TextView goData,backData,destination,message;
    private ImageView photo;
    private Button findhim;
    private ImageButton seller_back;
    private String buyer_card;
    private String url;
    SellerData sellerData = new SellerData();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_info);
        init();
        Intent intent = getIntent();
        buyer_card = intent.getStringExtra("card_number");
        System.out.println(buyer_card);
        url = "http://10.0.2.2/seller_information.php?card_number="+buyer_card;
        getData();
    }
    public void init(){
        name = (TextView) findViewById(R.id.name);
        card_number = (TextView) findViewById(R.id.card_num);
        phone = (TextView) findViewById(R.id.phone);
        domitory  = (TextView) findViewById(R.id.address);
        goData = (TextView) findViewById(R.id.go_data);
        backData = (TextView) findViewById(R.id.back_data);
        destination = (TextView) findViewById(R.id.destination);
        message = (TextView) findViewById(R.id.message);
        photo = (ImageView) findViewById(R.id.person_photo);
        findhim = (Button) findViewById(R.id.findhim);
        seller_back = (ImageButton) findViewById(R.id.seller_back);
        findhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SellerInfo.this, PlaceOrder.class);
                Bundle b = new Bundle();
                b.putString("seller_number",sellerData.getCard_number());
                i.putExtras(b);
                System.out.println("要传递的卖家号为："+sellerData.getCard_number());
                startActivity(i);
            }
        });
        seller_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getData() {
        RequestQueue queue = MyApplication.getsInstance().getMyRequestQueue();
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                System.out.println(s);
                Gson gson = new Gson();
                sellerData = gson.fromJson(s,SellerData.class);
                putInfo();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("获取失败");
            }
        });
        queue.add(request);
    }

    private void putInfo() {
        name.append(sellerData.getName());
        card_number.append(sellerData.getCard_number());
        phone.append(sellerData.getPhone());
        domitory.append(sellerData.getDomitory());
        destination.append(sellerData.getPlace());
        goData.append(sellerData.getDepart_time());
        backData.append(sellerData.getReturn_time());
        message.append(sellerData.getInformation());
        new GetImage().getLoadImage(photo,sellerData.getPhoto());

    }

}
