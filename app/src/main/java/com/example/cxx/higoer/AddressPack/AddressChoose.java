package com.example.cxx.higoer.AddressPack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.cxx.higoer.MyApplication;
import com.example.cxx.higoer.R;
import com.example.cxx.higoer.volleyget.GetImage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddressChoose extends Activity implements View.OnClickListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    private ImageView[] img = new ImageView[4];
    private String url = "http://10.0.2.2/address";
    public String reponst="香港";
    private SearchView search;
    private ListView listView;
    private List<AddressEntity> addressEntities = new ArrayList<AddressEntity>();
    ArrayList<String>arrayList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_choose);

        search = (SearchView) findViewById(R.id.search);
        listView = (ListView) findViewById(R.id.list);
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
        getInfo();
        initList();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img1:
                reponst="香港";
                break;
            case R.id.img2:
                reponst="澳门";
                break;
            case R.id.img3:
                reponst="韩国";
                break;
            case R.id.img4:
                reponst="日本";
                break;
        }
        Intent intent = new Intent();
        intent.putExtra("address",reponst);
        setResult(1,intent);
        finish();
    }
    public void initList(){
        search.setOnQueryTextListener(this);
        search.setOnCloseListener(this);
        search.setSubmitButtonEnabled(false);
        listView.setAdapter(new ArrayAdapter<Object>(getApplicationContext(),android.R.layout.simple_expandable_list_item_1,arrayList.toArray()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(AddressChoose.this,"选择"+arrayList.get(position).toString(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("address",arrayList.get(position).toString());
                setResult(1,intent);
                finish();


            }
        });
    }
    public void getInfo(){
        String Url="http://10.0.2.2/address.php";
        System.out.println(Url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                System.out.println("onresponse");
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("address");
                    String dataString = jsonArray.toString();
                    System.out.println(dataString);
                    Gson gson = new Gson();
                    addressEntities = gson.fromJson(dataString,new TypeToken<List<AddressEntity>>(){}.getType());
                    for(int i=0;i<addressEntities.size();i++){
                        arrayList.add(addressEntities.get(i).getPlace());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("errorresponse");
                Log.e("-----------",volleyError.getMessage());
            }
        });
        MyApplication.getsInstance().getMyRequestQueue().add(jsonObjectRequest);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listView.setVisibility(View.VISIBLE);
        for(int i=0;i<4;i++){
            img[i].setVisibility(View.GONE);
        }
        Object[] obj = searchItem(newText);
        updateLayout(obj);
        return false;
    }
    public Object[]searchItem(String name){
        ArrayList<String> mSearchList = new ArrayList<String>();
        for(int i=0;i<arrayList.size();i++){
            int index = arrayList.get(i).indexOf(name);
            if(index!=-1){
                mSearchList.add(arrayList.get(i));
            }
        }
        return mSearchList.toArray();
    }
    public void updateLayout(Object[]obj){
        listView.setAdapter(new ArrayAdapter<Object>(getApplicationContext(),android.R.layout.simple_expandable_list_item_1,obj));
    }

    @Override
    public boolean onClose() {
        listView.setVisibility(View.GONE);
        for(int i=0;i<4;i++)
            img[i].setVisibility(View.VISIBLE);
        return false;
    }
}
