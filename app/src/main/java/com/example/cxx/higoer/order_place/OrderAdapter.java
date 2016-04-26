package com.example.cxx.higoer.order_place;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.cxx.higoer.R;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter{
    public static final int LAST_POSITION=-1;
    private List<String> mData;
    private List<CommodityInfo>commodities;
    private int list_amount;
    CommodityInfo commodityInfo;

    public List<CommodityInfo> getCommodities() {
        return commodities;
    }

    public OrderAdapter(int list_amount){
        commodities = new ArrayList<CommodityInfo>();
        this.list_amount = list_amount;

    }
    public  void add(String s,int position){
        position =position == LAST_POSITION ? getItemCount():position;
        list_amount++;
        notifyItemInserted(position);
    }
    public void remove(int position){
        if(position==LAST_POSITION &&getItemCount()>0)
            position = getItemCount()-1;
        if(position>LAST_POSITION&&position<getItemCount()){
            list_amount--;
            notifyItemRemoved(position);
        }
    }
    private static class myViewHolder extends RecyclerView.ViewHolder {
        public EditText commodity,price,amount;
        public myViewHolder(View itemView) {
            super(itemView);
            commodity = (EditText) itemView.findViewById(R.id.commodity);
            price = (EditText) itemView.findViewById(R.id.price);
            amount = (EditText) itemView.findViewById(R.id.amount);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_item,viewGroup,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        myViewHolder myViewHolder  = (myViewHolder) holder;
        commodityInfo = new CommodityInfo(myViewHolder.commodity,myViewHolder.price,myViewHolder.amount);
        commodities.add(commodityInfo);
//        myViewHolder.title.setText(mData.get(position));
//        editTexts.add(myViewHolder.content);
//        System.out.println(editTexts.get(position).getText().toString());
    }


    @Override
    public int getItemCount() {
        return list_amount;
    }
}
