package com.home.atm6;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import androidx.annotation.Nullable;

public class NewsFragment extends Fragment {
    public static NewsFragment instance;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    public static NewsFragment getInstance() {
        if (instance==null){
            instance = new NewsFragment ();
        }
        return instance;
    }
}
