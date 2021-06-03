package com.e.learnhindi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {
    private ArrayList<Word> list;
    MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener afChange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);
        Intent intent=getIntent();
        String st=intent.getExtras().getString("title4");
        this.setTitle(st);

        audioManager=(AudioManager) getSystemService(Context.AUDIO_SERVICE);

        list=new ArrayList<>();
        list.add(new Word("Kaala","Black",R.drawable.color_black,R.raw.colorblack));
        list.add(new Word("Bhoora","Brown",R.drawable.color_brown,R.raw.colorbrown));
        list.add(new Word("Jhaankha Peela","Dusty Yellow",R.drawable.color_dusty_yellow,R.raw.colordustyyellow));
        list.add(new Word("Dhoosar","Gray",R.drawable.color_gray,R.raw.colorgray));
        list.add(new Word("Hara","Green",R.drawable.color_green,R.raw.colorgreen));
        list.add(new Word("Sarsanvi Peela","Mustard Yellow",R.drawable.color_mustard_yellow,R.raw.colormustardyellow));
        list.add(new Word("Laal","Red",R.drawable.color_red,R.raw.colorred));
        list.add(new Word("Safed","White",R.drawable.color_white,R.raw.colorwhite));


        WordAdapter adapter=new WordAdapter(this,list);
        ListView view=findViewById(R.id.Collist);
        view.setAdapter(adapter);

        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(NumbersActivity.this, "Item Clicked", Toast.LENGTH_SHORT).show();
                Word word= list.get(i);
                int music=word.getmAudio();
                releaseMedia();
                afChange=new AudioManager.OnAudioFocusChangeListener() {
                    @Override
                    public void onAudioFocusChange(int i) {
                        if(i==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT||i==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                            mediaPlayer.pause();
                            mediaPlayer.seekTo(0);
                        }
                        else if(i==AudioManager.AUDIOFOCUS_GAIN){
                            mediaPlayer.start();

                        }
                        else if(i==AudioManager.AUDIOFOCUS_LOSS){
                            releaseMedia();
                        }
                    }
                };

                //Requesting AudioFocus To get the focus of mediaplayerand play sound braeak free

                int result=audioManager.requestAudioFocus(afChange, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(ColorsActivity.this, music);
                    mediaPlayer.start();


                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            releaseMedia();
                        }
                    });
                }
            }
        });

    }

    private void releaseMedia(){
      if(mediaPlayer!=null) mediaPlayer.release();
        mediaPlayer=null;
        audioManager.abandonAudioFocus(afChange);
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMedia();
    }
}
