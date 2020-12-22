package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
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

    public ArrayList<Clue> gameClues = new ArrayList<Clue>();
    public String whoseTurn = "A";
    public Integer roundIterator = 1;
    public Map<String, Integer> score = new HashMap<String, Integer>() {{
        put("A",0);
        put("B", 0);
    }};

    WebView webView;


    public void displayGif(View view) {
//        Hide start button
        ImageView startRef = (ImageView) findViewById(R.id.start_game2);
        startRef.animate().alpha(0f).setDuration(500);

//        Display screen
        webView.animate().alpha(1f).setDuration(500);

//        Retrieve gif url
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GifApi.BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();

        GifApi api = retrofit.create(GifApi.class);
        Call<Gif> call = api.getGif("tree", "1", "0", "g", "en");
//        Call<Gif> call = api.getGif();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);

//        Set background color for gif screen
        webView = (WebView) findViewById(R.id.gif_screen);
        webView.setBackgroundColor(Color.parseColor("#104C7A"));

//        Get data from previous activity
        Intent intent = getIntent();
        gameClues = (ArrayList<Clue>) intent.getParcelableArrayListExtra("gameClues");

    }
}