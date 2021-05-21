package edu.umb.cs443;

import android.provider.BaseColumns;

public final class QuizContract {

    private QuizContract(){}

    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz_questions";
        public static final String COLUMN_QUIZ_NR = "quiz_nr";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_OPTION4 = "option4";
        public static final String COLUMN_ANSWER_NR = "answer_nr";

    }

    public static class ScoresTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz_scores";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_QUIZ_NR = "quiz_nr";
        public static final String COLUMN_SCORE = "score";

    }

    public static class UsersTable implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_ACCOUNT_TYPE = "accountType";

    }
}
