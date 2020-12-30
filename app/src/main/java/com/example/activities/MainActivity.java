package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

//    Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>
//    Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>

    int[] teamsSize = {1, 1};
    int numberOfRounds = 1;

    public void teamAdd(View view) {
        if (view.getId() == R.id.team_one_plus) {
            TextView team_size = (TextView) findViewById(R.id.team_one_size);
            teamsSize[0] += 1;
            team_size.setText(String.valueOf(teamsSize[0]));
            Log.i("DEBUG", "Added player to team 1. Current number:" + teamsSize[0]);
        } else {
            TextView team_size = (TextView) findViewById(R.id.team_two_size);
            teamsSize[1] += 1;
            team_size.setText(String.valueOf(teamsSize[1]));
            Log.i("DEBUG", "Added player to team 2. Current number:" + teamsSize[1]);
        }
    }

    public void teamSub(View view) {
        if (view.getId() == R.id.team_one_minus) {
            TextView team_size = (TextView) findViewById(R.id.team_one_size);
            if (teamsSize[0] > 0) {
                teamsSize[0] -= 1;
                Log.i("DEBUG", "Subtracted player from team 1. Current number:" + teamsSize[0]);
            }
            team_size.setText(String.valueOf(teamsSize[0]));
        } else {
            TextView team_size = (TextView) findViewById(R.id.team_two_size);
            if (teamsSize[1] > 0) {
                teamsSize[1] -= 1;
                Log.i("DEBUG", "Subtracted player from team 2. Current number:" + teamsSize[1]);
            }
            team_size.setText(String.valueOf(teamsSize[1]));
        }
    }

    public void roundAdd(View view) {
        TextView round_size = (TextView) findViewById(R.id.round);
        numberOfRounds += 1;
        round_size.setText(String.valueOf(numberOfRounds));
        Log.i("DEBUG", "Increased number of  clues. Current number:" + numberOfRounds);
    }

    public void roundSub(View view) {
        TextView round_size = (TextView) findViewById(R.id.round);
        if (numberOfRounds > 1) {
            numberOfRounds -= 1;
            Log.i("DEBUG", "decreased number of  clues. Current number:" + numberOfRounds);
        }
        round_size.setText(String.valueOf(numberOfRounds));
    }

    public void moveToNextActivity(View view) {
        Intent intent = new Intent(this, Game1.class);
        intent.putExtra("numOfClues", (teamsSize[0] + teamsSize[1]) * this.numberOfRounds);
        intent.putExtra("teamASize", teamsSize[0]);
        intent.putExtra("teamBSize", teamsSize[1]);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView star = (ImageView) findViewById(R.id.open_star);
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.settings);
            }
        });
    }
}