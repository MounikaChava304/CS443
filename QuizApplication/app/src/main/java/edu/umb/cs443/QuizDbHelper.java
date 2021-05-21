package edu.umb.cs443;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.umb.cs443.QuizContract.QuestionsTable;
import edu.umb.cs443.QuizContract.ScoresTable;
import edu.umb.cs443.QuizContract.UsersTable;


public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "OnlineQuiz.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUIZ_NR + " TEXT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER " +
                " ) ";

        final String SQL_CREATE_USERS_TABLE = "CREATE TABLE " +
                UsersTable.TABLE_NAME + " ( " +
                UsersTable.COLUMN_USER_ID + " TEXT PRIMARY KEY, " +
                UsersTable.COLUMN_PASSWORD + " TEXT, " +
                UsersTable.COLUMN_ACCOUNT_TYPE + " TEXT " +
                " ) ";

        final String SQL_CREATE_SCORES_TABLE = "CREATE TABLE " +
                ScoresTable.TABLE_NAME + " ( " +
                ScoresTable.COLUMN_USER_ID + " TEXT, " +
                ScoresTable.COLUMN_QUIZ_NR + " TEXT, " +
                ScoresTable.COLUMN_SCORE + " INTEGER " +
                " ) ";

        db.execSQL(SQL_CREATE_USERS_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        db.execSQL(SQL_CREATE_SCORES_TABLE);

        db.execSQL("INSERT INTO quiz_questions(quiz_nr,question,option1,option2,option3,option4,answer_nr) VALUES ('443','What is the capital of Massachusetts?','Boston','Quincy','Newton','Wellesley','1')");
        db.execSQL("INSERT INTO quiz_questions(quiz_nr,question,option1,option2,option3,option4,answer_nr) VALUES ('443','What is your favourite color?','Red','Black','White','Blue','2')");
        db.execSQL("INSERT INTO quiz_questions(quiz_nr,question,option1,option2,option3,option4,answer_nr) VALUES ('443','What is your hobby?','Reading Books','Watching Movies','Playing Football','Cooking','3')");
        db.execSQL("INSERT INTO quiz_questions(quiz_nr,question,option1,option2,option3,option4,answer_nr) VALUES ('675','What is your favoruite sport?','Baseball','Cricket','Football','Basketball','4')");
        db.execSQL("INSERT INTO quiz_questions(quiz_nr,question,option1,option2,option3,option4,answer_nr) VALUES ('675','Expand WHO','World Health Organization','Women Health Organization','Wellness Health Organization','World Healing Organization','1')");

        db.execSQL("INSERT INTO quiz_scores(user_id,quiz_nr,score) VALUES ('12345','443',2)");
        db.execSQL("INSERT INTO quiz_scores(user_id,quiz_nr,score) VALUES ('12345','675',1)");

        db.execSQL("INSERT INTO users(user_id,password,accountType) VALUES ('12345','12345','Student')");
        db.execSQL("INSERT INTO users(user_id,password,accountType) VALUES ('443','443','Instructor')");
    }

    public long addUser(String userID, String pwd, String accountType) {
//        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(UsersTable.COLUMN_USER_ID, userID);
        cv.put(UsersTable.COLUMN_PASSWORD, pwd);
        cv.put(UsersTable.COLUMN_ACCOUNT_TYPE, accountType);
        long result = db.insert(UsersTable.TABLE_NAME, null, cv);
        return result;

    }

    public boolean checkUser(String userID, String pwd, String accountType) {
        db = this.getReadableDatabase();
        String[] selectionArgs = new String[]{userID, pwd, accountType};
        Cursor c = db.rawQuery("SELECT * FROM " + UsersTable.TABLE_NAME +
                " WHERE " + UsersTable.COLUMN_USER_ID + " = ?" + "AND " + UsersTable.COLUMN_PASSWORD + " = ? " + "AND " + UsersTable.COLUMN_ACCOUNT_TYPE + " = ? ", selectionArgs);
        return c.getCount() > 0;
    }


    public void addQuestion(Question question) {
        db = getWritableDatabase();
        Log.i("INFO_DB", String.valueOf(db));
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUIZ_NR, question.getQuizNr());
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_OPTION4, question.getOption4());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);

    }

    public List<Question> getAllQuestions(String quizId) {
        List<Question> questionList = new ArrayList<>();
        db = this.getReadableDatabase();

        String[] selectionArgs = new String[]{quizId};
        //Log.i("INFO",quizId);
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME +
                " WHERE " + QuestionsTable.COLUMN_QUIZ_NR + " = ?", selectionArgs);
        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuizNr(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUIZ_NR)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

    public boolean checkUserScore(String userID) {
        db = this.getReadableDatabase();
        String[] selectionArgs = new String[]{userID};
        Cursor c = db.rawQuery("SELECT * FROM " + ScoresTable.TABLE_NAME +
                " WHERE " + ScoresTable.COLUMN_USER_ID + " = ?", selectionArgs);
        return c.getCount() > 0;
    }

    public void addScore(Score score) {
        db = getWritableDatabase();
        Log.i("INFO_SCORE_DB", String.valueOf(db));
        ContentValues cv = new ContentValues();
        cv.put(ScoresTable.COLUMN_USER_ID, score.getUserID());
        cv.put(ScoresTable.COLUMN_QUIZ_NR, score.getQuiz_nr());
        cv.put(ScoresTable.COLUMN_SCORE, score.getScore());
        db.insert(ScoresTable.TABLE_NAME, null, cv);
    }

    public void updateScore(Score score) {
        db = getWritableDatabase();
        String user_id = score.getUserID();
        String quiz_nr = score.getQuiz_nr();
        int score_new = score.getScore();
        Log.i("INFO_SCORE_UPDATE", String.valueOf(db));
        ContentValues cv = new ContentValues();
        cv.put(ScoresTable.COLUMN_USER_ID, score.getUserID());
        cv.put(ScoresTable.COLUMN_QUIZ_NR, score.getQuiz_nr());
        cv.put(ScoresTable.COLUMN_SCORE, score.getScore());
        db.update(ScoresTable.TABLE_NAME,cv,"user_id = '"+user_id+"' AND quiz_nr = '"+quiz_nr+"'",null);
    }

    public ArrayList<Score> getAllScores(String quizId) {
        ArrayList<Score> scoreList = new ArrayList<>();
        db = this.getReadableDatabase();
        Log.i("INFO_DB_SCORES_QUIZ", quizId);
        Log.i("INFO_DB_SCORES", "Getting Scores");

        String[] selectionArgs = new String[]{quizId};
        Cursor c = db.rawQuery("SELECT * FROM " + ScoresTable.TABLE_NAME +
                " WHERE " + ScoresTable.COLUMN_QUIZ_NR + " = ?", selectionArgs);
        if (c.moveToFirst()) {
            do {
                Score score = new Score();
                score.setQuiz_nr(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUIZ_NR)));
                score.setUserID(c.getString(c.getColumnIndex(ScoresTable.COLUMN_USER_ID)));
                score.setScore(c.getInt(c.getColumnIndex(ScoresTable.COLUMN_SCORE)));
                scoreList.add(score);
            } while (c.moveToNext());
        }
        c.close();
        Log.i("INFO_DB_SCORES", scoreList.toString());
        return scoreList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UsersTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ScoresTable.TABLE_NAME);
        Log.i("Info", "Table Dropped");
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UsersTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ScoresTable.TABLE_NAME);
        Log.i("Info", "Table Dropped");
        onCreate(db);
    }
}
