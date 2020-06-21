package com.dertsizvebugsiz.news.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.dertsizvebugsiz.news.EndlessRecyclerViewScrollListener;
import com.dertsizvebugsiz.news.R;
import com.dertsizvebugsiz.news.activities.MainActivity;
import com.dertsizvebugsiz.news.adapters.CollectionsAdapter;
import com.dertsizvebugsiz.news.adapters.RecentNewsAdapter;
import com.dertsizvebugsiz.news.dataclasses.News;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class RecentNewsFragment extends Fragment {

    private LinearLayout recentNewsContainer, collectionsContainer;

    public RecyclerView recentNewsRecyclerView;
    public RecentNewsAdapter recentNewsAdapter;

    public RecyclerView collectionsRecyclerView;
    public CollectionsAdapter collectionsAdapter;

    public SwipeRefreshLayout recentNewsSwipe;


    public static RecentNewsFragment newInstance(){
        RecentNewsFragment recentNewsFragment = new RecentNewsFragment();
        return recentNewsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recent_news, container, false);

        getViews(rootView);
        initRecentNewsRecycler();
        initCollectionsRecycler();
        registerEventListeners();

        Log.d("DEBUG", "ONCREATEVIEW");

        return rootView;
    }

    private void getViews(View root){
        recentNewsRecyclerView = root.findViewById(R.id.recent_news_recyclerview);
        collectionsRecyclerView = root.findViewById(R.id.collections_recyclerview);

        recentNewsContainer = root.findViewById(R.id.recent_news_recyclerview_container);
        collectionsContainer = root.findViewById(R.id.collections_recyclerview_container);

        recentNewsSwipe = root.findViewById(R.id.recent_news_swipe_refresh);
    }

    private void initRecentNewsRecycler(){
        News[] news = new News[]{};
        ArrayList arrayList = new ArrayList<>(Arrays.asList(news));
        recentNewsAdapter = new RecentNewsAdapter(arrayList, ((MainActivity)getActivity()), getActivity().getLayoutInflater());
        recentNewsRecyclerView.setAdapter(recentNewsAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recentNewsRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initCollectionsRecycler(){
        collectionsAdapter = new CollectionsAdapter(((MainActivity) getActivity()), getActivity().getLayoutInflater());
        collectionsRecyclerView.setAdapter(collectionsAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        collectionsRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private void registerEventListeners(){
        recentNewsRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener((LinearLayoutManager) recentNewsRecyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                ((MainActivity)getActivity()).loadMoreIntoRecentNews();
            }
        });
        recentNewsSwipe.setOnRefreshListener(() -> ((MainActivity)getActivity()).loadLastUploadedNews());
    }

    public void openRecentNewsRecycler(){
        recentNewsContainer.setVisibility(View.VISIBLE);
        collectionsContainer.setVisibility(View.INVISIBLE);
        recentNewsAdapter.notifyDataSetChanged();
    }

    public void openCollectionsRecycler(){
        recentNewsContainer.setVisibility(View.INVISIBLE);
        collectionsContainer.setVisibility(View.VISIBLE);
        collectionsAdapter.notifyDataSetChanged();
    }

    public void RefreshCompleted(){
        recentNewsSwipe.setRefreshing(false);
    }

}
