package com.home.atm6;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ContactActivity extends AppCompatActivity {

    private static final int REQUEST_CONTACT = 80;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        EdgeToEdge.enable (this);
        setContentView (R.layout.activity_contact);
        ViewCompat.setOnApplyWindowInsetsListener (findViewById (R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets (WindowInsetsCompat.Type.systemBars ( ));
            v.setPadding (systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        if(permission== PackageManager.PERMISSION_GRANTED){
            readContact();
        }else{
            ActivityCompat.requestPermissions (this, new String[]{android.Manifest.permission.READ_CONTACTS}, REQUEST_CONTACT);
        }

    }

    private void readContact() {
        try (
            Cursor cursor = getContentResolver ( ).query (ContactsContract.Contacts.CONTENT_URI, null, null,
                    null, null)) {
            while (true){
                assert cursor != null;
                if (!cursor.moveToNext()) break;
                @SuppressLint("Range") int id=cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                @SuppressLint("Range") int hasPhone=cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                if(hasPhone==1){
                    Cursor c2=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"=?",
                            new String[]{String.valueOf(id)},
                            null);
                    while(true){
                        assert c2 != null;
                        if (!c2.moveToNext()) break;
                        @SuppressLint("Range") String phone=c2.getString(c2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));
                        Log.i("Contact", name+" "+phone);
                    }
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult (requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CONTACT){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                readContact();
            }
        }
    }
}