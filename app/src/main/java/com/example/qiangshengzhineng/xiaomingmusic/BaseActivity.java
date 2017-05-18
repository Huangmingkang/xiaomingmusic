package com.example.qiangshengzhineng.xiaomingmusic;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;


/**
 * Created by qiangshengzhineng on 2017/5/15.
 */

public abstract class BaseActivity extends FragmentActivity {

    protected PlayService playService;


    private boolean isbound = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            PlayService.PlayBinder playBinder=(PlayService.PlayBinder)service;

            playService = playBinder.getPlayService();

            playService.setMusicUpdatalistener(musicUpdatalistener);

           // musicUpdatalistener.onChange();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            playService = null;
            isbound=false;
        }
    };

    private PlayService.MusicUpdatalistener musicUpdatalistener=new PlayService.MusicUpdatalistener() {

        @Override
        public void onPublish(int progress) {
            publish(progress);

        }

        @Override
        public void onChange(int position) {

            change(position);
        }
    };

    public abstract void publish(int progress);
    public abstract void change(int position);


    //绑定服务
    public void bindPlayseveice(){

         if(isbound==false) {
            Intent intent = new Intent(this, PlayService.class);
            bindService(intent, conn, Context.BIND_AUTO_CREATE);
}
    }

    public void unbinPlaySeveice(){
          if (isbound) {
           unbindService(conn);
             isbound=false;

}
    }
}
