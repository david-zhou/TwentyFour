package com.dzt.twentyfour.Activity;


public class RelaxActivity extends BaseActivity {

    @Override
    protected void initializeTimer() {
        //do nothing
    }

    @Override
    protected void initializeViews() {
        super.initializeViews();
        mode = "relax";
        timer.setText("--:--");
    }

    @Override
    protected void correct() {
        showNextCards();
        resetCards();
        addScore();
    }

    @Override
    protected void skipCards() {
        if(possible) {
            vibrator.vibrate(100);
            showNextCards();
            resetCards();
        } else {
            correct();
        }
    }

    @Override
    protected void showNextCards() {
        if(deck.size() == 0) {
            initializeDeck();
            shuffleCards();
        }
        setCards();
    }

    @Override
    protected void endGameExit() {
        finish();
    }
}