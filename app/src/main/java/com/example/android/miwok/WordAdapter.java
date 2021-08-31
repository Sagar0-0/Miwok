package com.example.android.miwok;

import android.app.Activity;
import android.media.MediaPlayer;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    private int mColorId;

    public WordAdapter(Activity context, ArrayList<Word> words,int color){
        super(context,0,words);
        mColorId=color;
    }




    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Word currentWord=getItem(position);

        View listItemView= convertView;
        if(listItemView==null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);

        }

        TextView miwokTextView=(TextView) listItemView.findViewById(R.id.miwok_text_view);
        miwokTextView.setText(currentWord.getMiwokTranslation());

        TextView defTextView=(TextView) listItemView.findViewById(R.id.default_text_view);
        defTextView.setText(currentWord.getDefaultTranslation());


        ImageView img=(ImageView) listItemView.findViewById(R.id.image_view);
//        if(currentWord.hasImage()){
            img.setImageResource(currentWord.getImage());
//        }else{
//            img.setVisibility(View.GONE);
//        }



        View textContainer=listItemView.findViewById(R.id.text_container);
        int color= ContextCompat.getColor(getContext(),mColorId);
        textContainer.setBackgroundColor(color);



        return listItemView;
    }
}
