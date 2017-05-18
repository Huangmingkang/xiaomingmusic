package com.example.qiangshengzhineng.xiaomingmusic.adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.qiangshengzhineng.xiaomingmusic.Gitset.List_Music_shiti;
import com.example.qiangshengzhineng.xiaomingmusic.R;
import com.example.qiangshengzhineng.xiaomingmusic.utils.Utils;

import java.util.ArrayList;

/**
 * Created by qiangshengzhineng on 2017/5/11.
 */

public class MyMusicListadaper extends BaseAdapter {

    LayoutInflater inflater;
    ArrayList<List_Music_shiti> data;

    public MyMusicListadaper(Context context, ArrayList<List_Music_shiti> list){

        this.data = list;
         inflater = LayoutInflater.from(context);

    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Viewhoider hoider;

        if(convertView==null){
            hoider=new Viewhoider();
            convertView=inflater.inflate(R.layout.layout_list_misic, null);
            hoider.textView_title=(TextView) convertView.findViewById(R.id.textView_dan1);
            hoider.textView_singer=(TextView) convertView.findViewById(R.id.textView_dan2);
            hoider.textView_time=(TextView) convertView.findViewById(R.id.textView_right);
            convertView.setTag(hoider);
        }else{

            hoider=(Viewhoider) convertView.getTag();


        }

       List_Music_shiti shiti=data.get(position);
        hoider.textView_title.setText(shiti.getTitle());
        hoider.textView_singer.setText(shiti.getArtist());
        hoider.textView_time.setText(Utils.formatTime(shiti.getDuration()));
        return convertView;

    }

    static class Viewhoider{

        TextView textView_title;
        TextView textView_singer;
        TextView textView_time;

    }
}
