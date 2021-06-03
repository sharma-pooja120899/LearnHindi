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

public class PhrasesActivity extends AppCompatActivity {
    private ArrayList<Word> list;
    MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener afChange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases);
        Intent intent=getIntent();
        String st=intent.getExtras().getString("title3");
        this.setTitle(st);

        audioManager=(AudioManager) getSystemService(Context.AUDIO_SERVICE);

        list=new ArrayList<>();

        list.add(new Word(" na-ma-sté","Hello",R.raw.phrasehello));
        list.add(new Word(" Shubh-pra-bhaat","Good morning",R.raw.phrasegoodmor));
        list.add(new Word("shubh-raa-tree","Good night",R.raw.phrasegoodni));
        list.add(new Word("phir milengé","See you",R.raw.phrasesee));
        list.add(new Word("aaglee baar tak","Til next time",R.raw.phrasetill));
        list.add(new Word("aap kaisé hai","How are you? ",R.raw.phrasehow));
        list.add(new Word(" theek-theek","Not bad ",R.raw.phrasenot));
        list.add(new Word(" mai theek nahee hoo)","I’m not well",R.raw.phraseim));


        WordAdapter adapter=new WordAdapter(this,list);
        ListView view=findViewById(R.id.Phlist);
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
                    mediaPlayer = MediaPlayer.create(PhrasesActivity.this, music);
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
