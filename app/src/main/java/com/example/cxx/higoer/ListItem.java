package com.example.cxx.higoer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ListItem extends Activity {
    private List<ItemData> itemDatas;
    private final String  url ="http://10.0.2.2/personjson.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        System.out.println("开始请求");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        System.out.println("请求应答");
                        if(jsonObject!=null){
                            try {
                                JSONArray jsonArray = jsonObject.getJSONArray("user");
                                String dataString = jsonArray.toString();
                                Gson gson = new Gson();
                                itemDatas = gson.fromJson(dataString,new TypeToken<List<ItemData>>(){}.getType());
                                System.out.println("解析完毕");
                                init();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("请求出错");
            }
        }
        );
        MyApplication.getsInstance().getMyRequestQueue().add(jsonObjectRequest);

    }
    private void init() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        MyAdapter myAdapter = new MyAdapter(itemDatas);
        recyclerView.setAdapter(myAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        myAdapter.setOnItemCliclListener(new MyAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(String data) {
                System.out.println("要传递的卡号是：" + data);
                Intent intent = new Intent(ListItem.this,SellerInfo.class);
                intent.putExtra("card_number",data);
                startActivity(intent);
//                Toast.makeText(getApplicationContext(),"跳转卖家信息",Toast.LENGTH_SHORT).show();
            }
        });
    }


}
