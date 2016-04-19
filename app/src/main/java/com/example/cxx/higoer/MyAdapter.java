package com.example.cxx.higoer;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.ImageLoader.ImageListener;
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
        System.out.println(datas.get(i).getName());
        vh.getPhone().setText(datas.get(i).getPhone());
        vh.getType().setText(datas.get(i).getCar_number());
        System.out.println(datas.get(i).getPhoto());
        getImage.getNetworkImage(vh.getPhoto(),datas.get(i).getPhoto());
//        getImage(vh, datas.get(i).getPhoto());
//        vh.getPhoto().setImageResource(R.drawable.ic_launcher);
        vh.itemView.setTag(datas.get(i).getName());
    }

    @Override
    //子对象的数量
    public int getItemCount() {
        System.out.println("getcount");
        return 5;

    }
//    public void getImage(ViewHolder viewHolder,String imageUrl){
//        System.out.println("获取照片");
//        RequestQueue requestQueue = MyApplication.getsInstance().getMyRequestQueue();
//        final LruCache<String,Bitmap> lruCache =MyApplication.getsInstance().getLruCache();
//        ImageCache imageCache = new ImageCache() {
//            @Override
//            public Bitmap getBitmap(String s) {
//                System.out.println("正在获取照片");
//                return lruCache.get(s);
//            }
//
//            @Override
//            public void putBitmap(String s, Bitmap bitmap) {
//                System.out.println("传递照片地址："+s);
//                lruCache.put(s,bitmap);
//            }
//        };
//        ImageLoader imageLoader = new ImageLoader(requestQueue,imageCache);
//        viewHolder.getPhoto().setTag("url");
//        viewHolder.getPhoto().setDefaultImageResId(R.drawable.ic_launcher);
//        viewHolder.getPhoto().setErrorImageResId(R.drawable.ic_launcher);
//        viewHolder.getPhoto().setImageUrl(imageUrl, imageLoader);
//        System.out.println("照片获取结束");
//
//    }
}
