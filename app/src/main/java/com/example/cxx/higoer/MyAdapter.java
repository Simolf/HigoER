package com.example.cxx.higoer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.cxx.higoer.volleyget.GetImage;

import java.util.List;

class MyAdapter extends RecyclerView.Adapter implements View.OnClickListener{
    private List<ItemData> datas;
    public GetImage getImage = new GetImage();
    MyAdapter(List<ItemData>datas){
        System.out.println("constructor");
        this.datas = datas;
    }

    //模仿OnItemClickListener自定义接口
    public static interface OnRecyclerViewItemClickListener{
        void onItemClick(String data);
    }
    private OnRecyclerViewItemClickListener myOnItemClickListener = null;

    @Override
    public void onClick(View v) {
        if(myOnItemClickListener!= null){
            //将点击事件转移给外面的调用者
            myOnItemClickListener.onItemClick((String)v.getTag());
        }
    }

    public void setOnItemCliclListener(OnRecyclerViewItemClickListener listener){
        this.myOnItemClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private View root;
        private NetworkImageView photo;
        private TextView name;
        private TextView phone;
        private TextView type;
        public TextView getPhone() {
            return phone;
        }
        public TextView getName() {
            return name;
        }
        public NetworkImageView getPhoto() {
            return photo;
        }
        public TextView getType() {
            return type;
        }
        public ViewHolder(View root) {
            super(root);
            photo = (NetworkImageView)root.findViewById(R.id.List_photo);
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
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder vh = (ViewHolder) viewHolder;
        vh.getName().setText(datas.get(i).getName());
        System.out.println(datas.get(i).getName());
        vh.getPhone().setText(datas.get(i).getPhone());
        vh.getType().setText(datas.get(i).getCard_number());
        getImage.getNetworkImage(vh.getPhoto(),datas.get(i).getPhoto());
        vh.itemView.setTag(datas.get(i).getCard_number());
    }

    @Override
    //子对象的数量
    public int getItemCount() {
        if(datas.size()<5)
            return datas.size();
        else
            return 5;

    }
}
