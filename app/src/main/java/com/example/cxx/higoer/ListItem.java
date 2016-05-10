package com.example.cxx.higoer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Random;

public class ListItem extends Activity implements View.OnClickListener {
    private int currentPosition;//当前位置
    private int resultArray[];//存放随机卖家信息
    private List<ItemData> itemDatas;//卖家信息对象列表
    private String  url ;
    private TextView tv_add;
    private ImageButton back,change;
    RecyclerView recyclerView ;
    LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);
        initial();
        System.out.println("开始请求");
        getInfo(url);
    }

    private void initial() {
        Intent i = getIntent();
        Bundle b= i.getExtras();
        String time = b.getString("time");
        String place = b.getString("address");
        url = "http://10.0.2.2/personjson.php?depart_time="+time+"&place="+place;
        tv_add = (TextView) findViewById(R.id.List_add);
        back = (ImageButton) findViewById(R.id.List_back);
        change = (ImageButton) findViewById(R.id.List_change);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        tv_add.setText(place);
        back.setOnClickListener(this);
        change.setOnClickListener(this);
        currentPosition=0;
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
    }
    //json从数据库读取信息
    public void getInfo(String jsonUrl){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(jsonUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        System.out.println("请求应答");
                        if(jsonObject!=null){
                            try {
                                JSONArray jsonArray = jsonObject.getJSONArray("user");
                                String dataString = jsonArray.toString();
                                System.out.println(dataString);
                                Gson gson = new Gson();
                                itemDatas = gson.fromJson(dataString,new TypeToken<List<ItemData>>(){}.getType());
                                System.out.println("解析完毕");
                                randNum();
                                if(itemDatas.size()<=0){
                                    Toast.makeText(getApplication(),"没有查找到行程",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    listInfo(recyclerView);
                                }
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
    //adapter
    private void listInfo(RecyclerView recyclerView1) {
        MyAdapter myAdapter = new MyAdapter(itemDatas,currentData());
        recyclerView1.setAdapter(myAdapter);
        recyclerView1.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
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


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.List_back:
                finish();
                break;
            case R.id.List_change:
                if(currentPosition<itemDatas.size()) {
                    listInfo(recyclerView);
                }
                break;
        }

    }
    //获取随机数，存入resultArray中
    public void randNum() {
       int len = itemDatas.size();
        int i,seed;
        int startArray[] = new int[len];
        resultArray= new int[len];
        for(i=0;i<len;i++)
            startArray[i]=i;
        Random random = new Random();
        for(i=0;i<len;i++) {
            seed = random.nextInt(len-i);
            resultArray[i]=startArray[seed];
            System.out.println(resultArray[i]);
            startArray[seed]=startArray[len-i-1];
        }
    }
    public int[] currentData(){
        int i,rlen;
        int lowArray[]=null;
        rlen=resultArray.length;
        if(rlen<=5){
            lowArray=new int [rlen];
            for(i=0;i<rlen;i++){
                lowArray[i]=resultArray[i];
            }
        }
        else if(rlen-currentPosition>5){
            lowArray = new int[5];
            for(i=currentPosition;i<currentPosition+5;i++){
                lowArray[i-currentPosition]=resultArray[i];
            }
            currentPosition+=5;
        }
        else if(rlen-currentPosition<=5){
            lowArray= new int [rlen-currentPosition];
            for(i=currentPosition;i<rlen;i++){
                lowArray[i-currentPosition]=resultArray[i];
            }
            currentPosition=rlen;
        }
    return lowArray;
    }
}
