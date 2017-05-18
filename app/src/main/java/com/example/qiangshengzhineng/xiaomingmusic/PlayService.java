package com.example.qiangshengzhineng.xiaomingmusic;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;

import com.example.qiangshengzhineng.xiaomingmusic.Gitset.List_Music_shiti;
import com.example.qiangshengzhineng.xiaomingmusic.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlayService extends Service {

    private MediaPlayer mPlaye;

    private int currentPosition;

    ArrayList<List_Music_shiti> mp3infos;

    private MusicUpdatalistener musicUpdatalistener;

    private ExecutorService es= Executors.newSingleThreadExecutor();

    public PlayService() {
    }




    @Override
    public IBinder onBind(Intent intent) {

        return new PlayBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPlaye = new MediaPlayer();
        mp3infos=Utils.getMp3Infos(this);
        es.execute(updataRunnble);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(es!=null&&es.isShutdown())
        es.shutdown();
        es=null;
    }

    Runnable updataRunnble = new Runnable() {
    @Override
    public void run() {

        while (true){

           if(musicUpdatalistener!=null&&mPlaye!=null&&mPlaye.isPlaying()) {

   musicUpdatalistener.onPublish(getcurrenProgress());

           }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
};

    //播放
    public void play(int position) {
        if (position >= 0 && position < mp3infos.size()) {
           List_Music_shiti mp3Info= mp3infos.get(position);

            try {
                mPlaye.reset();
                mPlaye.setDataSource(this, Uri.parse(mp3Info.getUrl()));
                mPlaye.prepare();
                mPlaye.start();
                currentPosition = position;
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (musicUpdatalistener!=null){

         musicUpdatalistener.onChange(currentPosition);

            }
        }
    }
    //暂停
    public void pause() {
        if (mPlaye.isPlaying()) {

            mPlaye.pause();

        }
    }
//停止
    public void stop(){
        if(mPlaye.isPlaying()){

            mPlaye.stop();

        }

    }
    //下一首
    public void next(){

if(currentPosition>=mp3infos.size()-1){
    currentPosition=0;
}else {

    currentPosition++;
    play(currentPosition);
}
    }
    //上一首
    public void prev(){
if (currentPosition-1<0){

    currentPosition = mp3infos.size()-1;
}else {

    currentPosition--;
}

    }

    //开始
    public void start(){

        if(!mPlaye.isPlaying()){

            mPlaye.start();

        }

    }

    public boolean isplay(){

        if(mPlaye!=null){

           return mPlaye.isPlaying();
        }
       return false;
    }

    public int getcurrenProgress(){

        if(mPlaye!=null&&mPlaye.isPlaying()){


            return mPlaye.getCurrentPosition();
        }
        return 0;
    }

    public int getDuration(){

        return mPlaye.getDuration();

    }

    public void seekTo(int msec){
        mPlaye.seekTo(msec);

    }


    //更新状态的接口
    public interface MusicUpdatalistener{

        public void onPublish(int progress);
        public void onChange(int position);



    }

    public void setMusicUpdatalistener(MusicUpdatalistener musicUpdatalistener) {
        this.musicUpdatalistener = musicUpdatalistener;
    }
    public class PlayBinder extends Binder {

        public  PlayService getPlayService(){

            return PlayService.this;

        }

    }


}
