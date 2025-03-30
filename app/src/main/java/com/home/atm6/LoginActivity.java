package com.home.atm6;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText edUserid;
    private EditText edPasswd;


    @SuppressLint("ApplySharedPref")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUserid = (EditText) findViewById(R.id.userid);
        edPasswd= (EditText)findViewById(R.id.passwd);
        String userid=getSharedPreferences("atm",MODE_PRIVATE).getString("USERID", "");
        edUserid.setText(userid);
    }

    public void login(View view) {
        String userid=edUserid.getText().toString();
        String passwd=edPasswd.getText().toString();
        try{
            FirebaseDatabase.getInstance().getReference("users").child(userid).child("password")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @SuppressLint("ApplySharedPref")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String pw;
                            pw = Objects.requireNonNull(Objects.requireNonNull(snapshot).getValue()).toString();
                            if(pw.equals(passwd)){
                                getSharedPreferences("atm",MODE_PRIVATE)
                                        .edit()
                                        .putString("USERID",userid)
                                        .commit();
                                setResult(RESULT_OK);
                                finish();
                            }else{
                                new AlertDialog.Builder(LoginActivity.this)
                                        .setTitle("登入結果")
                                        .setMessage("登入失敗")
                                        .setPositiveButton("OK", null)
                                        .show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }catch (Exception ex){
            Log.e("FirebaseDatabase", ex.toString());
        }

    }

    public void quit(View view) {

    }
}