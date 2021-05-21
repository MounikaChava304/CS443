package edu.umb.cs443;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddQuestionActivity extends AppCompatActivity {

    private EditText quizId;
    private EditText question;
    private EditText option1;
    private EditText option2;
    private EditText option3;
    private EditText option4;
    private EditText answerNr;

    private String newQuizId;
    private String newQuestion;
    private String newOption1;
    private String newOption2;
    private String newOption3;
    private String newOption4;
    private int newAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        quizId = findViewById(R.id.edit_text_quizID);
        question = findViewById(R.id.edit_text_question);
        option1 = findViewById(R.id.edit_text_option_1);
        option2 = findViewById(R.id.edit_text_option_2);
        option3 = findViewById(R.id.edit_text_option_3);
        option4 = findViewById(R.id.edit_text_option_4);
        answerNr = findViewById(R.id.edit_text_answer);

        Button buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveQuestion();
            }
        });

        Button buttonBack = findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddQuestionActivity.this, InstructorActivity.class);
                startActivity(intent);
            }
        });

    }

    private void saveQuestion() {
        newQuizId = quizId.getText().toString();
        newQuestion = question.getText().toString();
        newOption1 = option1.getText().toString();
        newOption2 = option2.getText().toString();
        newOption3 = option3.getText().toString();
        newOption4 = option4.getText().toString();
        newAnswer = Integer.parseInt(answerNr.getText().toString());

        Question q = new Question();
        q.setQuizNr(newQuizId);
        q.setQuestion(newQuestion);
        q.setOption1(newOption1);
        q.setOption2(newOption2);
        q.setOption3(newOption3);
        q.setOption4(newOption4);
        q.setAnswerNr(newAnswer);

        Log.i("INFO_QSTN", String.valueOf(newAnswer));

        QuizDbHelper dbHelper = new QuizDbHelper(this);
        dbHelper.addQuestion(q);

        Toast.makeText(AddQuestionActivity.this, "Question Added Successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddQuestionActivity.this, AddQuestionActivity.class);
        startActivity(intent);
    }
}