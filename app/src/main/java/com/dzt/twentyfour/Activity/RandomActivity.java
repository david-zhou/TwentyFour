package com.dzt.twentyfour.Activity;

import android.widget.Toast;

import com.dzt.twentyfour.Class.TwentyFour;

public class RandomActivity extends BaseActivity {
    int randomNumber;

    @Override
    protected void initializeTimer() {
        //do nothing
    }

    @Override
    protected void initializeViews() {
        super.initializeViews();
        mode = "random";
        timer.setText("--:--");
    }

    @Override
    protected void gameOver() {
        Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
        vibrator.vibrate(400);
        gameOverAnimation();
    }

    @Override
    protected void checkPossible() {
        int [] numbers = new int [4];
        for(int i = 0; i < 4; i++){
            numbers[i] = currentCards[i].getNumber();
        }

        randomNumber = random.nextInt(maxRandom - minRandom + 1) + minRandom;
        targetNumber.setText(randomNumber + "");

        this.possible = TwentyFour.isPossible(numbers, randomNumber);
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
            gameOver();
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
    protected void evaluateResult(double result) {
        if(result == randomNumber) {
            for (int i = 0; i <4; i++) {
                if(cardAvailable[i]) {
                    setWrongAnswer();
                    return;
                }
            }
            setCorrectAnswer();
        } else {
            setWrongAnswer();
        }
    }
}