package com.link.tictactoe;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_round);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    int aboutGameClicks = 0;
    public void aboutGameBtnClicked(View view){
        TextView aboutGame = findViewById(R.id.aboutGame);
        if(aboutGameClicks%2 == 0) {
            aboutGame.setText(R.string.aboutGame);
        } else {
            aboutGame.setText("");
        }
        aboutGameClicks++;

    }

    public void playGameBtnClicked (View v){
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

}