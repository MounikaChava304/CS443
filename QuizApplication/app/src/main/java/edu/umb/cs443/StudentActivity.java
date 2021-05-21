package edu.umb.cs443;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StudentActivity extends AppCompatActivity {
    public static final String QUIZ_NR = "QuizNr";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighScore";
    public static final String KEY_USERID = "KeyUserId";
    public static final String KEY_QUIZNR = "keyQuizNr";
    private static final int REQUEST_CODE_QUIZ = 1;
    QuizDbHelper dbHelper = new QuizDbHelper(this);
    private TextView textViewHighScore;
    private EditText quizNumberInput;
    private int score;
    private int questionCount;
    private String user_id = null;
    private String quizNumber = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        textViewHighScore = findViewById(R.id.text_view_highScore);
        quizNumberInput = findViewById(R.id.quizIdInput);

        Intent intent = getIntent();
        user_id = intent.getStringExtra(LoginActivity.USER_ID);
        textViewHighScore.setText("Your Score is: 0");

        Button buttonStartQuiz = findViewById(R.id.button_start_quiz);
        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });
    }

    private void startQuiz() {
        quizNumber = quizNumberInput.getText().toString();
        if (quizNumber != null || quizNumber.isEmpty()) {
            Log.i("INFO_TAG", quizNumber);
            Intent intent = new Intent(StudentActivity.this, QuizActivity.class);
            intent.putExtra(QUIZ_NR, quizNumber);
            startActivityForResult(intent, REQUEST_CODE_QUIZ);
        } else {
            Toast.makeText(StudentActivity.this, "Enter the Quiz ID", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK && data != null) {
                score = data.getIntExtra(QuizActivity.EXTRA_SCORE, 0);
                questionCount = data.getIntExtra(QuizActivity.QUESTION_COUNT, 0);
                if (questionCount != 0) {
                    updateScore();
                } else {
                    Toast.makeText(StudentActivity.this, "No Questions uploaded for this quiz ID", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(StudentActivity.this, "Error in fetching score.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateScore() {

        textViewHighScore.setText("Your Score is: " + score);

        Log.i("INFO_DB_TAG", "ENTERED");
        Score updatedScore = new Score();
        updatedScore.setUserID(user_id);
        updatedScore.setQuiz_nr(quizNumber);
        updatedScore.setScore(score);
        Boolean userScore = dbHelper.checkUserScore(user_id);
        if (userScore) {
            dbHelper.updateScore(updatedScore);
        } else {
            dbHelper.addScore(updatedScore);
        }
        Log.i("INFO_DB_TAG", "Score Updated Successfully");

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_USERID, user_id);
        editor.putString(KEY_QUIZNR, quizNumber);
        editor.putInt(KEY_HIGHSCORE, score);
        editor.commit();

    }

}