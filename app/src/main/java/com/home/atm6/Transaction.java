package com.home.atm6;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

public class Transaction {
    String account;
    String date;
    int amount;
    int type;

    public Transaction(){

    }

    public Transaction(JSONObject object) {
        try {
            // 2025.05.03 Kent 這裡不要加上String或int
            account=object.getString ("account");
            date=object.getString ("date");
            amount=object.getInt ("amount");
            type=object.getInt ("type");
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
