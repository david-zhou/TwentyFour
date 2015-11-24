package com.dzt.twentyfour.Class;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dzt.twentyfour.R;

import java.util.ArrayList;

public class ScoreboardListAdapter extends ArrayAdapter {
    Context context;
    ArrayList<Score> scores;

    public ScoreboardListAdapter(Context context, ArrayList<Score> scores) {
        super(context, R.layout.score_listview, scores);
        this.context = context;
        this.scores = scores;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.score_listview, null);

        if(position % 2 == 1) {
            item.setBackgroundColor(Color.LTGRAY);
        } else {
            item.setBackgroundColor(Color.WHITE);
        }
        //item.getLayoutParams().height =
        item.setMinimumHeight(parent.getHeight() / scores.size() - 5);

        TextView mode = (TextView) item.findViewById(R.id.score_listview_mode);
        TextView score = (TextView) item.findViewById(R.id.score_listview_score);

        String currentMode = scores.get(position).mode;
        int currentScore = scores.get(position).score;

        mode.setText(currentMode);
        score.setText(currentScore + "");

        return item;
    }
}
