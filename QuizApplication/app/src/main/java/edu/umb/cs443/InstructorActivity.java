package edu.umb.cs443;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class InstructorActivity extends AppCompatActivity {

    public static final String QUIZ_NR = "QuizNr";
    private EditText quizIdInput;
    private String quizNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);

        Button buttonAddQuestion = findViewById(R.id.button_add_questions);
        buttonAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructorActivity.this, AddQuestionActivity.class);
                startActivity(intent);
            }
        });

        Button buttonViewScores = findViewById(R.id.button_view_scores);
        buttonViewScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizIdInput = findViewById(R.id.quizIdInput);
                quizNumber = quizIdInput.getText().toString();
                Intent intent = new Intent(InstructorActivity.this, ViewScoreActivity.class);
                intent.putExtra(QUIZ_NR, quizNumber);
                startActivity(intent);
                Log.i("INFO_TAG_INSTR", quizNumber);
            }
        });

    }

}