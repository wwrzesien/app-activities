package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game1 extends AppCompatActivity {

    public static Map<String, List<String>> clues = new HashMap<String, List<String>>();
    public static String whoseTurn = "Unknown";
    public static Integer roundIterator;
    public static Integer team1Score = 0;
    public static Integer team2Score = 0;

    public void displayClueScreen(View view) {
        ImageView correctRef = (ImageView) findViewById(R.id.correct);
        ImageView wrongRef = (ImageView) findViewById(R.id.wrong);
        ImageView startRef = (ImageView) findViewById(R.id.start_game1);
        ListView cluesListRef = (ListView) findViewById(R.id.clue_list);

        startRef.animate().alpha(0f).setDuration(500);
        cluesListRef.animate().alpha(1f).setDuration(500);
        correctRef.animate().alpha(1f).setDuration(500);
        wrongRef.animate().alpha(1f).setDuration(500);

        Log.i("INFO", "Game1 started");



//        TextView clueRef = (TextView) findViewById(R.id.clue);
//        clueRef.setText(clues.get)

    }

    public void setDefaultValuesOnGameScreen() {
        TextView turnRef = (TextView) findViewById(R.id.whose_turn);
        turnRef.setText(whoseTurn);

        TextView t1ScoreRef = (TextView) findViewById(R.id.t1_score);
        t1ScoreRef.setText("Team A: " + team1Score);

        TextView t2ScoreRef = (TextView) findViewById(R.id.t2_score);
        t2ScoreRef.setText("Team B: " + team2Score);
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
                for (DataSnapshot data : snapShot.getChildren()) {
                    Map map = (Map) data.getValue();
                    clues.put(String.valueOf(map.get("name")), (List) map.get("forbidden"));
                    Log.i("Info", "" + clues);
                    Log.i("Info", "Data retrieved from database.");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Info", "onCancelled: Error: " + databaseError.getMessage());
            }
        });

        setDefaultValuesOnGameScreen();

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