package com.e.learnhindi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    private ArrayList<Word> list;
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener afChange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        /**
         * To show the title of the activity
         */
        Intent intent = getIntent();
        String st = intent.getExtras().getString("title1");
        this.setTitle(st);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /**
         * Preoaring list to show in NumbersActivity
         */
//        String[] numbersList=new String[10];
//        numbersList[0]="One";
//        numbersList[1]="Two";
//        numbersList[2]="Three";
//        numbersList[3]="Four";
//        numbersList[4]="Five";
//        numbersList[5]="Six";
//        numbersList[6]="Seven";
//        numbersList[7]="Eight";
//        numbersList[8]="Nine";
//        numbersList[9]="Ten";

        audioManager=(AudioManager) getSystemService(Context.AUDIO_SERVICE);

        list = new ArrayList<>();
        list.add(new Word("Ek", "One", R.drawable.number_one, R.raw.numone));
        list.add(new Word("Do", "Two", R.drawable.number_two, R.raw.numtwo));
        list.add(new Word("Teen", "Three", R.drawable.number_three, R.raw.numthree));
        list.add(new Word("Chaar", "Four", R.drawable.number_four, R.raw.numfour));
        list.add(new Word("Paanch", "Five", R.drawable.number_five, R.raw.numfive));
        list.add(new Word("Chhah", "Six", R.drawable.number_six, R.raw.numsix));
        list.add(new Word("Saat", "Seven", R.drawable.number_seven, R.raw.numseven));
        list.add(new Word("Aanth", "Eight", R.drawable.number_eight, R.raw.numeight));
        list.add(new Word("No", "Nine", R.drawable.number_nine, R.raw.numnine));
        list.add(new Word("Das", "Ten", R.drawable.number_ten, R.raw.numten));

        WordAdapter adapter = new WordAdapter(this, list);
        ListView view = findViewById(R.id.list);
        view.setAdapter(adapter);

        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(NumbersActivity.this, "Item Clicked", Toast.LENGTH_SHORT).show();
                Word word = list.get(i);
                int music = word.getmAudio();
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
                    mediaPlayer = MediaPlayer.create(NumbersActivity.this, music);
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
