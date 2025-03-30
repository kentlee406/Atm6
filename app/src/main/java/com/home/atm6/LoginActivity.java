package com.home.atm6;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    private EditText edUserid;
    private EditText edPasswd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUserid = (EditText) findViewById(R.id.userid);
        edPasswd= (EditText)findViewById(R.id.passwd);
    }

    public void login(View view) {
        String userid=edUserid.getText().toString();
        String passwd=edPasswd.getText().toString();
        if((userid.equals("kent"))&&(passwd.equals("1234"))){
            finish();
        }
    }

    public void quit(View view) {

    }
}