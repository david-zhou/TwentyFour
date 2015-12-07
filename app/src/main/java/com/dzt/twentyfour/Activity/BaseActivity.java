package com.dzt.twentyfour.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dzt.twentyfour.Class.Card;
import com.dzt.twentyfour.Class.TwentyFour;
import com.dzt.twentyfour.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

public class BaseActivity extends Activity implements View.OnClickListener, View.OnTouchListener, View.OnLongClickListener, DialogInterface.OnClickListener, CheckBox.OnCheckedChangeListener{

    TextView timer, operationArea, resultArea, scoreCounter, checkBoxText, targetNumber;
    ImageView[] cardImages;
    boolean [] cardAvailable;
    boolean possible, showAgain;
    Button[] operatorButtons;
    Button nextCardsButton, deleteLastButton, skipCards;
    List<Card> deck = new ArrayList<>();
    List<String> operationAreaList = new ArrayList<>();
    Card[] currentCards = new Card[4];
    int timerSeconds, score, secondsPerCorrect, maxRandom, minRandom;
    Timer t;
    Stack<String> operationStack;
    Vibrator vibrator;
    View checkBoxView;
    CheckBox checkBox;
    AlertDialog.Builder builder;
    Random random;
    String mode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);

        initializeViews();
        initializeDeck();
        initializeTimer();
        shuffleCards();
        setCards();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //endGameExit();
    }

    @Override
    public void onBackPressed() {
        if (showAgain) {
            checkBoxView = View.inflate(this, R.layout.checkbox, null);
            checkBox = (CheckBox) checkBoxView.findViewById(R.id.checkbox);
            checkBox.setOnCheckedChangeListener(this);
            checkBoxText = (TextView) checkBoxView.findViewById(R.id.checkBoxText);
            checkBoxText.setOnClickListener(this);

            builder = new AlertDialog.Builder(this);
            builder.setMessage("Leaving will finish the game, are you sure?").setView(checkBoxView).setPositiveButton("Yes", this).setNegativeButton("No", this).setCancelable(true);

            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            endGameExit();
        }
    }

    protected void initializeTimer() {
        t = new Timer();
        //Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        String formattedTime = formatTime(timerSeconds);
                        timer.setText(formattedTime);
                        if (timerSeconds == 0) {
                            gameOver();
                        } else {
                            timerSeconds -= 1;
                        }
                    }
                });
            }
        }, 0, 1000);
    }

    protected void gameOver() {
        vibrator.vibrate(400);
        gameOverAnimation();
        t.cancel();

    }

    protected void gameOverAnimation() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                openScoreActivity();
            }
        }, 2000);
    }

    protected void openScoreActivity() {
        Bundle params = new Bundle();
        params.putString("mode", mode);
        params.putInt("score", score);

        Intent intent  = new Intent(this, ScoresActivity.class);
        intent.putExtras(params);
        startActivity(intent);

        finish();
    }

    protected String formatTime(int s) {
        String formattedTime = "";
        int minutes = s / 60;
        int seconds = s % 60;
        formattedTime += minutes + ":";
        if(seconds < 10) {
            formattedTime += "0" + seconds;
        } else {
            formattedTime += seconds;
        }
        return formattedTime;
    }

    protected void initializeViews() {

        cardImages = new ImageView[4];
        cardImages[0] = (ImageView) findViewById(R.id.card0);
        cardImages[1] = (ImageView) findViewById(R.id.card1);
        cardImages[2] = (ImageView) findViewById(R.id.card2);
        cardImages[3] = (ImageView) findViewById(R.id.card3);
        cardImages[0].setOnTouchListener(this);
        cardImages[1].setOnTouchListener(this);
        cardImages[2].setOnTouchListener(this);
        cardImages[3].setOnTouchListener(this);

        cardAvailable = new boolean [4];
        for(int i = 0; i < 4; i++) {
            cardAvailable[i] = true;
        }

        nextCardsButton = (Button) findViewById(R.id.next_button);
        nextCardsButton.setEnabled(false);
        nextCardsButton.setOnClickListener(this);

        deleteLastButton = (Button) findViewById(R.id.backspace_button);
        deleteLastButton.setOnClickListener(this);
        deleteLastButton.setOnLongClickListener(this);

        skipCards = (Button) findViewById(R.id.skip_button);
        skipCards.setOnClickListener(this);

        operatorButtons = new Button[6];
        operatorButtons[0] = (Button) findViewById(R.id.plus_button);
        operatorButtons[1] = (Button) findViewById(R.id.minus_button);
        operatorButtons[2] = (Button) findViewById(R.id.multiply_button);
        operatorButtons[3] = (Button) findViewById(R.id.divide_button);
        operatorButtons[4] = (Button) findViewById(R.id.open_parenthesis_button);
        operatorButtons[5] = (Button) findViewById(R.id.close_parenthesis_button);
        operatorButtons[0].setOnClickListener(this);
        operatorButtons[1].setOnClickListener(this);
        operatorButtons[2].setOnClickListener(this);
        operatorButtons[3].setOnClickListener(this);
        operatorButtons[4].setOnClickListener(this);
        operatorButtons[5].setOnClickListener(this);
        operatorButtons[0].setOnLongClickListener(this);
        operatorButtons[2].setOnLongClickListener(this);

        timerSeconds = 120;
        score = 0;
        secondsPerCorrect = 20;
        mode = "";

        timer = (TextView)findViewById(R.id.timer_text);

        operationArea = (TextView) findViewById(R.id.operation_area_label);

        scoreCounter = (TextView) findViewById(R.id.score_text);

        resultArea = (TextView) findViewById(R.id.result_area_label);

        operationStack = new Stack<>();

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        checkBoxView = View.inflate(this, R.layout.checkbox, null);
        checkBox = (CheckBox) checkBoxView.findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(this);
        checkBoxText = (TextView) checkBoxView.findViewById(R.id.checkBoxText);
        checkBoxText.setOnClickListener(this);


        SharedPreferences sp = getSharedPreferences("settings", MODE_PRIVATE);
        showAgain = sp.getBoolean("showAgain",true);

        builder = new AlertDialog.Builder(this);
        builder.setMessage("Leaving will finish the game, are you sure?").setView(checkBoxView).setPositiveButton("Yes", this).setNegativeButton("No", this).setCancelable(true);

        random = new Random();
        maxRandom = 50;
        minRandom = 1;

        targetNumber = (TextView) findViewById(R.id.target_text);

    }

    protected void initializeDeck() {
        for(int i = 0; i < 52; i++) {
            if ((i + 1) % 13 != 0) {
                Card newCard = numberToCard(i);
                deck.add(newCard);
            }
        }
    }

    protected Card numberToCard(int i) {
        int number = i%13;
        int suit = i/13;
        String s = "";
        switch(suit){
            case 0:
                s = "Diamonds";
                break;
            case 1:
                s = "Clubs";
                break;
            case 2:
                s = "Hearts";
                break;
            case 3:
                s = "Spades";
                break;
        }
        return new Card(number + 1, s, i);
    }

    protected void shuffleCards() {
        Random r = new Random();
        int numberOfCards = deck.size();
        for(int i = 0; i < 1000; i++) {
            int cardToShuffle = r.nextInt(numberOfCards);
            Card popedCard = deck.remove(cardToShuffle);
            deck.add(popedCard);
        }
    }

    protected void setCards() {
        for(int i = 0; i< 4; i++){
            currentCards[i] = deck.remove(0);
            cardImages[i].setImageResource(currentCards[i].getImageId());
        }

        checkPossible();
    }

    protected void checkPossible(){
        int [] numbers = new int [4];
        for(int i = 0; i < 4; i++){
            numbers[i] = currentCards[i].getNumber();
        }

        this.possible = TwentyFour.isPossible(numbers, 24.0);
    }

    protected void correct() {
        showNextCards();
        addTime();
        resetCards();
        addScore();
    }

    protected void addScore() {
        score++;
        scoreCounter.setText(score + "");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.checkBoxText:
                //showAgain = !showAgain;
                checkBox.setChecked(showAgain);
                break;
            case R.id.next_button:
                correct();
                break;
            case R.id.skip_button:
                skipCards();
                break;
            case R.id.backspace_button:
                removeLast();
                break;
            case R.id.plus_button:
                if(!operationStack.isEmpty()) {
                    String top = operationStack.peek();
                    if ((top.equals("0") || top.equals("1") || top.equals("2") || top.equals("3") || top.equals(")"))) {
                        putOperatorInOperationArea('+');
                    }
                }
                break;
            case R.id.minus_button:
                if(!operationStack.isEmpty()) {
                    String top = operationStack.peek();
                    if ((top.equals("0") || top.equals("1") || top.equals("2") || top.equals("3") || top.equals(")"))) {
                        putOperatorInOperationArea('-');
                    }
                }
                break;
            case R.id.multiply_button:
                if(!operationStack.isEmpty()) {
                    String top = operationStack.peek();
                    if ((top.equals("0") || top.equals("1") || top.equals("2") || top.equals("3") || top.equals(")"))) {
                        putOperatorInOperationArea('*');
                    }
                }
                break;
            case R.id.divide_button:
                if(!operationStack.isEmpty()) {
                    String top = operationStack.peek();
                    if ((top.equals("0") || top.equals("1") || top.equals("2") || top.equals("3") || top.equals(")"))) {
                        putOperatorInOperationArea('/');
                    }
                }
                break;
            case R.id.open_parenthesis_button:
                if(!operationStack.isEmpty()) {
                    String top = operationStack.peek();
                    if (!(top.equals("0") || top.equals("1") || top.equals("2") || top.equals("3") || top.equals(")"))) {
                        putOperatorInOperationArea('(');
                    }
                } else {
                    putOperatorInOperationArea('(');
                }
                break;
            case R.id.close_parenthesis_button:
                if(!operationStack.isEmpty()) {
                    String top = operationStack.peek();
                    if ((top.equals("0") || top.equals("1") || top.equals("2") || top.equals("3") || top.equals(")"))) {
                        putOperatorInOperationArea(')');
                    }
                }
                break;
        }
    }

    protected void skipCards() {
        if(possible) {
            if(timerSeconds < secondsPerCorrect) {
                timer.setText(formatTime(0));
                gameOver();
                return;
            } else {
                timerSeconds -= secondsPerCorrect;
            }
            vibrator.vibrate(100);
            showNextCards();
            resetCards();
        } else {
            correct();
        }
    }

    protected void resetCards() {
        for(int i = 0; i < 4; i++) {
            cardImages[i].setAlpha(1.0f);
            cardAvailable[i] = true;
        }
        operationStack.removeAllElements();
        operationAreaList = new ArrayList<>();
        operationArea.setText("");
        resultArea.setText("");
        nextCardsButton.setEnabled(false);
    }

    protected void addTime() {
        timerSeconds += secondsPerCorrect;
    }

    protected void showNextCards() {
        if(deck.size() == 0) {
            if(secondsPerCorrect < 5) {
                secondsPerCorrect = 5;
            } else {
                secondsPerCorrect--;
            }
            initializeDeck();
            shuffleCards();
            setCards();
        } else {
            setCards();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int viewId = v.getId();
                for(int i = 0; i < 4; i++) {
                    if (viewId == cardImages[i].getId() && cardAvailable[i]) {
                        if(operationStack.isEmpty()) {
                            disableCard(i);
                            putNumberInOperationArea(i);
                        } else {
                            String top = operationStack.peek();
                            if (!(top.equals("0") || top.equals("1") || top.equals("2") || top.equals("3") || top.equals(")"))) {
                                disableCard(i);
                                putNumberInOperationArea(i);
                            }
                        }
                        break;
                    }
                }
                break;
            default:
                break;
        }
        return true;
    }

    protected void putStringInOperationArea() {
        String finalString = "";
        for (String s : operationAreaList) {
            finalString+=s;
        }
        operationArea.setText(finalString);
    }

    protected void putNumberInOperationArea(int i) {
        operationStack.add(i + "");
        operationAreaList.add(currentCards[i].getNumber() + "");
        putStringInOperationArea();
        evaluateOperation();
    }

    protected void putOperatorInOperationArea(char c) {
        operationStack.add(c + "");
        operationAreaList.add(c+"");
        putStringInOperationArea();
        evaluateOperation();
    }

    protected void disableCard(int i) {
        cardImages[i].setAlpha(0.5f);
        cardAvailable[i] = false;
    }

    protected void enableCard(int i) {
        cardImages[i].setAlpha(1.0f);
        cardAvailable[i] = true;
    }

    protected void removeLast() {
        if (!operationStack.empty()) {
            String popped = operationStack.pop();
            switch(popped) {
                case "0":
                    enableCard(0);
                    break;
                case "1":
                    enableCard(1);
                    break;
                case "2":
                    enableCard(2);
                    break;
                case "3":
                    enableCard(3);
                    break;
                default:
                    break;
            }
            operationAreaList.remove(operationAreaList.size() - 1);
            putStringInOperationArea();
            evaluateOperation();
        }
    }

    protected void evaluateOperation() {
        Stack<String> copyStack = (Stack<String>)operationStack.clone();
        Stack<String> copyToSend = new Stack<>();
        while(!copyStack.empty()) {
            String pop = copyStack.pop();
            switch (pop) {
                case "0":
                    copyToSend.push(currentCards[0].getNumber() + "");
                    break;
                case "1":
                    copyToSend.push(currentCards[1].getNumber() + "");
                    break;
                case "2":
                    copyToSend.push(currentCards[2].getNumber() + "");
                    break;
                case "3":
                    copyToSend.push(currentCards[3].getNumber() + "");
                    break;
                default:
                    copyToSend.push(pop);
                    break;
            }
        }
        String result = TwentyFour.evaluateOperation(operationArea.getText().toString(), copyToSend);
        resultArea.setText(result + "");
        if(result.equals("")) {
            setWrongAnswer();

        } else {
            evaluateResult(Double.parseDouble(result));
        }
    }

    protected void evaluateResult(double result) {
        if(result == 24.0) {
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

    protected void setWrongAnswer() {
        nextCardsButton.setEnabled(false);
    }

    protected void setCorrectAnswer() {
        nextCardsButton.setEnabled(true);
    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.plus_button:
                sumAll();
                return true;
            case R.id.multiply_button:
                multiplyAll();
                return true;
            case R.id.backspace_button:
                deleteAll();
                return true;
        }
        return false;
    }

    protected void sumAll() {
        if (!operationStack.empty()) {
            deleteAll();
        }
        for(int i = 0; i < 3; i++) {
            disableCard(i);
            putNumberInOperationArea(i);
            putOperatorInOperationArea('+');
        }
        disableCard(3);
        putNumberInOperationArea(3);
    }

    protected void multiplyAll() {
        if (!operationStack.empty()) {
            deleteAll();
        }
        for(int i = 0; i < 3; i++) {
            disableCard(i);
            putNumberInOperationArea(i);
            putOperatorInOperationArea('*');
        }
        disableCard(3);
        putNumberInOperationArea(3);
    }

    protected void deleteAll(){
        while(!operationStack.empty()) {
            removeLast();
        }
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        switch (i) {
            case DialogInterface.BUTTON_POSITIVE:
                doNotShowAgain();
                endGameExit();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                doNotShowAgain();
                break;
        }


    }
    protected void doNotShowAgain() {
        if (!showAgain) {
            SharedPreferences sp = getSharedPreferences("settings",MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("showAgain", false);
            editor.apply();
        }
    }

    protected void endGameExit() {
        saveRecord();
        finish();
    }

    protected void saveRecord() {
        SharedPreferences sp = getSharedPreferences("Scores", MODE_PRIVATE);
        int thisModeHighScore = sp.getInt(mode,0);
        if (score > thisModeHighScore) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt(mode, score);
            editor.apply();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        showAgain = !b;
    }

}
