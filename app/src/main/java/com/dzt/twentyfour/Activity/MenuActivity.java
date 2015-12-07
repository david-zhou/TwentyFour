package com.dzt.twentyfour.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dzt.twentyfour.Class.TwentyFour;
import com.dzt.twentyfour.R;

public class MenuActivity extends Activity implements View.OnClickListener{

    private Button buttonSurvival;
    private Button buttonArcade;
    private Button buttonRelax;
    private Button buttonRandom;
    private Button buttonNormal;
    private Button buttonScoreboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);

        initializeViews();
    }

    private void initializeViews() {
        buttonScoreboard = (Button) findViewById(R.id.menu_button_scoreboard);
        buttonScoreboard.setOnClickListener(this);

        buttonSurvival = (Button) findViewById(R.id.menu_button_survival);
        buttonSurvival.setOnClickListener(this);

        buttonArcade = (Button) findViewById(R.id.menu_button_arcade);
        buttonArcade.setOnClickListener(this);

        buttonRelax = (Button) findViewById(R.id.menu_button_relax);
        buttonRelax.setOnClickListener(this);

        buttonRandom = (Button) findViewById(R.id.menu_button_random);
        buttonRandom.setOnClickListener(this);

        buttonNormal = (Button) findViewById(R.id.menu_button_normal);
        buttonNormal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.menu_button_scoreboard:
                openScoreboardActivity();
                break;
            case R.id.menu_button_survival:
                openSurvivalActivity();
                break;
            case R.id.menu_button_arcade:
                openArcadeActivity();
                break;
            case R.id.menu_button_relax:
                openRelaxActivity();
                break;
            case R.id.menu_button_random:
                openRandomActivity();
                break;
            case R.id.menu_button_normal:
                openNormalActivity();
                break;
            default:
                break;
        }
    }

    private void openSurvivalActivity(){
        startActivity(new Intent(this, SurvivalActivity.class));
    }

    private void openArcadeActivity() {
        startActivity(new Intent(this, ArcadeActivity.class));
    }

    private void openRandomActivity() {
        startActivity(new Intent(this, RandomActivity.class));
    }

    private void openRelaxActivity() {
        startActivity(new Intent(this, RelaxActivity.class));
    }

    private void openNormalActivity() {
        startActivity(new Intent(this, NormalActivity.class));
    }

    private void openScoreboardActivity() {
        int [] numbers = {1,5,5,9};
        boolean possible = TwentyFour.isPossible(numbers, 24.0);
        Toast.makeText(this, "1,5,5,9 " + possible, Toast.LENGTH_SHORT).show();
        /*
        Bundle params = new Bundle();
        params.putString("mode", "none");
        params.putInt("score", 0);

        Intent intent  = new Intent(this, ScoresActivity.class);
        intent.putExtras(params);
        startActivity(intent);
        */
    }
}
