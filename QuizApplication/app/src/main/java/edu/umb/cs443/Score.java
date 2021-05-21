package edu.umb.cs443;

public class Score {

    private String quiz_nr;
    private String userID;
    private int score;

    public Score() {
    }

    public Score(String quiz_nr, String userID, int score) {
        this.quiz_nr = quiz_nr;
        this.userID = userID;
        this.score = score;
    }

    public String getQuiz_nr() {
        return quiz_nr;
    }

    public void setQuiz_nr(String quiz_nr) {
        this.quiz_nr = quiz_nr;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
