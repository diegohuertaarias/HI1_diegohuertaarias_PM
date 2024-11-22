package com.empresa.App;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private int score = 0;
    private TextView textScore;
    private TextView textRanking;
    private TextView textTimer;
    private Button buttonClick;
    private Button buttonReset;
    private SharedPreferences sharedPreferences;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textScore = findViewById(R.id.text_score);
        textRanking = findViewById(R.id.text_ranking);
        textTimer = findViewById(R.id.text_timer);
        buttonClick = findViewById(R.id.button_click);
        buttonReset = findViewById(R.id.button_reset);

        sharedPreferences = getSharedPreferences("ranking", MODE_PRIVATE);
        updateRanking();

        buttonClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score++;
                textScore.setText("Score: " + score);
                updateRanking();
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        startTimer();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textTimer.setText("Time: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                buttonClick.setEnabled(false);
                textTimer.setText("Time's up!");
            }
        }.start();
    }

    private void resetGame() {
        score = 0;
        textScore.setText("Score: " + score);
        buttonClick.setEnabled(true);
        countDownTimer.cancel();
        startTimer();
    }

    private void updateRanking() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int highScore = sharedPreferences.getInt("high_score", 0);

        if (score > highScore) {
            highScore = score;
            editor.putInt("high_score", highScore);
            editor.apply();
        }

        textRanking.setText("High Score: " + highScore);
    }
}