package edu.umb.cs443;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewScoreActivity extends AppCompatActivity {

    private ListView listView;
    private String quizNumber;
    private Score currentScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_score);

        Intent intent = getIntent();
        quizNumber = intent.getStringExtra(InstructorActivity.QUIZ_NR);
        updateListViewScores();

        Button buttonBack = findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewScoreActivity.this, InstructorActivity.class);
                startActivity(intent);
            }
        });
    }

    public void updateListViewScores() {

        QuizDbHelper dbHelper = new QuizDbHelper(this);
        ArrayList<Score> scoresList = dbHelper.getAllScores(quizNumber);
        int scoresListSize = scoresList.size();

        ArrayList<String> userFinalScores = new ArrayList<String>();
        if (scoresListSize > 0) {
            for (int scoreList = 0; scoreList < scoresListSize; scoreList++) {
                currentScore = scoresList.get(scoreList);
                String userID = currentScore.getUserID();
                int userScore = currentScore.getScore();
                String userDetails = userID + " : " + userScore;
                userFinalScores.add(userDetails);

                ArrayAdapter<String> scoresAdapter =
                        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, userFinalScores);

                listView = findViewById(R.id.scores_list);
                listView.setAdapter(scoresAdapter);
                Log.i("INFO_USER_SCORE_DETAILS", userDetails);


            }
        } else {
            Toast.makeText(ViewScoreActivity.this, "No Scores Updated for this quiz: " + quizNumber, Toast.LENGTH_SHORT).show();
        }
        Log.i("INFO_TAG_INSTR", String.valueOf(scoresList));
    }
}