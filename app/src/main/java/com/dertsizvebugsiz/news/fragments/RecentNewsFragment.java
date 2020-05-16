package com.dertsizvebugsiz.news.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dertsizvebugsiz.news.EndlessRecyclerViewScrollListener;
import com.dertsizvebugsiz.news.R;
import com.dertsizvebugsiz.news.activities.MainActivity;
import com.dertsizvebugsiz.news.adapters.RecentNewsAdapter;
import com.dertsizvebugsiz.news.dataclasses.News;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecentNewsFragment extends Fragment {

    public RecyclerView recyclerView;
    public RecentNewsAdapter recentNewsAdapter;

    public static RecentNewsFragment newInstance(){
        RecentNewsFragment recentNewsFragment = new RecentNewsFragment();
        return recentNewsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recent_news, container, false);

        getViews(rootView);
        initRecycler();
        registerEventListeners();

        Log.d("DEBUG", "ONCREATEVIEW");

        return rootView;
    }

    private void getViews(View root){
        recyclerView = root.findViewById(R.id.recent_news_recyclerview);
    }

    private void initRecycler(){

        News[] news = new News[]{};
        ArrayList arrayList = new ArrayList<>(Arrays.asList(news));
        recentNewsAdapter = new RecentNewsAdapter(arrayList, ((MainActivity)getActivity()), getActivity().getLayoutInflater());
        recyclerView.setAdapter(recentNewsAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void registerEventListeners(){
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener((LinearLayoutManager) recyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                ((MainActivity)getActivity()).loadMoreIntoRecentNews();
            }
        });
    }

}
