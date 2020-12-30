package com.example.activities;

import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class Controller extends AppCompatActivity {
    public String whoseTurn = "A";
    public Integer roundIterator = 0;
    public Map<String, Integer> teamSize = new HashMap<String, Integer>() {{
        put("A",1);
        put("B", 1);
    }};
    public Map<String, Double> score = new HashMap<String, Double>() {{
        put("A",0.0);
        put("B", 0.0);
    }};

    ImageView correctRef;
    ImageView wrongRef;
    ImageView startRef;
    TextView turnRef;
    TextView clueRef;
    TextView t1ScoreRef;
    TextView t2ScoreRef;
    TextView phaseDescRef;
    ImageView exitRef;


    public void displayRoundScreen() {
        correctRef.animate().alpha(0f).setDuration(500);
        wrongRef.animate().alpha(0f).setDuration(500);
        startRef.animate().alpha(1f).setDuration(500);
        phaseDescRef.animate().alpha(1f).setDuration(500);
    }

    public void displayGameScreen(View view) {
        startRef.animate().alpha(0f).setDuration(500);
        correctRef.animate().alpha(1f).setDuration(500);
        wrongRef.animate().alpha(1f).setDuration(500);
        phaseDescRef.animate().alpha(0f).setDuration(500);
    }

    public void setValuesRoundScreen() {
        roundIterator += 1;
        if (whoseTurn == "A") whoseTurn = "B";
        else whoseTurn = "A";

        turnRef.setText("Team " + whoseTurn);
        t1ScoreRef.setText("Team A: " + score.get("A"));
        t2ScoreRef.setText("Team B: " + score.get("B"));

        clueRef.setTypeface(null, Typeface.NORMAL);
        clueRef.setText("Round: " + Math.round(roundIterator / 2.0));

        Log.i("INFO", "Clue number " + roundIterator);
        Log.i("Info", "Round " + Math.round(roundIterator/2.0) + ", team " + whoseTurn + " turn.");
    }


    public void correctButtonPressed(View view) {
        score.put(whoseTurn, score.get(whoseTurn) + 1.0/teamSize.get(whoseTurn));
        Log.i("POINTS", "Team " + whoseTurn + " + " + 1.0/teamSize.get(whoseTurn) + "points.");
    }

    public void wrongButtonPressed(View view) {
//        if (score.get(whoseTurn) == 0) {
//            score.put(whoseTurn, 0);
//        } else {
//            score.put(whoseTurn, score.get(whoseTurn) - 1);
//        }
    }
}
