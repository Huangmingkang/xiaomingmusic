package com.example.myyinyue;

import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void chax(View view){

        ContentResolver cr = getContentResolver();
        Cursor c=cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        ,null,null,null,null);

        if(c!=null){
             while (c.moveToNext()){

                  String url=c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA));
                 String songName=c.getString(c.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                String artist= c.getString(c.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                 System.out.print("hello"+url);
                 //Log.d("hello",songName);
                 //Log.d("hello",artist);
                 //Log.d("hello","________--------_______");

             }


        }


    }
    MediaPlayer mp1 ;
public void noo(View v){

mp1.stop();


}
    public void playfromRes(View v){
        mp1 = MediaPlayer.create(this,R.raw.qq);

         mp1.start();
    }

    public void playFromsys(View view){

        MediaPlayer mp=new MediaPlayer();
       // String path="／data/media/0/netease/cloudmusic/Music/弹珠传说片尾曲 - 心 - 郭妮.mp3";
String path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)+"/弹珠传说片尾曲 - 心 - 郭妮.mp3";
        try {
            mp.setDataSource(this, Uri.parse(path));
            mp.prepare();//同步执行
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void playFrominf(View v){

       String path= "http://sc1.111ttt.com:8282/2017/4/05/10/298101104389.mp3?tflag=1494482399&pin=39cc54fe559499c5d9e4cf5416859732";
     MediaPlayer mp = new MediaPlayer();
        try {
            mp.setDataSource(this,Uri.parse(path));
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            mp.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
           //
        }


    }
}
