package edu.umb.cs443;

public class User {
    public static final String INSTRUCTOR_ACCOUNT = "Instructor";
    public static final String STUDENT_ACCOUNT = "Student";

    private String userID;
    private String userPwd;
    private String userAccountType;

    public User() {

    }

    public User(String userID, String userPwd, String userAccountType) {
        this.userID = userID;
        this.userPwd = userPwd;
        this.userAccountType = userAccountType;
    }

    public static String[] getAccountTypes() {
        return new String[]{
                INSTRUCTOR_ACCOUNT,
                STUDENT_ACCOUNT
        };
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserAccountType() {
        return userAccountType;
    }

    public void setUserAccountType(String userAccountType) {
        this.userAccountType = userAccountType;
    }
}
