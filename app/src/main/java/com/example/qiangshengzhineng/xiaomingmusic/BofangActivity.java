package com.example.qiangshengzhineng.xiaomingmusic;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.qiangshengzhineng.xiaomingmusic.Gitset.List_Music_shiti;
import com.example.qiangshengzhineng.xiaomingmusic.utils.Utils;

import java.util.ArrayList;

public class BofangActivity extends BaseActivity implements View.OnClickListener{

    private int position;
    private ArrayList<List_Music_shiti> mp3infos;

    private TextView bofanggemiang,dangqiang,zhong;
    private ImageView fengmiang,moshi,bofangbofang,shangyishou,bofangxiayishou,fanhui;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //去导航栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bofang);
        find();

        mp3infos= Utils.getMp3Infos(this);
        bindPlayseveice();

    }

    private void find() {

        bofanggemiang= (TextView) findViewById(R.id.textView_bofanggemiang);
        dangqiang= (TextView) findViewById(R.id.textView_dangqiang);
        zhong= (TextView) findViewById(R.id.textView_zhong);
        seekBar= (SeekBar) findViewById(R.id.seekBar);
        fanhui= (ImageView) findViewById(R.id.imageView_fanhui);
        moshi= (ImageView) findViewById(R.id.imageView_moshi);
        bofangbofang= (ImageView) findViewById(R.id.imageView_baofangbaofang);
        shangyishou= (ImageView) findViewById(R.id.imageView_shangyishou);
        bofangxiayishou= (ImageView) findViewById(R.id.imageView_bofangxiayishou);
        fengmiang= (ImageView) findViewById(R.id.imageView_fengmian);

    }

    @Override
    public void publish(int progress) {


       // dangqiang .setText(Utils.formatTime(progress));
        seekBar.setProgress(progress);

    }

    @Override
    public void change(int position) {
     if(this.playService.isplay()) {
         List_Music_shiti mp3Inf = mp3infos.get(position);
         bofanggemiang.setText(mp3Inf.getTitle());

         Bitmap albumBitmap = Utils.getArtwork(this, mp3Inf.getId(), mp3Inf.getAlbumId(), true, true);
         fengmiang.setImageBitmap(albumBitmap);
         zhong.setText(Utils.formatTime(mp3Inf.getDuration()));
         bofangbofang.setImageResource(R.mipmap.dian);
         seekBar.setProgress(0);
         seekBar.setMax((int)mp3Inf.getId());

     }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinPlaySeveice();
    }
}
