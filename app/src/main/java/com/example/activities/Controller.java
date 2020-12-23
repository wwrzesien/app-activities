package com.example.activities;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Controller {
    public ArrayList<Clue> gameClues = new ArrayList<Clue>();
    public String whoseTurn = "A";
    public Integer roundIterator = 1;
    public Map<String, Integer> score = new HashMap<String, Integer>() {{
        put("A",0);
        put("B", 0);
    }};

    ImageView correctRef;
    ImageView wrongRef;
    ImageView startRef;
    TextView turnRef;
    TextView clueRef;
    TextView t1ScoreRef;
    TextView t2ScoreRef;


    public void displayRoundScreen() {
        correctRef.animate().alpha(0f).setDuration(500);
        wrongRef.animate().alpha(0f).setDuration(500);
        startRef.animate().alpha(1f).setDuration(500);
    }

    public void displayGameScreen(View view) {
        startRef.animate().alpha(0f).setDuration(500);
        correctRef.animate().alpha(1f).setDuration(500);
        wrongRef.animate().alpha(1f).setDuration(500);
    }

    public void setValuesRoundScreen() {
        roundIterator += 1;
        if (whoseTurn == "A") whoseTurn = "B";
        else whoseTurn = "A";

        turnRef.setText("Team " + whoseTurn);
        t1ScoreRef.setText("Team A: " + score.get("A"));
        t2ScoreRef.setText("Team B: " + score.get("B"));
        clueRef.getLayoutParams().height = 180 * 3;
        clueRef.setText("Round: " + roundIterator / 2 );

        Log.i("Info", "Round " + roundIterator/2 + ", team " + whoseTurn + " turn.");
    }

    public void setValuesGameScreen() {
        Clue clue = gameClues.get(roundIterator - 1);
        clueRef.getLayoutParams().height = 120 * 3;
        clueRef.setText(clue.getName());
    }

    public void correctButtonPressed(View view) {
        score.put(whoseTurn, score.get(whoseTurn) + 1);
    }

    public void wrongButtonPressed(View view) {
        if (score.get(whoseTurn) == 0) {
            score.put(whoseTurn, 0);
        } else {
            score.put(whoseTurn, score.get(whoseTurn) - 1);
        }
    }
}
