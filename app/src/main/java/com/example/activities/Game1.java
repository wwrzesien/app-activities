package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class Game1 extends AppCompatActivity {

    public ArrayList<Clue> gameClues = new ArrayList<Clue>();
    public String whoseTurn = "A";
    public Integer roundIterator = 1;
    public Map<String, Integer> score = new HashMap<String, Integer>() {{
        put("A",0);
        put("B", 0);
    }};

    public void displayRoundScreen() {
        ImageView correctRef = (ImageView) findViewById(R.id.correct);
        ImageView wrongRef = (ImageView) findViewById(R.id.wrong);
        ImageView startRef = (ImageView) findViewById(R.id.start_game1);
        ListView cluesListRef = (ListView) findViewById(R.id.clue_list);

        cluesListRef.animate().alpha(0f).setDuration(500);
        correctRef.animate().alpha(0f).setDuration(500);
        wrongRef.animate().alpha(0f).setDuration(500);
        startRef.animate().alpha(1f).setDuration(500);

        setValuesRoundScreen();
    }

    public void displayGameScreen(View view) {
        ImageView correctRef = (ImageView) findViewById(R.id.correct);
        ImageView wrongRef = (ImageView) findViewById(R.id.wrong);
        ImageView startRef = (ImageView) findViewById(R.id.start_game1);
        ListView cluesListRef = (ListView) findViewById(R.id.clue_list);

        startRef.animate().alpha(0f).setDuration(500);
        cluesListRef.animate().alpha(1f).setDuration(500);
        correctRef.animate().alpha(1f).setDuration(500);
        wrongRef.animate().alpha(1f).setDuration(500);

        setValuesGameScreen();
    }

    public void setValuesRoundScreen() {
        roundIterator += 1;
        if (whoseTurn == "A") whoseTurn = "B";
        else whoseTurn = "A";

        TextView turnRef = (TextView) findViewById(R.id.whose_turn);
        turnRef.setText("Team " + whoseTurn);

        TextView t1ScoreRef = (TextView) findViewById(R.id.t1_score);
        t1ScoreRef.setText("Team A: " + score.get("A"));

        TextView t2ScoreRef = (TextView) findViewById(R.id.t2_score);
        t2ScoreRef.setText("Team B: " + score.get("B"));

        TextView clueRef = (TextView) findViewById(R.id.clue);
        clueRef.getLayoutParams().height = 180 * 3;
        clueRef.setText("Round: " + roundIterator / 2 );

        Log.i("Info", "Round " + roundIterator/2 + ", team " + whoseTurn + " turn.");
    }

    public void setValuesGameScreen() {
        Clue clue = gameClues.get(roundIterator - 1);

        TextView clueRef = (TextView) findViewById(R.id.clue);
        clueRef.getLayoutParams().height = 120 * 3;
        clueRef.setText(clue.getName());

        ListView clueListRef = (ListView) findViewById(R.id.clue_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.row, clue.getArray());
        clueListRef.setAdapter(adapter);
    }

    public void setWrapUpScreen() {
        TextView clueRef = (TextView) findViewById(R.id.clue);
        clueRef.getLayoutParams().height = 200 * 3;
        clueRef.setText("Game 1 finsihed.");
    }

    public void correctButtonPressed(View view) {
        score.put(whoseTurn, score.get(whoseTurn) + 1);
        displayRoundScreen();
    }

    public void wrongButtonPressed(View view) {
        if (score.get(whoseTurn) == 0) {
            score.put(whoseTurn, 0);
        } else {
            score.put(whoseTurn, score.get(whoseTurn) - 1);
        }
        displayRoundScreen();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_game1);

//      Read all data from Firebase
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference dataRef = database.child("data/");


        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapShot) {
                ArrayList<Clue> allClues = new ArrayList<Clue>();
                for (DataSnapshot data : snapShot.getChildren()) {
                    Map map = (Map) data.getValue();
                    Clue tempClue = new Clue(String.valueOf(map.get("name")), (ArrayList) map.get("forbidden"));
                    Log.i("Info", tempClue.getName());
                    Log.i("Info", "" + tempClue.getArray());
                    allClues.add(tempClue);
                    Log.i("Info", "Data retrieved from database.");
                }

//                Select clues for game
                Integer numberOfClues = 3; // Value will be passed from previous activity
                Random rand = new Random();

                do {
                    Clue tempClue =  allClues.get(rand.nextInt(allClues.size()));
                    if (gameClues.contains(tempClue)) continue;
                    else gameClues.add(tempClue);
                }while(gameClues.size() < numberOfClues);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Info", "onCancelled: Error: " + databaseError.getMessage());
            }
        });

        setValuesRoundScreen();
        Log.i("INFO", "Game1 started");

//        ImageView startGame1 = (ImageView) findViewById(R.id.start_game1);
//        startGame1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setContentView(R.layout.display_game1);
//                setValuesOnGameScreen(v);
//                Log.i("Info", "Moved to screen with clues.");
//            }
//        });
    }
}