package com.dzt.twentyfour.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.dzt.twentyfour.Class.Score;
import com.dzt.twentyfour.Adapter.ScoreboardListAdapter;
import com.dzt.twentyfour.R;

import java.util.ArrayList;

public class ScoresActivity extends Activity implements View.OnClickListener{

    String mode;
    int score;
    Button tryAgain, menu;
    ArrayList<Score> scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        assignViews();
        setScore();
        assignHighScore();
        createScoresArrayList();
        createAdapter();
    }

    private void assignViews() {
        tryAgain = (Button) findViewById(R.id.scoreboard_try_again);
        tryAgain.setOnClickListener(this);

        menu = (Button) findViewById(R.id.scoreboard_menu);
        menu.setOnClickListener(this);

        scores = new ArrayList<>();
    }

    private void createAdapter() {
        ScoreboardListAdapter adapter = new ScoreboardListAdapter(this, scores);
        ListView listView = (ListView) findViewById(R.id.scores_listview);
        listView.setAdapter(adapter);
    }

    private void setScore() {
        mode = getIntent().getExtras().getString("mode");
        if(mode.equals("none")) {
            hideTryAgainButton();
        }
        score = getIntent().getExtras().getInt("score");
    }

    private void assignHighScore() {
        SharedPreferences sp = getSharedPreferences("Scores", MODE_PRIVATE);
        int thisModeHighScore = sp.getInt(mode,0);
        if (score > thisModeHighScore) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt(mode, score);
            editor.apply();
            showMessage("You got a new High Score!");
        } else if (mode.equals("none")) {
            showMessage("");
        } else {
            showMessage("Your score is " + score);
        }

    }

    private void createScoresArrayList() {

        SharedPreferences sp = getSharedPreferences("Scores", MODE_PRIVATE);
        int scoreSurvival = sp.getInt("survival", 0);
        int scoreArcade = sp.getInt("arcade", 0);
        int scoreRandom = sp.getInt("random",0);
        int scoreNormal = sp.getInt("normal",0);
        Score survival = new Score("survival",scoreSurvival);
        Score arcade = new Score("arcade",scoreArcade);
        Score random = new Score("random",scoreRandom);
        Score normal = new Score("normal",scoreNormal);

        this.scores.add(survival);
        this.scores.add(arcade);
        this.scores.add(random);
        this.scores.add(normal);
    }

    private void showMessage(String msg) {
        TextView message = (TextView) findViewById(R.id.scoreboard_message);
        message.setText(msg);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.scoreboard_try_again:
                tryAgainOpenActivity();
                break;
            case R.id.scoreboard_menu:
                mode = "menu";
                tryAgainOpenActivity();
                break;

        }
    }

    private void tryAgainOpenActivity() {
        Intent intent;
        switch (mode) {
            case "survival":
                intent = new Intent(this, SurvivalActivity.class);
                break;
            case "arcade":
                intent = new Intent(this, ArcadeActivity.class);
                break;
            case "random":
                intent = new Intent(this, RandomActivity.class);
                break;
            case "normal":
                intent = new Intent(this, NormalActivity.class);
                break;
            default:
                intent = new Intent(this, MenuActivity.class);
                break;
        }
        startActivity(intent);
        finish();
    }

    private void hideTryAgainButton() {
        tryAgain.setVisibility(View.INVISIBLE);
    }
}
