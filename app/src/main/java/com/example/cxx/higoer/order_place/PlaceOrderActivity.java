package com.example.cxx.higoer.order_place;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.cxx.higoer.MyApplication;
import com.example.cxx.higoer.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaceOrderActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    OrderAdapter myAdapter;
    private List<CommodityEditText>commodityInfoList;
    private List<OrderData>orders;
    private int list_amount;
    private String seller_number;
    private String orderJson;
    private Button sumbit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_place_order);
        //启动actionbar上的返回键
//        getActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        sumbit = (Button) findViewById(R.id.sumbit);
        list_amount=1;
        //获取选择的卖家卡号
        Intent i = getIntent();
        Bundle b = i.getExtras();
        seller_number = b.getString("seller_number");
        //设置recyclerView的adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
//       recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        myAdapter = new OrderAdapter(list_amount);
        recyclerView.setAdapter(myAdapter);
        sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orders = new ArrayList<OrderData>();
                int b=1;
                Calendar calendar = Calendar.getInstance();
                String orderNum=seller_number+calendar.getTime().toString();
                commodityInfoList = myAdapter.getCommodities();
                if(commodityInfoList.size()==0){
                    System.out.println("EditList 为空");
                }
                else{
                    for(int i=0;i<commodityInfoList.size();i++)
                        if(!commodityInfoList.get(i).isEmpty().equals("")){
                            Toast.makeText(getApplicationContext(),commodityInfoList.get(i).isEmpty()+"为空",Toast.LENGTH_SHORT).show();
                            b=0;
                        }
                    if(b!=0) {
                        for (int i = 0; i < commodityInfoList.size(); i++) {
                            OrderData order = new OrderData();
                            order.setGood_name(commodityInfoList.get(i).getCommodityText());
                            order.setPrice(commodityInfoList.get(i).getPriceText());
                            order.setAmount(commodityInfoList.get(i).getAmountText());
                            order.setOrder_number(orderNum);
                            orders.add(order);
                        }
                        putJson();
                    }

                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.order_recycler, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            //返回键
            case android.R.id.home:
                finish();
                break;
            //添加按钮
            case R.id.action_add:
                myAdapter.add("New String", OrderAdapter.LAST_POSITION);
                break;
            //删除按钮
            case R.id.action_remove:
                myAdapter.remove(OrderAdapter.LAST_POSITION);
                Toast.makeText(this,"item："+myAdapter.getItemCount(),Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    //提交订单信息至服务器文件
    public void putJson(){
        RequestQueue requestQueue = MyApplication.getsInstance().getMyRequestQueue();
        String url="http://10.0.2.2/add_order.php";
        Gson gson = new Gson();
        orderJson = gson.toJson(orders);
        System.out.println(orderJson);
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                System.out.println("返回的数据是："+s);
                Toast.makeText(getApplicationContext(),"成功添加订单",Toast.LENGTH_SHORT).show();
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(),"添加失败，请检查网络",Toast.LENGTH_SHORT).show();
            }
        };
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,listener,errorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>map = new HashMap<String,String>();
                map.put("order",orderJson);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
