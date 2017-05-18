package com.example.qiangshengzhineng.xiaomingmusic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qiangshengzhineng.xiaomingmusic.Gitset.List_Music_shiti;
import com.example.qiangshengzhineng.xiaomingmusic.adaper.MyMusicListadaper;
import com.example.qiangshengzhineng.xiaomingmusic.utils.Utils;

import java.util.ArrayList;

/**
 * Created by qiangshengzhineng on 2017/5/11.
 */

public class MyMusicListFragment extends Fragment implements AdapterView.OnItemClickListener,View.OnClickListener{

    private ListView lv;
    private ArrayList<List_Music_shiti> mp3infos;
    private MainActivity mainActiviay;
    private MyMusicListadaper myMusicListadaper;
    private ImageView imageView_album;
    private TextView textView_geming,textView_geshou;
    private ImageView imageView_zhanting,imageView_xiayishou;
    private boolean ispuase=false;
    private int position=0;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActiviay= (MainActivity) context;
    }

    public static MyMusicListFragment newInstance() {

        MyMusicListFragment my = new MyMusicListFragment();

        return my;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.my_music_list_layout,null);

        lv= (ListView) view.findViewById(R.id.list_lie);
        imageView_album= (ImageView) view.findViewById(R.id.imageView_lie);
        imageView_zhanting= (ImageView) view.findViewById(R.id.imageView_zhanting);
        imageView_xiayishou= (ImageView) view.findViewById(R.id.imageView_xiayishou);
        textView_geming= (TextView) view.findViewById(R.id.textView_geming);
        textView_geshou= (TextView) view.findViewById(R.id.textView_geshou);


        imageView_album.setOnClickListener(this);
        imageView_zhanting.setOnClickListener(this);
        imageView_xiayishou.setOnClickListener(this);
        lv.setOnItemClickListener(this);
        loadData();
        mainActiviay.bindPlayseveice();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainActiviay.unbinPlaySeveice();
    }

    private void loadData() {
        mp3infos = Utils.getMp3Infos(mainActiviay);
        myMusicListadaper=new MyMusicListadaper(mainActiviay,mp3infos);
        lv.setAdapter(myMusicListadaper);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        mainActiviay.playService.play(position);



    }

    //回调
    public  void changeUIstatusOnolay(int position){

          if(position>=0&&position<mp3infos.size()){

              List_Music_shiti mp3Inf = mp3infos.get(position);

              textView_geshou.setText(mp3Inf.getArtist());
              textView_geming.setText(mp3Inf.getTitle());
              imageView_zhanting.setImageResource(R.mipmap.next);


            Bitmap albumBitmap = Utils.getArtwork(mainActiviay,mp3Inf.getId(),mp3Inf.getAlbumId(),true,true);

              imageView_album.setImageBitmap(albumBitmap);
              this.position=position;
          }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.imageView_xiayishou:

                mainActiviay.playService.next();

              break;
            case R.id.imageView_zhanting:{
                mainActiviay.playService.isplay();
                if(mainActiviay.playService.isplay()){
                    imageView_zhanting.setImageResource(R.mipmap.play);
                    mainActiviay.playService.pause();
                    ispuase=true;

                }else {


                    if(ispuase) {
                        imageView_zhanting.setImageResource(R.mipmap.next);
                        mainActiviay.playService.start();
                    }else {
                        mainActiviay.playService.play(0);
                    }

                    ispuase=false;

                }

                break;
            }

            case R.id.imageView_lie:
                Intent intent=new Intent(mainActiviay,BofangActivity.class);
               intent.putExtra("position",position);
                startActivity(intent);

                break;
        }


    }
}
