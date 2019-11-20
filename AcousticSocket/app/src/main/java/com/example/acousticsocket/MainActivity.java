package com.example.acousticsocket;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mina.MinaThread;
import com.example.record.AudioRecorder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements View.OnClickListener {
    Button start;
    Button pause;
    Button pcmList;
    Button wavList;
    Button button_timer;
    Button button_t_cos;
    Button button_allug;
    RadioGroup RadioGroup_signal;
    RadioButton radioButton_ContinueChirp;
    RadioButton radioButton_ImpulseChirp;
    RadioButton radioButton_ContinueCos;
    RadioButton radioButton_ContinueMulCos;
    RadioButton radioButton_ContinueminMulCos;
    RadioButton radioButton_ultragesture;
    RadioButton allRebuildUG;
    TextView textView;

    int count;
    String playername;
    AudioRecorder audioRecorder;
    MediaPlayer player,player1;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        addListener();

        new Thread(new Runnable() {
            @Override
            public void run() {
                new MinaThread().start();
            }
        }).start();

    }

    private void addListener() {
        start.setOnClickListener(this);
        pause.setOnClickListener(this);
        pcmList.setOnClickListener(this);
        wavList.setOnClickListener(this);
        button_timer.setOnClickListener(this);
        button_t_cos.setOnClickListener(this);
        button_allug.setOnClickListener(this);

        RadioGroup_signal.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId){
                if(radioButton_ContinueChirp.getId()==checkedId){
                    playername = "continuechirp";
                }
                if(radioButton_ContinueCos.getId()==checkedId){
                    playername = "continuecos";
                }
                if(radioButton_ContinueMulCos.getId()==checkedId){
                    playername = "continuemulcos";
                }
                if(radioButton_ContinueminMulCos.getId()==checkedId){
                    playername = "cos150";
                }
                if(radioButton_ImpulseChirp.getId()==checkedId){
                    playername = "chirpa";
                }
                if(radioButton_ultragesture.getId()==checkedId){
                    playername = "ultragesture";
                }
                if(allRebuildUG.getId()==checkedId)
                {
                    playername="allrebuildultragesture";
                }
            }
        });



    }

    private void init() {
        start = (Button) findViewById(R.id.start);
        pause = (Button) findViewById(R.id.pause);
        pcmList = (Button) findViewById(R.id.pcmList);
        wavList = (Button) findViewById(R.id.wavList);
        button_timer = (Button)findViewById(R.id.button_timer);
        button_t_cos = (Button)findViewById(R.id.button_tcos);
        button_allug = (Button)findViewById(R.id.button_allug);
        textView = (TextView)findViewById(R.id.textView);
        pause.setVisibility(View.GONE);
        audioRecorder = AudioRecorder.getInstance();
        RadioGroup_signal = (RadioGroup)findViewById(R.id.signal);
        radioButton_ContinueChirp = (RadioButton)findViewById(R.id.radioButton_ContinueChirp);
        radioButton_ImpulseChirp = (RadioButton)findViewById(R.id.radioButton_ImpulseChirp);
        radioButton_ContinueCos = (RadioButton)findViewById(R.id.radioButton_ContinueCos);
        radioButton_ContinueMulCos = (RadioButton)findViewById(R.id.radioButton_ContinueMulCos);
        radioButton_ContinueminMulCos = (RadioButton)findViewById(R.id.radioButton_ContinueminMulCos);
        radioButton_ultragesture = (RadioButton)findViewById(R.id.radioButton_ultragesture);
        allRebuildUG = (RadioButton)findViewById(R.id.allRebuildUG);

        count=0;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                try {
                    if (audioRecorder.getStatus() == AudioRecorder.Status.STATUS_NO_READY) {
                        //初始化录音
                        if(playername=="continuechirp") {
                            player = MediaPlayer.create(this, R.raw.continuechirp);
                        }
                        if(playername=="chirpa") {
                            player = MediaPlayer.create(this, R.raw.rebuildultragesture);
                        }
                        if(playername=="continuemulcos") {
                            player = MediaPlayer.create(this, R.raw.continuemulcos);
                        }
                        if(playername=="continuecos") {
                            player = MediaPlayer.create(this, R.raw.continuecos);
                        }
                        if(playername=="cos150") {
                            player = MediaPlayer.create(this, R.raw.cos);
                        }
                        if(playername=="ultragesture"){
                            player = MediaPlayer.create(this,R.raw.rebuildultragesture);
                        }
                        if(playername=="allrebuildultragesture")
                        {
                            player = MediaPlayer.create(this,R.raw.allrebuildultragesture);
                        }



//        MediaPlayer player =  MediaPlayer.create(this,R.raw.test);
                        player.start();
                        String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
                        audioRecorder.createDefaultAudio(fileName);
                        audioRecorder.startRecord(null);
                        System.out.println("当前状态为："+audioRecorder.getStatus());


                        start.setText("停止录音");

                        pause.setVisibility(View.VISIBLE);

                    } else {
                        //停止录音
                        audioRecorder.stopRecord();
                        start.setText("开始录音");
                        pause.setText("暂停录音");
                        pause.setVisibility(View.GONE);
                        player.stop();
                    }

                } catch (IllegalStateException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.pause:
                try {
                    if (audioRecorder.getStatus() == AudioRecorder.Status.STATUS_START) {
                        //暂停录音
                        audioRecorder.pauseRecord();
                        pause.setText("继续录音");
                        player.stop();
                        break;

                    } else {
                        audioRecorder.startRecord(null);
                        pause.setText("暂停录音");

                    }
                } catch (IllegalStateException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.pcmList:
                Intent showPcmList = new Intent(MainActivity.this, ListActivity.class);
                showPcmList.putExtra("type", "pcm");
                startActivity(showPcmList);
                break;

            case R.id.wavList:
                Intent showWavList = new Intent(MainActivity.this, ListActivity.class);
                showWavList.putExtra("type", "wav");
                startActivity(showWavList);
                break;

            case R.id.button_timer:
                Timer t=new Timer();
                player = MediaPlayer.create(this,R.raw.rebuildultragesture);
                myTaskStart mytaskstart = new myTaskStart();
                t.schedule(mytaskstart,5000,4000);
                break;
            case R.id.button_tcos:
                Timer t2=new Timer();
                player = MediaPlayer.create(this,R.raw.cos);
                myTaskStart mytaskstart2 = new myTaskStart();
                t2.schedule(mytaskstart2,5500,4500);
                break;
            case R.id.button_allug:
                Timer t3=new Timer();
                player = MediaPlayer.create(this,R.raw.cos);
                myTaskStart mytaskstart3 = new myTaskStart();
                t3.schedule(mytaskstart3,5500,4000);
                break;
        }
    }
class myTaskStart extends TimerTask {

        @Override
    public void run(){
            //开始录音
                System.out.println("启动程序");
                if(count<110){
                    count=count+1;
                }
                else {
                    this.cancel();
                    System.exit(0);
                }
            textView.post(new Runnable() {
                @Override
                public void run() {
                    textView.setText("start"+count);
                }
            });
                player.start();
                player.setVolume(1f,1f);
                fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
                audioRecorder.createDefaultAudio(fileName);    // status = Status.STATUS_READY;
                audioRecorder.startRecord(null);

              //等待录音
            try {
                Thread.sleep(2500);
            }
            catch (InterruptedException e)
            {
                Toast.makeText(getApplicationContext(),"异常", Toast.LENGTH_LONG).show();
            }

            //停止录音
            System.out.println("停止录音啊啊啊啊");
            audioRecorder.stopRecord();

            player.stop();

            textView.post(new Runnable() {
                @Override
                public void run() {
                    textView.setText("end");
                }
            });
            try {
                player.prepare();
            }
            catch (IOException e)
            {
                System.out.println("player初始化失败！");
            }
        }
}
class myTaskStop extends TimerTask {
    @Override
    public void run(){
        System.out.println("停止录音啊啊啊啊");
        audioRecorder.stopRecord();
//                                start.setText("开始录音");
//                                pause.setText("暂停录音");
//                                pause.setVisibility(View.GONE);
        player.stop();
    }
}
//    /**
//     * 长连接上传数据
//     */
//    public void connect( ){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                new MinaThread().run();
//            }
//        }).start();
//    }
class myTaskWait extends TimerTask {
        @Override
    public void run(){
            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException e)
            {
                Toast.makeText(getApplicationContext(),"异常", Toast.LENGTH_LONG).show();
            }
        }
}
    @Override
    protected void onPause() {
        super.onPause();
        if (audioRecorder.getStatus() == AudioRecorder.Status.STATUS_START) {
            audioRecorder.pauseRecord();
            pause.setText("继续录音");
        }

    }

    @Override
    protected void onDestroy() {
        audioRecorder.release();
        super.onDestroy();

    }
}
