package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Game2 extends AppCompatActivity {

    ArrayList<String> gameCluesNames = new ArrayList<String>();
    Controller controller;
    WebView webView;
    Boolean hideText = true;

//    public ArrayList<Clue> gameClues = new ArrayList<Clue>();
//    public String whoseTurn = "A";
//    public Integer roundIterator = 1;
//    public Map<String, Integer> score = new HashMap<String, Integer>() {{
//        put("A",0);
//        put("B", 0);
//    }};

    public void main() {
        if (controller.roundIterator < gameCluesNames.size()) {
            displayRoundScreen();
        } else {
            setWrapUpScreen();
//            (new Handler()).postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    moveToNextActivity();
//                }
//            }, 1000);
        }
    }

    public void displayRoundScreen() {
        controller.displayRoundScreen();
        webView.animate().alpha(0f).setDuration(500);
        setValuesRoundScreen();
    }

    public void displayGameScreen(View view) {
        controller.displayGameScreen(view);
        webView.animate().alpha(1f).setDuration(500);
        setValuesGameScreen();
    }

    public void setValuesRoundScreen() {
        controller.setValuesRoundScreen();
    }

    public void setValuesGameScreen() {
        String clueName = gameCluesNames.get(controller.roundIterator - 1);
        controller.clueRef.getLayoutParams().height = 120 * 3;
        controller.clueRef.setText("Show clue");
//        controller.setValuesGameScreen();
        displayGif(clueName);
    }

    public void setWrapUpScreen() {
        controller.t1ScoreRef.setText("Team A: " + controller.score.get("A"));
        controller.t2ScoreRef.setText("Team B: " + controller.score.get("B"));
        controller.correctRef.animate().alpha(0f).setDuration(500);
        controller.wrongRef.animate().alpha(0f).setDuration(500);
        controller.clueRef.getLayoutParams().height = 200 * 3;
        controller.clueRef.setText("Game finsihed.");
    }

//    public void moveToNextActivity() {
//        Intent intent = new Intent(this, Game2.class);
//        intent.putExtra("gameClues", controller.gameClues);
//        intent.putExtra("gameScoreA", controller.score.get("A"));
//        intent.putExtra("gameScoreB", controller.score.get("B"));
//        startActivity(intent);
//    }

    public void correctButtonPressed(View view) {
        controller.correctButtonPressed(view);
        main();
    }

    public void wrongButtonPressed(View view) {
        controller.wrongButtonPressed(view);
        main();
    }

    public void displayGif(String clue) {
//        Hide start button
//        ImageView startRef = (ImageView) findViewById(R.id.start_game);
//        controller.startRef.animate().alpha(0f).setDuration(500);

//        Display screen
//        webView.animate().alpha(1f).setDuration(500);

//        Retrieve gif url
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GifApi.BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();

        GifApi api = retrofit.create(GifApi.class);
        Call<Gif> call = api.getGif(clue, "1", "0", "g", "en");
        Log.i("PHASE 2", "API for clue: " + clue);

        call.enqueue(new Callback<Gif>() {
            @Override
            public void onResponse(Call<Gif> call, Response<Gif> response) {
                Gif gif = response.body();
                ArrayList<Data> data = gif.getData();
                String gifUrl = data.get(0).getEmbed_url();
                Log.d("Gif url", "" + gifUrl);

                //        Display gif
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webView.loadUrl(gifUrl);
            }

            @Override
            public void onFailure(Call<Gif> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showText(View view) {
        if (hideText) {
            String clueName = gameCluesNames.get(controller.roundIterator - 1);
            controller.clueRef.setText(clueName);
        } else {
            controller.clueRef.setText("Show clue");
        }
        hideText = !hideText;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);

        controller = new Controller();
        webView = (WebView) findViewById(R.id.gif_screen);

        controller.correctRef = (ImageView) findViewById(R.id.correct);
        controller.wrongRef = (ImageView) findViewById(R.id.wrong);
        controller.startRef = (ImageView) findViewById(R.id.start_game);
        controller.turnRef = (TextView) findViewById(R.id.whose_turn);
        controller.clueRef = (TextView) findViewById(R.id.clue);
        controller.t1ScoreRef = (TextView) findViewById(R.id.t1_score);
        controller.t2ScoreRef = (TextView) findViewById(R.id.t2_score);

//        Set background color for gif screen
        webView.setBackgroundColor(Color.parseColor("#104C7A"));

//        Get data from previous activity
        Intent intent = getIntent();
        gameCluesNames = (ArrayList<String>) intent.getStringArrayListExtra("gameClues");
        Log.i("INFO", "Game  2 clues: " + gameCluesNames);
        controller.score.put("A", intent.getIntExtra("gameScoreA", 0));
        controller.score.put("B", intent.getIntExtra("gameScoreB", 0));

        setValuesRoundScreen();
        Log.i("INFO", "Game 2 started");

    }
}