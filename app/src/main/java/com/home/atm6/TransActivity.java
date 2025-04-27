package com.home.atm6;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class TransActivity extends AppCompatActivity {
    public static final String TAG=TransActivity.class.getSimpleName ();
    private static ArrayList<Transaction> transactions;
    public static RecyclerView recycler;

    @SuppressLint("MissingInflatedId")
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
        recycler = findViewById (R.id.recycler);
        recycler.setHasFixedSize (true);
        recycler.setLayoutManager (new LinearLayoutManager (this));

        new TransTask ().execute ("https://atm201605.appspot.com/h");
        // 2025.04.27 Added

        /* 2025.04.27 在留言區發問的問題--使用okhttpclient套件印不出JSON格式
        OkHttpClient client=new OkHttpClient (  );
        final Request request=new Request.Builder().url("https://atm201605.appspot.com/h").build();
        client.newCall (request).enqueue (new Callback ( ) {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                assert response.body ( ) != null;
                Log.d (TAG, "onResponse: "+response.body().toString());
            }
        });
        */
    }
    @SuppressLint("StaticFieldLeak")
    public class TransTask extends AsyncTask<String, Void, String>{

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
            runOnUiThread (new Runnable ( ) {
                @Override
                public void run() {

                    parseJSON(s);

                }
            });



        }








    }

    public class TransAdapter extends RecyclerView.Adapter<TransAdapter.TransHolder> {

        @NonNull
        @Override
        public TransHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=getLayoutInflater ().inflate (R.layout.item_trans, parent, false);
            return new TransHolder (view);
        }

        @Override
        public void onBindViewHolder(@NonNull TransHolder holder, int position) {
            Transaction tran= transactions.get(position);
            holder.bindTo(tran);
        }

        @Override
        public int getItemCount() {
            return transactions.size();
        }

        public class TransHolder extends RecyclerView.ViewHolder{
            TextView dateText, amountText, typeText;
            public TransHolder(View itemView){
                super(itemView);
                dateText=itemView.findViewById (R.id.item_date);
                amountText=itemView.findViewById (R.id.item_amount);
                typeText=itemView.findViewById (R.id.item_type);
            }

            public void bindTo(Transaction tran) {
                dateText.setText(tran.getDate ());
                amountText.setText(String.valueOf(tran.getAmount ()));
                typeText.setText(tran.getType());
            }
        }
    }
    public static void parseJSON(String json) {
        transactions = new ArrayList<> (  );

        try {

            JSONArray array=new JSONArray ( json );
            // array.length ().fori
            for (int i = 0; i < array.length ( ); i++) {
                JSONObject object=array.getJSONObject (i);
                transactions.add(new Transaction ( object ));
            }
        } catch (JSONException e) {
            throw new RuntimeException (e);
        }
        Transaction adapter=new Transaction (  );
        recycler.setAdapter (adapter);

    }

}