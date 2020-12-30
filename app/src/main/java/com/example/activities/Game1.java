package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import java.util.Collections;
import java.util.Map;
import java.util.Random;


public class Game1 extends AppCompatActivity {

    public ArrayList<Clue> gameClues = new ArrayList<Clue>();
    Controller controller;
    ListView cluesListRef;
    Boolean dataLoaded = false;

    public void main() {
        Log.i("GAME 1", "RoundIterator " + controller.roundIterator + " gameCLues size " + gameClues.size());
        if (controller.roundIterator < gameClues.size()) {
            displayRoundScreen();
        } else {
            setWrapUpScreen();
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    moveToNextActivity();
                }
            }, 1000);
        }
    }

    public void displayRoundScreen() {
//        backgroundSong.start();

        if (dataLoaded) {
            controller.turnRef.animate().alpha(1f).setDuration(500);
            controller.clueRef.animate().alpha(1f).setDuration(500);
            controller.t1ScoreRef.animate().alpha(1f).setDuration(500);
            controller.t2ScoreRef.animate().alpha(1f).setDuration(500);
            controller.exitRef.animate().alpha(1f).setDuration(500);
            controller.startRef.setImageResource(R.drawable.next_button);

            controller.phaseDescRef.setText("Get your teammates to guess the given word you are describing, but look out! \n There is a list of words you can't say.\n\n Have fun!");
            controller.displayRoundScreen();
            cluesListRef.animate().alpha(0f).setDuration(500);
            setValuesRoundScreen();
        } else {
            displayLoadingData();
        }
    }

    public void displayGameScreen(View view) {
//        backgroundSong.stop();

        controller.displayGameScreen(view);
        cluesListRef.animate().alpha(1f).setDuration(500);
        setValuesGameScreen();
    }

    public void setValuesRoundScreen() {
        controller.setValuesRoundScreen();
    }

    public void setValuesGameScreen() {
        Clue clue = gameClues.get(controller.roundIterator - 1);
        controller.clueRef.setText(clue.getName());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.row, clue.getArray());
        cluesListRef.setAdapter(adapter);
    }

    public void setWrapUpScreen() {
        cluesListRef.animate().alpha(0f).setDuration(500);
        controller.t1ScoreRef.setText("Team A: " + Math.round(controller.score.get("A") * 10) / 10.0);
        controller.t2ScoreRef.setText("Team B: " + Math.round(controller.score.get("B") * 10) / 10.0);
        controller.correctRef.animate().alpha(0f).setDuration(500);
        controller.wrongRef.animate().alpha(0f).setDuration(500);
        controller.clueRef.animate().alpha(0f).setDuration(500);
        controller.phaseDescRef.animate().alpha(1f).setDuration(500);
        controller.phaseDescRef.setText("First battle is already behind us! \n Next one starts in 1s... \n\n Don't give up!");
    }

    public void moveToNextActivity() {
        Intent intent = new Intent(Game1.this, Game2.class);
        intent.putExtra("gameClues", getCluesNames());
        intent.putExtra("gameScoreA", controller.score.get("A"));
        intent.putExtra("gameScoreB", controller.score.get("B"));
        intent.putExtra("teamASize", controller.teamSize.get("A"));
        intent.putExtra("teamBSize", controller.teamSize.get("B"));
        startActivity(intent);
    }

    public void correctButtonPressed(View view) {
        controller.correctButtonPressed(view);
        main();
    }

    public void wrongButtonPressed(View view) {
        controller.wrongButtonPressed(view);
        main();
    }

    public void exitGame(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public ArrayList<String> getCluesNames() {
        ArrayList<String> cluesList = new ArrayList<String>();

        for (Clue clue : gameClues) {
            cluesList.add(clue.getName());
        }
        Collections.shuffle(cluesList);
        return cluesList;
    }

    public void displayLoadingData() {
        controller.phaseDescRef.setText("Loading data from database...");
        controller.phaseDescRef.animate().alpha(1f).setDuration(500);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_game1);

//      Read all data from Firebase
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference dataRef = database.child("data/");

        controller = new Controller();
        cluesListRef = (ListView) findViewById(R.id.clue_list);

        controller.correctRef = (ImageView) findViewById(R.id.correct);
        controller.wrongRef = (ImageView) findViewById(R.id.wrong);
        controller.startRef = (ImageView) findViewById(R.id.start_game);
        controller.turnRef = (TextView) findViewById(R.id.whose_turn);
        controller.clueRef = (TextView) findViewById(R.id.clue);
        controller.t1ScoreRef = (TextView) findViewById(R.id.t1_score);
        controller.t2ScoreRef = (TextView) findViewById(R.id.t2_score);
        controller.phaseDescRef = (TextView) findViewById(R.id.phaseDesc);
        controller.exitRef = (ImageView) findViewById(R.id.exit);

        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapShot) {
                ArrayList<Clue> allClues = new ArrayList<Clue>();
                for (DataSnapshot data : snapShot.getChildren()) {
                    Map map = (Map) data.getValue();
                    Clue tempClue = new Clue(String.valueOf(map.get("name")), (ArrayList) map.get("forbidden"));
                    Log.i("DATABASE", tempClue.getName());
                    Log.i("DATABASE", "" + tempClue.getArray());
                    allClues.add(tempClue);
                    Log.i("DATABASE", "Data retrieved from database.");
                }

//                Select clues for game
                Intent intent = getIntent();
                Integer numberOfClues = intent.getIntExtra("numOfClues", 0);
                controller.teamSize.put("A", intent.getIntExtra("teamASize", 1));
                controller.teamSize.put("B", intent.getIntExtra("teamBSize", 1));

                Log.i("GAME 1", "Number of clues: " + numberOfClues);
                Log.i("GAME 1", "Team A size " + controller.teamSize.get("A"));
                Log.i("GAME 1", "Team B size " + controller.teamSize.get("B"));

                if (controller.teamSize.get(("A")) > controller.teamSize.get(("B"))) {
//                    In controller will be changed to A
                    controller.whoseTurn = "B";
                } else {
                    controller.whoseTurn = "A";
                }

                Random rand = new Random();

                do {
                    Clue tempClue =  allClues.get(rand.nextInt(allClues.size()));
                    if (gameClues.contains(tempClue)) continue;
                    else {
                        gameClues.add(tempClue);
                        Log.i("Game 1", "Selected clue: " + tempClue.getName());
                    }
                }while(gameClues.size() < numberOfClues);

                dataLoaded = true;
                displayRoundScreen();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Info", "onCancelled: Error: " + databaseError.getMessage());
            }
        });
        Log.i("GAME 1", "Game 1 started");
    }
}