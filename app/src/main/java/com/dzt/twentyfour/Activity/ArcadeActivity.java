package com.dzt.twentyfour.Activity;

import android.widget.Toast;

public class ArcadeActivity extends BaseActivity {
    @Override
    protected void initializeViews() {
        super.initializeViews();

        timerSeconds = 60;
        secondsPerCorrect = 60;
        mode = "arcade";
    }

    @Override
    protected void gameOver() {
        if (timerSeconds == 0) {
            Toast.makeText(this, "Time's up", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
        }
        vibrator.vibrate(400);

        t.cancel();
        gameOverAnimation();
    }

    @Override
    protected void skipCards() {
        if(possible) {
            gameOver();
        } else {
            correct();
        }
    }

    @Override
    protected void addTime() {
        timerSeconds = secondsPerCorrect;
    }

    @Override
    protected void showNextCards() {
        if(deck.size() == 0) {
            if (secondsPerCorrect >= 20) {
                secondsPerCorrect -= 5;
            }
            initializeDeck();
            shuffleCards();
            setCards();
        } else {
            setCards();
        }
    }

}
