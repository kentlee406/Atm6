package com.home.atm6;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CAMERA = 5;
    private EditText edUserid;
    private EditText edPasswd;
    private CheckBox cb_rem_userid;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult (requestCode, permissions, grantResults);
        if (requestCode==REQUEST_CODE_CAMERA){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                takePhoto();
            }
        }
    }

    @SuppressLint("ApplySharedPref")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        int permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        if (permission== PackageManager.PERMISSION_GRANTED){
            takePhoto();  // Ctrl+Alt+M  // 新增拍照功能
        }else{
            ActivityCompat.requestPermissions (this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
        }


        edUserid = (EditText) findViewById(R.id.userid);
        edPasswd= (EditText)findViewById(R.id.passwd);
        cb_rem_userid= (CheckBox)findViewById(R.id.cb_rem_userid);
        cb_rem_userid.setChecked(getSharedPreferences("atm",MODE_PRIVATE).getBoolean("REMEMBER_USERID",false));
        cb_rem_userid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getSharedPreferences("atm",MODE_PRIVATE)
                        .edit()
                        .putBoolean("REMEMBER_USERID",b)
                        .apply();
            }
        });
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
                                boolean remember = getSharedPreferences("atm", MODE_PRIVATE)
                                        .getBoolean("REMEMBER_USERID", false);
                                if (remember){
                                    getSharedPreferences("atm",MODE_PRIVATE)
                                            .edit()
                                            .putString("USERID",userid)
                                            .commit();
                                }else{
                                    getSharedPreferences("atm",MODE_PRIVATE)
                                            .edit()
                                            .putString("USERID","")
                                            .commit();
                                }

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
        finish();

    }

    private void takePhoto() {
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
    }
}