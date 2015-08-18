package com.example.lenovo.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lenovo on 2015/6/17.
 */
public class SettingAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<SettingItem> list=new ArrayList<SettingItem>();
    public SettingAdapter(Context context,ArrayList<SettingItem> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SettingItem hh=list.get(i);
        H h=null;
        if (view==null){
            h=new H();
            view= LayoutInflater.from(context).inflate(R.layout.tongxunlu,viewGroup,false);
            h.pic=(ImageView)view.findViewById(R.id.tx1);
            h.name=(TextView)view.findViewById(R.id.tx2);

            view.setTag(h);
        }
        else{
            h=(H)view.getTag();
        }
        h.pic.setImageResource(Integer.parseInt(hh.getSettingPic()
        ));

        h.name.setText(hh.getSettingName());
        return view;
    }
    public class H{
        ImageView pic;
        TextView name;
    }
}
