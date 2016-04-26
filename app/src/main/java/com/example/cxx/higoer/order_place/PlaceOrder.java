package com.example.cxx.higoer.order_place;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.cxx.higoer.R;

import java.util.List;

public class PlaceOrder extends AppCompatActivity {
    RecyclerView recyclerView;
    OrderAdapter myAdapter;
    private List<CommodityInfo>commodityInfoList;
    private int list_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        list_amount=1;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
//       recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        myAdapter = new OrderAdapter(list_amount);
        recyclerView.setAdapter(myAdapter);
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

        if(id==R.id.action_add){
            myAdapter.add("New String", OrderAdapter.LAST_POSITION);
            return true;
        }
        if(id==R.id.action_remove){
            commodityInfoList = myAdapter.getCommodities();
            if(!commodityInfoList.get(0).isEmpty().equals(""))
                Toast.makeText(this,commodityInfoList.get(0).isEmpty()+"为空",Toast.LENGTH_SHORT).show();
            else {
                System.out.println("第一个商品是："+commodityInfoList.get(0).getCommodityText());
                Toast.makeText(this,commodityInfoList.get(0).getCommodityText(), Toast.LENGTH_SHORT).show();
            }
//            myAdapter.remove(MyAdapter.LAST_POSITION);
//            Toast.makeText(this,"item："+myAdapter.getItemCount(),Toast.LENGTH_SHORT).show();

            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
