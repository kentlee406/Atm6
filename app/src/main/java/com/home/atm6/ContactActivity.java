package com.home.atm6;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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
            List<Contact> contacts=new ArrayList<> ();

            while (true){
                assert cursor != null;
                if (!cursor.moveToNext()) break;
                @SuppressLint("Range") int id=cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                @SuppressLint("Range") int hasPhone=cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                Contact contact=new Contact(id, name);
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
                        contact.getPhones().add(phone);
                    }
                    contacts.add(contact);
                }
                ContactAdapter adapter=new ContactAdapter(contacts);
                RecyclerView recyclerView=findViewById (R.id.recycler);
                recyclerView.setHasFixedSize (true);
                recyclerView.setLayoutManager (new LinearLayoutManager (this));
                recyclerView.setAdapter(adapter);
            }
        }
    }
    public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> { // Alt+Enter to implementation
        List<Contact> contacts;
        public ContactAdapter(List<Contact> contacts){
            this.contacts=contacts;
        }

        @NonNull
        @Override
        public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater ().inflate (android.R.layout.simple_list_item_2, parent, false);
            return new ContactHolder (view);
        }

        @Override
        public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
            Contact contact=contacts.get(position);
            holder.nameText.setText(contact.getName ());
            StringBuilder sb=new StringBuilder (  );
            for(String phone:contact.getPhones ()){
                sb.append(phone);
                sb.append(" ");
            }
            holder.phoneText.setText(sb);
        }


        @Override
        public int getItemCount() {
            return contacts.size();
        }

        public class ContactHolder extends RecyclerView.ViewHolder{
            TextView nameText, phoneText;
            public ContactHolder(View itemView){
                super(itemView);
                nameText=itemView.findViewById(android.R.id.text1);
                phoneText=itemView.findViewById(android.R.id.text2);

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