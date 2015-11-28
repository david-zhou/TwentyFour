package com.dzt.twentyfour.Activity;

import com.dzt.twentyfour.Class.TwentyFour;

public class NormalActivity extends RandomActivity {

    @Override
    protected void initializeViews() {
        super.initializeViews();
        mode = "normal";
        timer.setText("--:--");
    }

    @Override
    protected void checkPossible() {
        int [] numbers = new int [4];
        for(int i = 0; i < 4; i++){
            numbers[i] = currentCards[i].getNumber();
        }
        targetNumber.setText(24 + "");
        randomNumber = 24;
        this.possible = TwentyFour.isPossible(numbers, 24.0);
    }

}
