package com.example.cxx.higoer;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cxx.higoer.volleyget.GetImage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainFragment extends Fragment implements View.OnClickListener {
    private ImageView iv;
    private Button btnDate,search,btnAddress;
    Calendar calendar = Calendar.getInstance();
    private int syear,smonth,sday,sweek;
    private String address = "香港";
    GetImage getImage= new GetImage();
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_layout,container,false);
        init(root);
        dateInit(root);
        return root;
    }
    //初始化界面
    public void init(View root){
        iv = (ImageView) root.findViewById(R.id.iv);
        btnAddress = (Button) root.findViewById(R.id.btnAddress);
        btnDate = (Button) root.findViewById(R.id.btnDate);
        search = (Button) root.findViewById(R.id.search);
        btnAddress.setOnClickListener(this);
        btnDate.setOnClickListener(this);
        search.setOnClickListener(this);
        getImage.getLoadImage(iv,"http://10.0.2.2/address0.jpg");
    }
    //时间初始化，当前系统时间
    public void dateInit(View root){
        syear = calendar.get(Calendar.YEAR);
        smonth = calendar.get(Calendar.MONTH)+1;
        sday = calendar.get(Calendar.DAY_OF_MONTH);
        sweek = calendar.get(Calendar.DAY_OF_WEEK);

        btnDate.setText("出发时间："+syear+"-"+smonth+"-"+sday+"      星期"+ChooseWeek(sweek));
    }
    //根据当前时间判断周几
    private String ChooseWeek(int w){
        switch (w){
            case 2:return "一";
            case 3:return "二";
            case 4:return "三";
            case 5:return "四";
            case 6:return "五";
            case 7:return "六";
           default:return "日";

        }
    }
    @Override
    //按钮点击事件
    public void onClick(View v) {
     switch (v.getId()){
         //地址选择
         case R.id.btnAddress:
             Intent intent = new Intent(getActivity(),AddressChoose.class);
             startActivityForResult(intent,0);
             break;
         //时间选择
         case R.id.btnDate:
             new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                 @Override
                 public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                     syear=year;smonth= monthOfYear+1;sday=dayOfMonth;
                     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                     String day=syear+"-"+smonth+"-"+sday;
                     try {
                         calendar.setTime(format.parse(day));
                     } catch (ParseException e) {
                         e.printStackTrace();
                     }
                     sweek=calendar.get(Calendar.DAY_OF_WEEK);
                     btnDate.setText("出发时间："+day+"      星期"+ChooseWeek(sweek));
                 }
             },syear,smonth-1,sday).show();
             break;
         //搜索按钮，跳转
         case R.id.search:
             Intent intent1 = new Intent(getActivity(),ListItem.class);
             String time = syear+"-"+smonth+"-"+sday;
             System.out.println(time);
             System.out.println(address);
             Bundle b = new Bundle();
             b.putString("time",time);//时间
             b.putString("address",address);//地点
             intent1.putExtras(b);
             startActivity(intent1);
             break;
     }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String respont = data.getStringExtra("address");
        String url = "http://10.0.2.2/address";
        if(respont.equals("img1")){
            getImage.getLoadImage(iv, url + "0.jpg");
//            iv.setBackgroundResource(R.drawable.hk1);
            address = "香港";
        }
        if(respont.equals("img2")){
            getImage.getLoadImage(iv,url+"1.jpg");
//            iv.setBackgroundResource(R.drawable.am);
            address = "澳门";
        }
        if(respont.equals("img3")){
            getImage.getLoadImage(iv,url+"2.jpg");
//            iv.setBackgroundResource(R.drawable.hg);
            address = "韩国";
        }
        if(respont.equals("img4")){
            getImage.getLoadImage(iv,url+"3.jpg");
//            iv.setBackgroundResource(R.drawable.rb);
            address = "日本";
        }
    }

}
