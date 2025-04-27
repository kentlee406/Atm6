package com.home.atm6;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

public class Transaction extends RecyclerView.Adapter {
    String account;
    String date;
    int amount;
    int type;

    public Transaction(){

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public Transaction(JSONObject object) {
        try {
            String account=object.getString ("account");
            String date=object.getString ("date");
            int amount=object.getInt ("amount");
            int type=object.getInt ("type");
        } catch (JSONException e) {
            throw new RuntimeException (e);
        }
    }
    // Alt+Insert


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
