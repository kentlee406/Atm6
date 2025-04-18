package com.home.atm6;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddActivity extends AppCompatActivity {

    private EditText ed_date;
    private EditText ed_info;
    private EditText ed_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        EdgeToEdge.enable (this);
        setContentView (R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener (findViewById (R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets (WindowInsetsCompat.Type.systemBars ( ));
            v.setPadding (systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ed_date = findViewById (R.id.ed_date);
        ed_info = findViewById (R.id.ed_info);
        ed_amount = findViewById (R.id.ed_amount);
    }

    public void add(View view) {
        String date = ed_date.getText().toString ();
        String info = ed_info.getText().toString ();
        int amount= Integer.parseInt (ed_amount.getText().toString ());
        ExpenseHelper helper=new ExpenseHelper (this, "atm", null,1);
        ContentValues values = new ContentValues (  );
        values.put("cdate", date);
        values.put("info", info);
        values.put("amount", amount);
        long id=helper.getWritableDatabase().insert("expense", null, values);
        if(id>-1){
            Toast.makeText (this,"新增完成", Toast.LENGTH_LONG ).show();
        }else{
            Toast.makeText (this,"新增失敗", Toast.LENGTH_LONG ).show();
        }
    }
}