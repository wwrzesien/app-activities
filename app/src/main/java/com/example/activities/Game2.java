package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Game2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GifApi.BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();

        GifApi api = retrofit.create(GifApi.class);
//        Call<Gif> call = api.getGif("snow", "1", "0", "g", "en");
        Call<Gif> call = api.getGif();
//        Log.d("url", );

        call.enqueue(new Callback<Gif>() {
            @Override
            public void onResponse(Call<Gif> call, Response<Gif> response) {
                Gif gif = response.body();
                Log.d("body", "" + gif.getUrl());
                Log.d("title", "" + gif.getTitle());
//                Log.d("embed_url", "" + gif.getEmbed_url());
            }

            @Override
            public void onFailure(Call<Gif> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}