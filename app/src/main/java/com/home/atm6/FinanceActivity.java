package com.home.atm6;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.home.atm6.databinding.ActivityFinanceBinding;

public class FinanceActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityFinanceBinding binding;
    private ExpenseAdapter adapter;
    private RecyclerView recyclerView;
    private ExpenseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        binding = ActivityFinanceBinding.inflate (getLayoutInflater ( ));
        setContentView (binding.getRoot ( ));

        setSupportActionBar (binding.toolbar);

//        NavController navController = Navigation.findNavController (this, R.id.nav_host_fragment_content_finance);
//        appBarConfiguration = new AppBarConfiguration.Builder (navController.getGraph ( )).build ( );
//        NavigationUI.setupActionBarWithNavController (this, navController, appBarConfiguration);

        binding.fab.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent (FinanceActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById (R.id.recycleView);
        recyclerView.setHasFixedSize (true);
        recyclerView.setLayoutManager (new LinearLayoutManager (this));

        helper = new ExpenseHelper(this);
        try {
            Cursor cursor = helper.getReadableDatabase ( ).query ("expense", null, null, null, null, null, null);
            adapter = new ExpenseAdapter (cursor);
            recyclerView.setAdapter(adapter);  //**//
        } catch (Exception e) {
            throw new RuntimeException (e);
        }

    }
    // Ctrl+O


    @Override
    protected void onResume() {
        super.onResume ( );
        Cursor cursor = helper.getReadableDatabase ( ).query ("expense", null, null, null, null, null, null);
        adapter = new ExpenseAdapter (cursor);
        recyclerView.setAdapter(adapter);  //**//
    }

    public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseHolder>{
        Cursor cursor;
        public ExpenseAdapter(Cursor cursor) {
            this.cursor=cursor;
        }

        @NonNull
        @Override
        public ExpenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=getLayoutInflater ().inflate (R.layout.expense_item, parent, false);
            return new ExpenseHolder (view);
        }

        @Override
        public void onBindViewHolder(@NonNull ExpenseHolder holder, int position) {
            cursor.moveToPosition (position);
            @SuppressLint("Range") String date=cursor.getString (cursor.getColumnIndex("cdate"));
            @SuppressLint("Range") String info=cursor.getString (cursor.getColumnIndex("info"));
            @SuppressLint("Range") int amount=cursor.getInt(cursor.getColumnIndex ("amount"));
            holder.dateText.setText(date);
            holder.infoText.setText(info);
            holder.amountText.setText(String.valueOf(amount));

        }

        @Override
        public int getItemCount() {
            return cursor.getCount ();
        }

        public class ExpenseHolder extends RecyclerView.ViewHolder{
            TextView dateText;
            TextView infoText;
            TextView amountText;

            public ExpenseHolder(View itemView){
                super(itemView);
                dateText=itemView.findViewById (R.id.item_date);
                infoText=itemView.findViewById (R.id.item_info);
                amountText=itemView.findViewById (R.id.item_amount);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController (this, R.id.nav_host_fragment_content_finance);
        return true;
    }
}