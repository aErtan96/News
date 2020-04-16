package com.dertsizvebugsiz.news.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dertsizvebugsiz.news.R;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class RecentNewsFragment extends Fragment {

    private RecyclerView recyclerView;

    public static RecentNewsFragment newInstance(){
        RecentNewsFragment recentNewsFragment = new RecentNewsFragment();
        return recentNewsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recent_news, container, false);
        recyclerView = rootView.findViewById(R.id.recent_news_recyclerview);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
