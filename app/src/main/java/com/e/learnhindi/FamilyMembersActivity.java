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

public class FamilyMembersActivity extends AppCompatActivity {
    private ArrayList<Word> list;
    MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener afChange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_members);
        Intent intent=getIntent();
        String st=intent.getExtras().getString("title2");
        this.setTitle(st);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        audioManager=(AudioManager) getSystemService(Context.AUDIO_SERVICE);

       list =new ArrayList<>();
        list.add(new Word("Pitaji","Father",R.drawable.family_father,R.raw.famfather));
        list.add(new Word("Mataji","Mother",R.drawable.family_mother,R.raw.fammother));
        list.add(new Word("Bete","Son",R.drawable.family_son,R.raw.famson));
        list.add(new Word("Beti","Daughter",R.drawable.family_daughter,R.raw.famdaughter));
        list.add(new Word("Dadi","GrandMother",R.drawable.family_grandmother,R.raw.famgrandmother));
        list.add(new Word("Dada","GrandFather",R.drawable.family_grandfather,R.raw.famgrandfather));
        list.add(new Word("Didi","Older Sister",R.drawable.family_older_sister,R.raw.famoldsister));
        list.add(new Word("Bade Bhaiya","Older Brother",R.drawable.family_older_brother,R.raw.famoldbrother));
        list.add(new Word("Chhota Bhai","Younger Brother",R.drawable.family_younger_brother,R.raw.famyoungbro));
        list.add(new Word("Chhoti Behen","Younger Sister",R.drawable.family_younger_sister,R.raw.famyoungsis));

        WordAdapter adapter=new WordAdapter(this,list);
        ListView view=findViewById(R.id.Famlist);
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
                    mediaPlayer = MediaPlayer.create(FamilyMembersActivity.this, music);
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
