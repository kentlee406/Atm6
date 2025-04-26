package com.home.atm6;

import static android.content.ContentValues.TAG;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TransActivity extends AppCompatActivity {
    public static final String TAG=TransActivity.class.getSimpleName ();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        EdgeToEdge.enable (this);
        setContentView (R.layout.activity_trans);
        ViewCompat.setOnApplyWindowInsetsListener (findViewById (R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets (WindowInsetsCompat.Type.systemBars ( ));
            v.setPadding (systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // The program start from this:
//        new TransTask ().execute ("https://atm201605.appspot.com/h");
        OkHttpClient client=new OkHttpClient (  );
        final Request request=new Request.Builder().url("https://atm201605.appspot.com/h").build();
        client.newCall (request).enqueue (new Callback ( ) {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                assert response.body ( ) != null;
                Log.d (TAG, "onResponse: "+response.body ().toString ());
            }
        });

    }
    public static class TransTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder sb;
            try {
                URL url=new URL ("https://atm201605.appspot.com/h");
                InputStream is=url.openStream ();
                BufferedReader in=new BufferedReader (new InputStreamReader (is));
                sb=new StringBuilder (  );
                String line=in.readLine ();
                while(line!=null){
                    sb.append(line);
                    line=in.readLine ();
                }
            } catch (IOException e) {
                throw new RuntimeException (e);
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute (s);
            Log.d (TAG, "onPostExecute: "+s);
        }
    }
}