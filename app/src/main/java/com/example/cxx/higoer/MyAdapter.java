package com.example.cxx.higoer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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

class MyAdapter extends RecyclerView.Adapter implements View.OnClickListener{
    private List<ItemData> datas;
    String stringjson;
    MyAdapter(List<ItemData>datas){
        System.out.println("constructor");
        this.datas = datas;
    }

    //模仿OnItemClickListener自定义接口
    public static interface OnRecyclerViewItemClickListener{
        void onItemClick(View view, String data);
    }
    private OnRecyclerViewItemClickListener myOnItemClickListener = null;

    @Override
    public void onClick(View v) {
        if(myOnItemClickListener!= null){
            //将点击事件转移给外面的调用者
            myOnItemClickListener.onItemClick(v,(String)v.getTag());
        }
    }

    public void setOnItemCliclListener(OnRecyclerViewItemClickListener listener){
        this.myOnItemClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private View root;
        private ImageView photo;
        private TextView name;
        private TextView phone;
        private TextView type;
        public TextView getPhone() {
            return phone;
        }
        public TextView getName() {
            return name;
        }
        public ImageView getPhoto() {
            return photo;
        }
        public TextView getType() {
            return type;
        }
        public ViewHolder(View root) {
            super(root);
            photo = (ImageView) root.findViewById(R.id.List_photo);
            name = (TextView) root.findViewById(R.id.List_name);
            phone = (TextView) root.findViewById(R.id.List_phone);
            type = (TextView) root.findViewById(R.id.List_type);
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
         View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_set,null);
        ViewHolder vh = new ViewHolder(view);
        //为每个item添加点击事件
//        view.setOnClickListener(this);
        System.out.println("oncreate");

//        getPerson();
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder vh = (ViewHolder) viewHolder;
        System.out.println("ViewHolder");
        System.out.println(i);
        vh.getName().setText(datas.get(i).getName());
        vh.getPhone().setText(datas.get(i).getPhone());
        vh.getType().setText(datas.get(i).getCar_number());
        vh.getPhoto().setImageResource(R.drawable.ic_launcher);
        //vh.getName().setText(data.getName(i));
//        vh.getType().setText(data.getType(i));
//        vh.getPhone().setText(data.getPhone(i));
//        data.getImage(vh,data.getPhoto(i));
        //ItemData itemData =data[i];

//        vh.getTvTitle().setText(itemData.title);
//        vh.getTvContent().setText(itemData.content);
        //定义tag全局变量，用于验证点击事件的发生
        //vh.itemView.setTag(itemData.title);
    }

    @Override
    //子对象的数量
    public int getItemCount() {
        System.out.println("getcount");
        return 5;

    }
}
