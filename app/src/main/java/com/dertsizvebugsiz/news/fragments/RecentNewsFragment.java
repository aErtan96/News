package com.dertsizvebugsiz.news.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dertsizvebugsiz.news.R;

import androidx.fragment.app.Fragment;

public class RecentNewsFragment extends Fragment {

    public static RecentNewsFragment newInstance(){
        RecentNewsFragment recentNewsFragment = new RecentNewsFragment();
        return recentNewsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recent_news, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
