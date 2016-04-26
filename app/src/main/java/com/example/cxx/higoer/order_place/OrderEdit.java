package com.example.cxx.higoer.order_place;

import android.widget.EditText;

public class OrderEdit {
    private EditText commodity,price,amount;

    public OrderEdit( EditText commodity, EditText price,EditText amount) {
        this.amount = amount;
        this.commodity = commodity;
        this.price = price;
    }

    public EditText getAmount() {
        return amount;
    }

    public EditText getCommodity() {
        return commodity;
    }

    public EditText getPrice() {
        return price;
    }
    public String getCommodityText(){
        return commodity.getText().toString();
    }
    public String getPriceText(){
        return price.getText().toString();
    }
    public String getAmountText(){
        return amount.getText().toString();
    }
    public String isEmpty(){
        if(commodity.getText().toString().equals("")){
            return "商品名";
        }
        if(price.getText().toString().equals("")){
            return "价格";
        }
        if(amount.getText().toString().equals("")){
            return "数量";
        }
        return "";
    }
}
