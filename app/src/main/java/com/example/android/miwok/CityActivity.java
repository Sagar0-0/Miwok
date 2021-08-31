package com.example.android.miwok;
import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.*;
import android.os.Bundle;

import java.util.*;

public class CityActivity extends AppCompatActivity {

    private MediaPlayer audio;
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener=
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                        audio.pause();
                        audio.seekTo(0);
                    }else if(focusChange==AudioManager.AUDIOFOCUS_GAIN){
                        audio.start();
                    }else if(focusChange==AudioManager.AUDIOFOCUS_LOSS){
                        releaseMediaPlayer();
                    }
                }
            };
    private MediaPlayer.OnCompletionListener mCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);


        mAudioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> w=new ArrayList<>();
        w.add(new Word("Kaithal","Publisher",R.drawable.sagar,R.raw.error));
        w.add(new Word("Hisar","Pawan, Ritika",R.drawable.pawan,R.raw.pawan));
        w.add(new Word("Ropar","Jannat",R.drawable.jnnt,R.raw.safed));
        w.add(new Word("Narwana","Arvind",R.drawable.arvind,R.raw.error));
        w.add(new Word("Ambikapur","Raghav",R.drawable.raghav,R.raw.raghav));
        w.add(new Word("Sonipat","Somil",R.drawable.somil,R.raw.somil));
        w.add(new Word("Hoshiarpur","Ansh",R.drawable.ansh,R.raw.ansh));
        w.add(new Word("Patiala","Harsh beta",R.drawable.harsh,R.raw.error));
        w.add(new Word("Dehradun","Mr.Faisu",R.drawable.faisu,R.raw.faisu));
        w.add(new Word("Panchkula","Ronit",R.drawable.ronit,R.raw.error));
        w.add(new Word("Varanasi","Avinash",R.drawable.avinash,R.raw.error));
        w.add(new Word("Indore","Aashutosh",R.drawable.ashu,R.raw.error));



        WordAdapter itemsAdapter = new WordAdapter(this, w,R.color.category_phrases);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Word word=w.get(position);

                releaseMediaPlayer();

                int result=mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    audio = MediaPlayer.create(CityActivity.this, word.getAudio());
                    audio.start();

                    audio.setOnCompletionListener(mCompletionListener);

                }

            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (audio != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            audio.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            audio = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}