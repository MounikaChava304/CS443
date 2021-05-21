package edu.umb.cs443;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    QuizDbHelper dbHelper = new QuizDbHelper(this);
    public static final String USER_ID = "user_id";
    private EditText userID;
    private EditText userPwd;
    private Button loginButton;
    private Spinner userAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userID = findViewById(R.id.userIdInput);
        userPwd = findViewById(R.id.userPassword);
        loginButton = findViewById(R.id.button_login);
        userAccount = findViewById(R.id.spinner_accountType);
        loadAccountTypes();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
    }

    private void loadAccountTypes() {
        String[] difficultyLevels = User.getAccountTypes();
        ArrayAdapter<String> adapterAccountType = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, difficultyLevels);
        adapterAccountType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userAccount.setAdapter(adapterAccountType);
    }

    private void userLogin() {
        String userId = userID.getText().toString();
        String userPassword = userPwd.getText().toString();
        String userAccountType = userAccount.getSelectedItem().toString();

        boolean checkUser = dbHelper.checkUser(userId, userPassword, userAccountType);
        if (checkUser && userAccountType == "Student") {
            Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
            Intent studentIntent = new Intent(LoginActivity.this, StudentActivity.class);
            studentIntent.putExtra(USER_ID, userId);
            startActivity(studentIntent);
        } else {
            if (checkUser && userAccountType == "Instructor") {
                Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                Intent instructorIntent = new Intent(LoginActivity.this, InstructorActivity.class);
                instructorIntent.putExtra(USER_ID, userId);
                startActivity(instructorIntent);
            } else {
                if (!checkUser) {
                    long result = dbHelper.addUser(userId, userPassword, userAccountType);
                    if (result > 0) {
                        Toast.makeText(LoginActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        if (userAccountType == "Student") {
                            Intent intent = new Intent(LoginActivity.this, StudentActivity.class);
                            intent.putExtra(USER_ID, userId);
                            startActivity(intent);
                        } else {
                            if (userAccountType == "Instructor") {
                                Intent intent = new Intent(LoginActivity.this, InstructorActivity.class);
                                intent.putExtra(USER_ID, userId);
                                startActivity(intent);
                            }
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Registration Error. Please check the Account Type.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }
    }
}