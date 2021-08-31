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

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

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
        w.add(new Word("One","Ek",R.drawable.number_one,R.raw.ek));
        w.add(new Word("Two","Do",R.drawable.number_two,R.raw.two));
        w.add(new Word("Three","Teen",R.drawable.number_three,R.raw.tin));
        w.add(new Word("Four","Chaar",R.drawable.number_four,R.raw.chaar));
        w.add(new Word("Five","Paanch",R.drawable.number_five,R.raw.panch));
        w.add(new Word("Six","Ch'hee",R.drawable.number_six,R.raw.shee));
        w.add(new Word("Seven","Sa'at",R.drawable.number_seven,R.raw.saat));
        w.add(new Word("Eight","A'ath",R.drawable.number_eight,R.raw.aanth));
        w.add(new Word("Nine","No'o",R.drawable.number_nine,R.raw.noo));
        w.add(new Word("Ten","Da'as",R.drawable.number_ten,R.raw.duss));





        WordAdapter itemsAdapter = new WordAdapter(this, w,R.color.category_numbers);
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
                    audio = MediaPlayer.create(NumbersActivity.this, word.getAudio());
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
