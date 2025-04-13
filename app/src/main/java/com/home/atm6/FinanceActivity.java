package com.home.atm6;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.home.atm6.databinding.ActivityFinanceBinding;

public class FinanceActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityFinanceBinding binding;

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
    }

    @Override
    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController (this, R.id.nav_host_fragment_content_finance);
        return true;
    }
}