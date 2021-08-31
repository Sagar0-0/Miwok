package com.example.android.miwok;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

    }

    public void openNumbers(View view){
        Intent i=new Intent(this, NumbersActivity.class);
        startActivity(i);
    }
    public void openFamily(View view){
        Intent i=new Intent(this, FamilyActivity.class);
        startActivity(i);
    }
    public void openColors(View view){
        Intent i=new Intent(this, ColorsActivity.class);
        startActivity(i);
    }
    public void openCity(View view){
        Intent i=new Intent(this, CityActivity.class);
        startActivity(i);
    }
}